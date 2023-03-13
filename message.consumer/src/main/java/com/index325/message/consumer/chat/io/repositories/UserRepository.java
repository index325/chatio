package com.index325.message.consumer.chat.io.repositories;

import com.index325.message.consumer.chat.io.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
