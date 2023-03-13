package com.index325.message.sender.chat.io.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageOutput {
    private String messageBody;
    private String toId;
    private String fromId;
}
