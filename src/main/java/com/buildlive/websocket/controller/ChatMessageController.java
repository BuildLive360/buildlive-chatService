package com.buildlive.websocket.controller;

import com.buildlive.websocket.dto.ChatMessage;
import com.buildlive.websocket.dto.ChatRoomRequest;
import com.buildlive.websocket.model.ChatMessages;
import com.buildlive.websocket.service.IChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class ChatMessageController {

    @Autowired
    IChatService chatService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

//    @MessageMapping("/create-chatRoom")
//    public void createOrOpenChatRoom(@Payload ChatRoomRequest request,SimpMessageHeaderAccessor headerAccessor){
//
//        System.out.println(request);
//        String roomName = chatService.createOrOpenChatRoom(request);
//        System.out.println(roomName+"name");
//        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        accessor.setSessionId(headerAccessor.getSessionId());
//        accessor.setLeaveMutable(true);
//        messagingTemplate.convertAndSendToUser(Objects.requireNonNull(headerAccessor.getSessionId()),
//                "/app/queue/chat-room-name",roomName,accessor.getMessageHeaders());
//    }

    @MessageMapping("/create-chatRoom")
    public void createOrOpenChatRoom(@Payload ChatRoomRequest request, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(request);
        String roomName = chatService.createOrOpenChatRoom(request);
        List<ChatMessages> messages = chatService.getMessagesForChatRoom(roomName);
        System.out.println(roomName + "name");
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        accessor.setSessionId(headerAccessor.getSessionId());
        accessor.setLeaveMutable(true);

        Map<String,Object> messagePayload = new HashMap<>();
        messagePayload.put("roomName",roomName);
        messagePayload.put("messages", messages);
        messagingTemplate.convertAndSendToUser(
                Objects.requireNonNull(headerAccessor.getSessionId()),
                "/queue/messages", // Use "/queue/chat-room-name" as the destination
                messagePayload,
                accessor.getMessageHeaders()
        );
    }


    @MessageMapping("/chat")
    public void handleChatMessage(@Payload ChatMessage chatMessage) throws JsonProcessingException {
//        System.out.println(roomName+"jjjjreviiiiiiiiiii");
        // Extract the roomName from the destination
        System.out.println(chatMessage+"mjeeejeshhh");

      ChatMessages chatMessages  =  chatService.processMessage(chatMessage);
        System.out.println(chatMessages+"kk");
        System.out.println(chatMessage.getChatRoomName());
        // You can further process or save the message as needed
        String jsonMessage = new ObjectMapper().writeValueAsString(chatMessages);
        messagingTemplate.convertAndSend("/topic/" + chatMessages.getChatRoomName(), jsonMessage);
    }
}
