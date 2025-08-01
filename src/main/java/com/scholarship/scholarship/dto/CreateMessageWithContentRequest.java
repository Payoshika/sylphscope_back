package com.scholarship.scholarship.dto;

import com.scholarship.scholarship.model.Message;
import com.scholarship.scholarship.model.MessageContent;
import lombok.Data;

@Data
public class CreateMessageWithContentRequest {
    private Message message;
    private MessageContent messageContent;
}

