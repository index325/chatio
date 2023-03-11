package com.index325.users.management.chat.io.services;

import com.index325.users.management.chat.io.dtos.output.UserOutput;
import com.index325.users.management.chat.io.dtos.Codes;
import com.index325.users.management.chat.io.dtos.input.UserCreationRequest;
import com.index325.users.management.chat.io.dtos.input.UserUpdateRequest;
import com.index325.users.management.chat.io.models.User;
import com.index325.users.management.chat.io.repositories.UserRepository;
import com.index325.users.management.chat.io.shared.exceptions.AppException;
import com.index325.users.management.chat.io.shared.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public UserOutput createUser(UserCreationRequest userCreationRequest) {

        Optional<User> checkIfEmailIsInUse = userRepository.findUserByEmail(userCreationRequest.getEmail());

        if (checkIfEmailIsInUse.isPresent()) {
            throw new AppException(Codes.Error.User.EmailAlreadyInUse.getMessage(), Codes.Error.User.EmailAlreadyInUse.getCode());
        }

        User user = new User();
        user.setPassword(userCreationRequest.getPassword());
        user.setEmail(userCreationRequest.getEmail());
        user.setName(userCreationRequest.getName());

        userRepository.save(user);

        UserOutput userOutput = new UserOutput(user);

        return userOutput;
    }

    public UserOutput findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new ResourceNotFoundException("This user was not found", Codes.Error.User.UserWasNotFound.getCode());
        }

        UserOutput userOutput = new UserOutput(user.get());

        return userOutput;
    }

    @Transactional
    public UserOutput updateUser(Long userId, UserUpdateRequest userUpdateRequest) {
        Optional<User> checkIfAlreadyExistsAnUserWithThisEmail = userRepository.findUserByEmailWithDifferentId(userUpdateRequest.getEmail(), userId);

        if (checkIfAlreadyExistsAnUserWithThisEmail.isPresent()) {
            throw new ResourceNotFoundException("This email is already in use", Codes.Error.User.EmailAlreadyInUse.getCode());
        }

        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("This user was not found", Codes.Error.User.UserWasNotFound.getCode());
        }

        User user = optionalUser.get();

        user.setName(userUpdateRequest.getName());
        user.setEmail(userUpdateRequest.getEmail());

        UserOutput userOutput = new UserOutput(user);

        return userOutput;
    }

    @Transactional
    public void deleteUser(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("This user was not found", Codes.Error.User.EmailAlreadyInUse.getCode());
        }

        User user = optionalUser.get();

        userRepository.delete(user);
    }

    public Page<UserOutput> findAllUsersPaged(Pageable pageable) {
        Page<User> userPaged = userRepository.findAll(pageable);

        List<UserOutput> userDTOs = userPaged.stream()
                .map(user -> new ModelMapper().map(user, UserOutput.class))
                .collect(Collectors.toList());

        return new PageImpl<>(userDTOs, pageable, userPaged.getTotalElements());
    }
}
