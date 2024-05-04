package com.buildlive.websocket.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
    String chatRoomName;
    String message;
    String receiverId;
    String senderEmail;

    @JsonCreator
    public ChatMessage(@JsonProperty("chatRoomName") String chatRoomName,
                       @JsonProperty("message") String message,
                       @JsonProperty("receiverId") String receiverId,
                       @JsonProperty("senderEmail") String senderEmail) {
        this.chatRoomName = chatRoomName;
        this.message = message;
        this.receiverId = receiverId;
        this.senderEmail = senderEmail;


    }
}
