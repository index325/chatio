package com.index325.message.consumer.chat.io.config.kafka;

import com.index325.chat.io.MessageAvro;
import com.index325.message.consumer.chat.io.models.User;
import com.index325.message.consumer.chat.io.models.UserMessages;
import com.index325.message.consumer.chat.io.repositories.UserMessagesRepository;
import com.index325.message.consumer.chat.io.repositories.UserRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class KafkaConsumerConfig {

    @Autowired
    private UserMessagesRepository userMessagesRepository;

    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "${kafka.topic}", groupId = "topic-user")
    public void consumer(ConsumerRecord<String, MessageAvro> messageAvro) {
        UserMessages userMessage = new UserMessages();
        MessageAvro value = messageAvro.value();

        Optional<User> fromOptional = userRepository.findById(value.getFromId());
        Optional<User> toOptional = userRepository.findById(value.getToId());

        if (fromOptional.isPresent() && toOptional.isPresent()) {
            User to = toOptional.get();
            User from = fromOptional.get();

            userMessage.setMessageBody(value.getMessageBody());
            userMessage.setFrom(from);
            userMessage.setTo(to);
            userMessage.setMessageBody(value.getMessageBody());
            userMessagesRepository.save(userMessage);
        }


    }


}
