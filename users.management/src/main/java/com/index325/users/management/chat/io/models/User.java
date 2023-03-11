package com.index325.users.management.chat.io.models;

import com.index325.users.management.chat.io.config.keycloak.listeners.SystemUserKeycloakListener;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners({SystemUserKeycloakListener.class})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    private String email;

    private String idUserKeycloak;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    public enum UserStatusEnum {
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        private final String status;

        private UserStatusEnum(String status) {
            this.status = status;
        }

        public String toString() {
            return this.status;
        }
    }
}
