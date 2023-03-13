package com.index325.users.authentication.chat.io.controllers;

import com.index325.users.authentication.chat.io.dto.input.UserAuthRequest;
import com.index325.users.authentication.chat.io.services.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> userLogin(@RequestBody UserAuthRequest userAuthRequest){
        var accessAndRefreshToken = authService.createAccessAndRefreshToken(userAuthRequest);
        return new ResponseEntity(accessAndRefreshToken, HttpStatus.OK);
    }
}
