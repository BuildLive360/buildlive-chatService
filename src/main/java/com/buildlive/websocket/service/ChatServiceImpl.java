package com.buildlive.websocket.service;

import com.buildlive.websocket.dto.ChatMessage;
import com.buildlive.websocket.dto.ChatRoomRequest;
import com.buildlive.websocket.model.ChatMessages;
import com.buildlive.websocket.model.ChatRoom;
import com.buildlive.websocket.repository.ChatMessageRepository;
import com.buildlive.websocket.repository.ChatRoomRepository;
import com.buildlive.websocket.service.feign.CompanyFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements IChatService{

    @Autowired
    CompanyFeign companyFeign;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Override
    public String createOrOpenChatRoom(ChatRoomRequest request) {
        String senderId = companyFeign.getPartyMemberId(request.getSenderEmail(), UUID.fromString(request.getCompanyId()));
        System.out.println(senderId);
        String chatRoomName = createChatRoomWithUniqueName(senderId,request.getReceiverId(),request.getCompanyId());
        if(!chatRoomExists(chatRoomName)){
            createChatRoom(chatRoomName,
                    senderId,
                    request.getReceiverId(),
                    request.getCompanyId());

        }
        else {
            openChatRoom(chatRoomName);
        }

        return chatRoomName;
    }

    @Override
    public void saveMessage(String roomName, String message) {
        System.out.println(roomName);
    }

    @Override
    public ChatMessages processMessage(ChatMessage chatMessage) {
        String chatRoomName = chatMessage.getChatRoomName();

        String[] ids = chatRoomName.split("/");
        if(ids.length == 3){
            String senderId = ids[0];
            String receiverId = ids[1];
            String companyId  = ids[2];
            System.out.println(senderId+"senderId");
            System.out.println(receiverId+"receiverId");
            System.out.println(companyId+"companyId");

            String sender = companyFeign.getPartyMemberId(chatMessage.getSenderEmail(), UUID.fromString(companyId));

            ChatMessages chatMessages = ChatMessages.builder()
                    .chatRoomName(chatMessage.getChatRoomName())
                    .content(chatMessage.getMessage())
                    .senderId(sender)
                    .recipientId(chatMessage.getReceiverId())
                    .companyId(companyId)
                    .build();
            chatMessageRepository.save(chatMessages);
            return chatMessages;
        }
        else {
            return null;
        }
    }

    @Override
    public List<ChatMessages> getMessagesForChatRoom(String roomName) {

        return chatMessageRepository.findByChatRoomName(roomName);
    }


//    private String createChatRoomWithUniqueName(String senderId,String receiverId,String companyId){
//        return senderId +"/"+ receiverId + "/" + companyId;
//    }

    private String createChatRoomWithUniqueName(String senderId, String receiverId, String companyId) {
        // Ensure consistent order of senderId and receiverId
        String[] idArray = {senderId, receiverId};
        Arrays.sort(idArray);
        String firstId = idArray[0];
        String secondId = idArray[1];

        return firstId + "/" + secondId + "/" + companyId;
    }


    private boolean chatRoomExists(String chatRoomName){
        return chatRoomRepository.existsByChatRoomName(chatRoomName);
    }

    private void openChatRoom(String chatRoomName){
        System.out.println("opened"+ chatRoomName);
    }

    private void createChatRoom(String chatRoomName,
                                String senderId,
                                String receiverId,
                                String companyId){

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .senderId(senderId)
                .recipientId(receiverId)
                .companyId(companyId)
                .build();

        chatRoomRepository.save(chatRoom);

    }
}
