package com.index325.users.management.chat.io.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class PasswordEncoder {
    public static String encode(String text) {
        return new BCryptPasswordEncoder().encode(text);
    }
}
