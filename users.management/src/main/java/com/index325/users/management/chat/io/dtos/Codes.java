package com.index325.users.management.chat.io.dtos;

public class Codes {
    public static class Success {
        public static class User {
            public static final ApiResponse UserCreatedSuccessfully = new ApiResponse("User created successfully", 1000L);
            public static final ApiResponse UserFoundSuccessfully = new ApiResponse("User found successfully", 1001L);
            public static final ApiResponse UserUpdatedSuccessfully = new ApiResponse("User updated successfully", 1002L);
            public static final ApiResponse UserDeletedSuccessfully = new ApiResponse("User found successfully", 1003L);
        }
    }

    public static class Error {
        public static class User {
            public static final ApiResponse UserWasNotFound = new ApiResponse("User was not found", -1001L);
            public static final ApiResponse EmailAlreadyInUse = new ApiResponse("This email is already in use", -1002L);
            public static final ApiResponse CouldNotUpdateThisUser = new ApiResponse("Could not update this user", -1003L);
            public static final ApiResponse dontHavePermissionToCreateUser = new ApiResponse("User don't have permission to list or create users", -1004L);
        }
    }
}
