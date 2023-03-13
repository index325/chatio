package com.index325.message.sender.chat.io.dtos;

public class Codes {
    public static class Success {
        public static class Message {
            public static final ApiResponse MessageSentSuccessfully = new ApiResponse("Message sent successfully", 1000L);
        }
    }

    public static class Error {
        public static class Message {
            public static final ApiResponse MessageNotSent = new ApiResponse("An error occurred when tried to send message", -1000L);
        }
    }
}
