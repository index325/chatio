package com.index325.message.sender.chat.io.controllers;

import com.index325.message.sender.chat.io.dtos.Codes;
import com.index325.message.sender.chat.io.dtos.input.SendMessageRequest;
import com.index325.message.sender.chat.io.dtos.output.MessageOutput;
import com.index325.message.sender.chat.io.dtos.response.SendMessageResponse;
import com.index325.message.sender.chat.io.services.MessageService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<SendMessageResponse> sendMessage(@RequestBody SendMessageRequest sendMessageRequest, KeycloakAuthenticationToken keycloakAuthenticationToken) {
        MessageOutput messageOutput = messageService.sendMessage(sendMessageRequest);

        return new ResponseEntity<>(new SendMessageResponse(Codes.Success.Message.MessageSentSuccessfully, messageOutput), HttpStatus.OK);
    }
}
