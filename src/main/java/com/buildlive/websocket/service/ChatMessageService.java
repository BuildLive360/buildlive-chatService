//package com.buildlive.websocket.service;
//
//import com.buildlive.websocket.exception.ResourceNotFoundException;
//import com.buildlive.websocket.model.ChatMessage;
//import com.buildlive.websocket.model.MessageStatus;
//import com.buildlive.websocket.repository.ChatMessageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoOperations;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class ChatMessageService {
//    @Autowired private ChatMessageRepository repository;
//    @Autowired private ChatRoomService chatRoomService;
//    @Autowired private MongoOperations mongoOperations;
//
////    public ChatMessage save(ChatMessage chatMessage) {
////        chatMessage.setStatus(MessageStatus.RECEIVED);
////        repository.save(chatMessage);
////        return chatMessage;
////    }
//
//    public long countNewMessages(String senderId, String recipientId) {
//        return repository.countBySenderIdAndRecipientIdAndStatus(
//                senderId, recipientId, MessageStatus.RECEIVED);
//    }
//
//    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
//        var chatId = chatRoomService.getChatId(senderId, recipientId, false);
//
//        var messages =
//                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());
//
//        if(messages.size() > 0) {
//            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
//        }
//
//        return messages;
//    }
//
//    public ChatMessage findById(String id) {
//        return repository
//                .findById(id)
//                .map(chatMessage -> {
//                    chatMessage.setStatus(MessageStatus.DELIVERED);
//                    return repository.save(chatMessage);
//                })
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("can't find message (" + id + ")"));
//    }
//
//    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
//        Query query = new Query(
//                Criteria
//                        .where("senderId").is(senderId)
//                        .and("recipientId").is(recipientId));
//        Update update = Update.update("status", status);
//        mongoOperations.updateMulti(query, update, ChatMessage.class);
//    }
//
//}