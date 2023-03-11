package com.index325.users.management.chat.io.controllers;

import com.index325.users.management.chat.io.dtos.Codes;
import com.index325.users.management.chat.io.dtos.output.UserOutput;
import com.index325.users.management.chat.io.dtos.response.FindAllUsersPagedResponse;
import com.index325.users.management.chat.io.dtos.response.FindUserResponse;
import com.index325.users.management.chat.io.dtos.response.UserCreationResponse;
import com.index325.users.management.chat.io.services.UserService;
import com.index325.users.management.chat.io.dtos.input.UserCreationRequest;
import com.index325.users.management.chat.io.dtos.input.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserCreationResponse> userCreation(@RequestBody UserCreationRequest userCreationRequest){
        userService.createUser(userCreationRequest);

        return new ResponseEntity<>(new UserCreationResponse(Codes.Success.User.UserCreatedSuccessfully), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<FindAllUsersPagedResponse> findAllUsersPaged(Pageable pageable) {

        Page<UserOutput> user = userService.findAllUsersPaged(pageable);

        return new ResponseEntity<>(new FindAllUsersPagedResponse(Codes.Success.User.UserFoundSuccessfully, user), HttpStatus.FOUND);
    }

    @GetMapping("{id}")
    public ResponseEntity<FindUserResponse> findById(@PathVariable Long id) {
        UserOutput user = userService.findUserById(id);

        return new ResponseEntity<>(new FindUserResponse(Codes.Success.User.UserFoundSuccessfully, user), HttpStatus.FOUND);
    }

    @PutMapping("{id}")
    public ResponseEntity<FindUserResponse> update(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        UserOutput user = userService.updateUser(id, userUpdateRequest);

        return new ResponseEntity<>(new FindUserResponse(Codes.Success.User.UserUpdatedSuccessfully, user), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<FindUserResponse> delete(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(new FindUserResponse(Codes.Success.User.UserDeletedSuccessfully), HttpStatus.NO_CONTENT);
    }

}
