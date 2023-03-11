package com.index325.users.management.chat.io.dtos.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequest {
    private String name;

    @Email
    private String email;

}
