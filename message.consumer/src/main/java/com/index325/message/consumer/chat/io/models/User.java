package com.index325.message.consumer.chat.io.models;


import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    private String id;

    private String name;

    private String password;

    private String email;

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
