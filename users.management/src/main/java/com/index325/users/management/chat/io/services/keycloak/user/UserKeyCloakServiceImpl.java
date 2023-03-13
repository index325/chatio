package com.index325.users.management.chat.io.services.keycloak.user;

import com.index325.users.management.chat.io.config.keycloak.properties.KeycloakProperties;
import com.index325.users.management.chat.io.dtos.Codes;
import com.index325.users.management.chat.io.models.User;
import com.index325.users.management.chat.io.shared.exceptions.AppException;
import com.index325.users.management.chat.io.shared.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.ErrorRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.Collections.singletonList;

@Slf4j
@Service
public class UserKeyCloakServiceImpl implements UserKeyCloakService {

    private final RealmResource realmResource;

    private final UsersResource usersResource;

    private final KeycloakProperties keycloakProperties;

    public UserKeyCloakServiceImpl(
                                   KeycloakProperties keyCloakProperties
    ) {
        this.realmResource = keyCloakProperties.getInstance().realm(keyCloakProperties.getRealm());
        this.usersResource = realmResource.users();
        this.keycloakProperties = keyCloakProperties;
    }

    @Override
    public User save(User systemUser) {
        log.info("Send save new user keycloak: {}", systemUser.getName());
        var user = systemUserToUserRepresentation(systemUser, true);
        try {
            var checkIfEmailIsInUse = getUserRepresentationByEmail(systemUser.getEmail())
                    .stream()
                    .findFirst();

            if (checkIfEmailIsInUse.isPresent()) {
                throw new AppException(Codes.Error.User.EmailAlreadyInUse);
            }

            usersResource.create(user);
            var userSaved = getUserRepresentationByEmail(systemUser.getEmail())
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Email: " + systemUser.getEmail() + "not found"));
            systemUser.setId(userSaved.getId());
            return systemUser;
        }
        catch (ForbiddenException e) {
            ErrorRepresentation error = e.getResponse().readEntity(ErrorRepresentation.class);
            log.error("User don't have permission to list or create users. {}", error.getErrorMessage());
            throw new AppException(Codes.Error.User.dontHavePermissionToCreateUser);
        }
        catch (ClientErrorException e) {
            ErrorRepresentation error = e.getResponse().readEntity(ErrorRepresentation.class);
            log.error("Error to save user uri. {}", error.getErrorMessage());
            throw new ClientErrorException(e.getResponse());
        }
    }

    @Override
    public User update(User systemUser) {
        log.info("update user {}", systemUser.getName());

        UserResource userResource = usersResource.get(systemUser.getId());
        UserRepresentation userRepresentation = userResource.toRepresentation();
        if (!Objects.equals(userRepresentation.getUsername().toLowerCase(), systemUser.getEmail().toLowerCase()) ||
                !Objects.equals(userRepresentation.getEmail(), systemUser.getEmail())) {

            try (Response response = usersResource.delete(systemUser.getId())) {
                if (!Objects.equals(HttpStatus.NO_CONTENT, response.getStatus())) {
                    throw new AppException(Codes.Error.User.CouldNotUpdateThisUser.getMessage(), Codes.Error.User.CouldNotUpdateThisUser.getCode());
                }
            }

            return this.save(systemUser);
        }
        userResource.update(systemUserToUserRepresentation(systemUser, false));
        return systemUser;
    }

    @Override
    public List<UserRepresentation> getUserRepresentationByEmail(String email) {
        log.info("get user by email {}", email);
        return usersResource.search(email, 0, 10);
    }

    private UserRepresentation systemUserToUserRepresentation(User systemUser, boolean isCreation) {
        var user = new UserRepresentation();
        user.setUsername(systemUser.getEmail());
        user.setFirstName(systemUser.getName());
        user.setEmail(systemUser.getEmail());
        user.setEmailVerified(Boolean.TRUE);
        user.setEnabled(Boolean.TRUE);
//        user.setOrigin(BACKEND_APPLICATION_CHAT_IO);
        user.setCreatedTimestamp(getCreatedTimestamp(LocalDateTime.now()));
        if (isCreation) {
            user.setCredentials(singletonList(createPasswordCredentials(systemUser.getPassword())));
        }
        return user;
    }

    private User systemUserToUserRepresentation(UserRepresentation userRepresentation) {
        return User
                .builder()
                .name(userRepresentation.getFirstName())
                .email(userRepresentation.getEmail())
                .status(Boolean.TRUE.equals(userRepresentation.isEnabled()) ?
                        User.UserStatusEnum.ACTIVE : User.UserStatusEnum.INACTIVE)
                .id(userRepresentation.getId())
                .build();
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    private long getCreatedTimestamp(LocalDateTime createdDate) {
        return ZonedDateTime.of(createdDate, ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    @Override
    public User syncKeyCloakByEmail(String email) {
        log.info("syncKeyCloakByEmail: {}", email);
        List<UserRepresentation> userRepresentationByEmail = getUserRepresentationByEmail(email);
        return userRepresentationByEmail
                .stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(this::systemUserToUserRepresentation)
                .orElseThrow(() -> new ResourceNotFoundException(email, Codes.Error.User.UserWasNotFound.getCode()));
    }

    @Override
    public List<UserSessionRepresentation> getAllActiveUserSessions() {
        try {
            ClientResource clientResource = realmResource.clients().get("test");
            return clientResource.getUserSessions(0, 1000);

        } catch (ClientErrorException e) {
            ErrorRepresentation error = e.getResponse().readEntity(ErrorRepresentation.class);
            log.error("Error to verify login logout users. {}", error.getErrorMessage());
            throw new ClientErrorException(e.getResponse());
        }
    }

    @Override
    public List<UserSessionRepresentation> getAllActiveSessionByUser(User systemUser) {
        try {
            return usersResource.get(systemUser.getId()).getUserSessions();
        } catch (ClientErrorException e) {
            ErrorRepresentation error = e.getResponse().readEntity(ErrorRepresentation.class);
            log.error("Error to verify login logout users. {}", error.getErrorMessage());
            throw new ClientErrorException(e.getResponse());
        }
    }

    @Override
    public void logout(User systemUser) {
        try {
            usersResource.get(systemUser.getId()).logout();
        } catch (ClientErrorException e) {
            ErrorRepresentation error = e.getResponse().readEntity(ErrorRepresentation.class);
            log.error("Error to logout user. {}", error.getErrorMessage());
            throw new ClientErrorException(e.getResponse());
        }
    }

}
