package com.index325.message.sender.chat.io.dtos.response;

import com.index325.message.sender.chat.io.dtos.ApiResponse;
import com.index325.message.sender.chat.io.dtos.output.MessageOutput;

public class SendMessageResponse extends ApiResponse {

        private MessageOutput messageOutput;

        public SendMessageResponse(ApiResponse apiResponse) {
            super(apiResponse.getMessage(), apiResponse.getCode());
        }

    public SendMessageResponse(ApiResponse apiResponse, MessageOutput messageOutput) {
        super(apiResponse.getMessage(), apiResponse.getCode());
        this.messageOutput = messageOutput;
    }

    public MessageOutput getMessageOutput() {
        return messageOutput;
    }

    public void setMessageOutput(MessageOutput messageOutput) {
        this.messageOutput = messageOutput;
    }
}
