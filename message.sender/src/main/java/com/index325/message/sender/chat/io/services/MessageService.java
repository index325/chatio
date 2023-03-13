package com.index325.message.sender.chat.io.services;

import com.index325.message.sender.chat.io.config.kafka.KafkaProducerConfig;
import com.index325.message.sender.chat.io.dtos.input.SendMessageRequest;
import com.index325.message.sender.chat.io.dtos.output.MessageOutput;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class MessageService {

    @Autowired
    KafkaProducerConfig kafkaProducerConfig;

    public MessageOutput sendMessage(SendMessageRequest sendMessageRequest) {

        KeycloakAuthenticationToken authentication =
                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        Principal principal = (Principal) authentication.getPrincipal();

        String loggedUserId = "";

        if (principal instanceof KeycloakPrincipal) {
            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            AccessToken token = kPrincipal.getKeycloakSecurityContext().getToken();
            loggedUserId = token.getSubject();
        }

        kafkaProducerConfig.producer(new MessageOutput(sendMessageRequest.getMessageBody(), sendMessageRequest.getToId(), loggedUserId));
        return new MessageOutput();
    }
}
