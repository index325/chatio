package com.index325.users.management.chat.io.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Profiles {

    private final Environment env;

    public boolean verifyTestProfile() {
        Optional<String> stringOptional = Arrays.stream(env.getActiveProfiles()).findAny();
        return stringOptional.isPresent() && Objects.equals(stringOptional.get(), "test");
    }
}
