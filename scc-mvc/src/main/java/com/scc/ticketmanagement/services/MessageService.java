package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.exentities.Conversation;

import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
public interface MessageService {
    List<MessageEntity> getAllConversations();

    List<Conversation> getAllConversationsByPageId(String pageId);

    List<String> getAllSenderIdByPageID(String pageId);

    List<MessageEntity> getMessageDesc(String receiverId, String senderId);

    String getLastMessage(String receiverId, String senderId);
}
