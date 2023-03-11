package com.index325.users.management.chat.io.dtos.response;

import com.index325.users.management.chat.io.dtos.output.UserOutput;
import com.index325.users.management.chat.io.dtos.ApiResponse;

public class FindUserResponse extends ApiResponse {
    UserOutput userOutput;
    public FindUserResponse(ApiResponse apiResponse) {
        super(apiResponse.getMessage(), apiResponse.getCode());
    }

    public FindUserResponse(ApiResponse apiResponse, UserOutput userOutput) {
        super(apiResponse.getMessage(), apiResponse.getCode());
        this.userOutput = userOutput;
    }

    public UserOutput getUserOutput() {
        return userOutput;
    }

    public void setUserOutput(UserOutput userOutput) {
        this.userOutput = userOutput;
    }
}
