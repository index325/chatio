package com.index325.users.management.chat.io.dtos.response;

import com.index325.users.management.chat.io.dtos.ApiResponse;

public class UserCreationResponse extends ApiResponse {
    public UserCreationResponse(ApiResponse apiResponse) {
        super(apiResponse.getMessage(), apiResponse.getCode());
    }

}
