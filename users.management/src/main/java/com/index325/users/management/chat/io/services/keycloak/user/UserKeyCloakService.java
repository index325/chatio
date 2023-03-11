package com.index325.users.management.chat.io.services.keycloak.user;

import com.index325.users.management.chat.io.models.User;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.UserSessionRepresentation;

import java.util.List;

public interface UserKeyCloakService {

    User save(User systemUser);

    User update(User systemUser);

    List<UserRepresentation> getUserRepresentationByEmail(String email);

    User syncKeyCloakByEmail(String email);

    List<UserSessionRepresentation> getAllActiveUserSessions();

    List<UserSessionRepresentation> getAllActiveSessionByUser(User systemUser);

    void logout(User systemUser);

}
