package com.index325.users.management.chat.io.dtos;

public class ApiResponse {
    String message;
    Long code;
    Boolean isError;

    protected ApiResponse(String description, Long code) {
        this.message = description;
        this.code = code;
        this.isError = this.code < 0;
    }

    public ApiResponse clone() {
        return new ApiResponse(this.message, this.code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Boolean getError() {
        return isError;
    }

    public void setError(Boolean error) {
        isError = error;
    }
}
