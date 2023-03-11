package com.index325.users.management.chat.io.dtos.response;

import com.index325.users.management.chat.io.dtos.ApiResponse;
import org.springframework.data.domain.Page;

public class FindAllUsersPagedResponse extends ApiResponse {
    Page usersPaged;
    public FindAllUsersPagedResponse(ApiResponse apiResponse) {
        super(apiResponse.getMessage(), apiResponse.getCode());
    }

    public FindAllUsersPagedResponse(ApiResponse apiResponse, Page usersPaged) {
        super(apiResponse.getMessage(), apiResponse.getCode());
        this.usersPaged = usersPaged;
    }

    public Page getUsersPaged() {
        return usersPaged;
    }

    public void setUsersPaged(Page usersPaged) {
        this.usersPaged = usersPaged;
    }
}