package com.index325.users.management.chat.io.shared.exceptions;

import com.index325.users.management.chat.io.dtos.ApiResponse;
import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private Long code;

    public AppException(String description, Long code) {
        super(description);
        this.code = code;
    }

    public AppException(ApiResponse apiResponse) {
        super(apiResponse.getMessage());
        this.code = apiResponse.getCode();
    }
}
