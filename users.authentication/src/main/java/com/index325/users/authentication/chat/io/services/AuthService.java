package com.index325.users.authentication.chat.io.services;

import com.index325.users.authentication.chat.io.dto.input.UserAuthRequest;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AccessTokenResponse createAccessAndRefreshToken(UserAuthRequest userAuthDto) {

        AuthzClient authzClient = AuthzClient.create();

        return authzClient.obtainAccessToken(userAuthDto.getUsername(), userAuthDto.getPassword());
    }

}
