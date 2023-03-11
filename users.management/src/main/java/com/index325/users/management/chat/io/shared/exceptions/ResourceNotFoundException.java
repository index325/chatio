package com.index325.users.management.chat.io.shared.exceptions;

import com.index325.users.management.chat.io.dtos.ApiResponse;
import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {
    private Long code;

    public ResourceNotFoundException(String description, Long code) {
        super(description);
        this.code = code;
    }

    public ResourceNotFoundException(ApiResponse apiResponse) {
        super(apiResponse.getMessage());
        this.code = apiResponse.getCode();
    }

}
