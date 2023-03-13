package com.index325.users.management.chat.io.dtos.output;

import com.index325.users.management.chat.io.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserOutput {
    private String id;
    private String name;
    private String email;



    public UserOutput(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.id = user.getId();
    }
}
