package com.index325.message.sender.chat.io.config.kafka;

import com.index325.chat.io.MessageAvro;
import com.index325.message.sender.chat.io.dtos.output.MessageOutput;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${topic.name.producer}")
    private String value;

    @Autowired
    private KafkaTemplate<String, MessageAvro> kafkaTemplate;

    public void producer(MessageOutput messageOutput) {
        MessageAvro messageAvro = MessageAvro.newBuilder()
                .setMessageBody(messageOutput.getMessageBody())
                .setFromId(messageOutput.getFromId())
                .setToId(messageOutput.getToId()).build();

        kafkaTemplate.send(value, messageAvro);
    }
}
