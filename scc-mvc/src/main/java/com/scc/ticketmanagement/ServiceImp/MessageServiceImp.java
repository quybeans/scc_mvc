package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.ContactEntity;
import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.exentities.Conversation;
import com.scc.ticketmanagement.facebook.Contact;
import com.scc.ticketmanagement.repositories.ContactRepository;
import com.scc.ticketmanagement.repositories.MessageRepository;
import com.scc.ticketmanagement.services.MessageService;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.utilities.Constant;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PageService pageService;

    @Override
    public List<MessageEntity> getAllConversations() {
        return messageRepository.getAllConversations();
    }

    @Override
    public List<Conversation> getAllConversationsByPageId(String pageId) {
        List<Conversation> conversationList = new ArrayList<>();

        String accessToken = pageService.getPageAccessTokenByPageId(pageId);

        ContactEntity contactEntity = null;
        Contact fbContact = null;

        //Lay tat ca senderId cua page
        List<String> senderIds = this.getAllSenderIdByPageID(pageId);
        for (String senderId : senderIds) {
            //tim ra contact cua 1 thang sender
            try {
                contactEntity = contactRepository.getContactById(senderId);
                if (contactEntity != null) {
                } else {
                    try {
                        //neu khong co trong database thi gui api len fb kiem
                        fbContact = FacebookUtility.getContact(senderId, accessToken);
                        contactEntity = new ContactEntity();
                        contactEntity.setFacebookid(senderId);
                        contactEntity.setName(fbContact.getFirst_name() + " " + fbContact.getLast_name());
                        contactEntity.setPicture(fbContact.getProfile_pic());
                        contactEntity.setGender(fbContact.getGender());
                        contactEntity.setLocale(fbContact.getLocale());
                        contactEntity.setTimezone(fbContact.getTimezone());
                        contactRepository.saveAndFlush(contactEntity);
                    } catch (AuthenticationException ex) {
                        ex.printStackTrace();
                        return null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return null;
                    }
                }
                Conversation conversation = new Conversation();
                conversation.setSenderId(senderId);
                conversation.setSenderName(contactEntity.getName());
                conversation.setSenderPicture(contactEntity.getPicture());
                conversation.setLastMessage(this.getLastMessageContent(pageId, senderId));
                conversation.setRead(this.getLastMessage(pageId, senderId).getMessageRead());
                conversation.setLastMessageId(this.getLastMessage(pageId, senderId).getId());
                conversationList.add(conversation);
            } catch (javax.persistence.EntityNotFoundException e) {
                e.printStackTrace();
            }


        }
        return conversationList;
    }

    @Override
    public List<String> getAllSenderIdByPageID(String pageId) {
        return messageRepository.getAllSenderIdByPageId(pageId);
    }

    @Override
    public List<MessageEntity> getMessageDesc(String receiverId, String senderId) {
        return messageRepository.getMessageDesc(receiverId, senderId);
    }

    @Override
    public List<MessageEntity> getMessageAsc(String receiverId, String senderId) {
        return messageRepository.getMessageAsc(receiverId, senderId);
    }

    @Override
    public Page<MessageEntity> getMessageAscWithPage(String receiverId, String senderId, Integer page) {
        PageRequest pageRequest = new PageRequest(page - 1, Constant.PAGE_SIZE, Sort.Direction.ASC, "seq");
        return messageRepository.getMessageWithPage(receiverId, senderId, pageRequest);
    }
    @Override
    public Page<MessageEntity> getMessageDescWithPage(String receiverId, String senderId, Integer pageNum) {
        PageRequest pageRequest = new PageRequest(pageNum - 1, Constant.PAGE_SIZE, Sort.Direction.DESC, "seq");
        return messageRepository.getMessageWithPage(receiverId, senderId, pageRequest);
    }

    @Override
    public Page<MessageEntity> getMessageDescWithPageSize(String receiverId, String senderId, Integer pageNum) {
        PageRequest pageRequest = new PageRequest(0, (Constant.PAGE_SIZE)*pageNum, Sort.Direction.DESC, "seq");
        return messageRepository.getMessageWithPage(receiverId, senderId, pageRequest);
    }

    @Override
    public String getLastMessageContent(String receiverId, String senderId) {
        MessageEntity messageEntity = this.getMessageDesc(receiverId, senderId).get(0);
        return messageEntity.getContent();
    }

    @Override
    public MessageEntity getLastMessage(String receiverId, String senderId) {
        return this.getMessageDesc(receiverId, senderId).get(0);
    }


    @Override
    public MessageEntity setMessageRead(String messageId) {
        messageRepository.setMessageRead(messageId);
        return null;
    }
}
