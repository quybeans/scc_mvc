package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.exentities.Conversation;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
public interface MessageService {
    List<MessageEntity> getAllConversations();

    List<Conversation> getAllConversationsByPageId(String pageId);

    List<String> getAllSenderIdByPageID(String pageId);

    List<MessageEntity> getMessageDesc(String receiverId, String senderId);
    List<MessageEntity> getMessageAsc(String receiverId, String senderId);
    Page<MessageEntity> getMessageAscWithPage(String receiverId, String senderId, Integer page);

    String getLastMessage(String receiverId, String senderId);
}
