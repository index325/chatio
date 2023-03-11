package com.index325.users.management.chat.io.config.keycloak.listeners;

import com.index325.users.management.chat.io.services.keycloak.user.UserKeyCloakServiceImpl;
import com.index325.users.management.chat.io.utils.PasswordEncoder;
import com.index325.users.management.chat.io.utils.Profiles;
import com.index325.users.management.chat.io.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Slf4j
@Component
@RequiredArgsConstructor
public class SystemUserKeycloakListener {

    private final UserKeyCloakServiceImpl userKeyCloakService;

    private final Profiles profiles;

    @PrePersist
    public void prePersist(User systemUser) {
        if (profiles.verifyTestProfile()) return;
        userKeyCloakService.save(systemUser);
        systemUser.setPassword(PasswordEncoder.encode(systemUser.getPassword()));
        log.info("Saved SystemUser keycloak: {}", systemUser);
    }

    @PreUpdate
    public void preUpdate(User systemUser) {
        if (profiles.verifyTestProfile()) return;
        userKeyCloakService.update(systemUser);
        log.info("Updated SystemUser keycloak: {}", systemUser);
    }

}
