package com.buildlive.websocket.repository;

import com.buildlive.websocket.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId,String recipientId);

    boolean existsByChatRoomName(String chatRoomName);
}
