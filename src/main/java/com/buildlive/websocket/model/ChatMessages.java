package com.buildlive.websocket.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessages {

    @Id
    private String id;

    private String chatRoomName;
    private String senderId;
    private String recipientId;
    private String companyId;
    private String content;
    private Date timestamp;
//    private MessageStatus status;
}
