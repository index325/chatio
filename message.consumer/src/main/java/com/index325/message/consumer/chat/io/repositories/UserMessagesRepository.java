package com.index325.message.consumer.chat.io.repositories;

import com.index325.message.consumer.chat.io.models.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessagesRepository extends JpaRepository<UserMessages, Long> {
}
