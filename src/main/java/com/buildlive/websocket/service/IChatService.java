package com.buildlive.websocket.service;

import com.buildlive.websocket.dto.ChatMessage;
import com.buildlive.websocket.dto.ChatRoomRequest;
import com.buildlive.websocket.model.ChatMessages;

import java.util.List;

public interface IChatService {

    String createOrOpenChatRoom(ChatRoomRequest request);

    void saveMessage(String roomName,String message);

    ChatMessages processMessage(ChatMessage chatMessage);

    List<ChatMessages> getMessagesForChatRoom(String roomName);
}
