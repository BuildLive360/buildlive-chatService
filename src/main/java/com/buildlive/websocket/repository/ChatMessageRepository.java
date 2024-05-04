package com.buildlive.websocket.repository;

import com.buildlive.websocket.model.ChatMessages;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessages,String> {

//    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);
//    List<ChatMessage> findByChatId(String chatId);
    List<ChatMessages> findByChatRoomName(String chatRoomName);
}
