package com.index325.users.management.chat.io.repositories;

import com.index325.users.management.chat.io.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.id != :id")
    Optional<User> findUserByEmailWithDifferentId(String email, Long id);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);
}
