package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.ServiceImp.TicketIteamServiceImp;
import com.scc.ticketmanagement.exentities.Conversation;
import com.scc.ticketmanagement.exentities.ExMessage;
import com.scc.ticketmanagement.repositories.ContactRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.MessageItemService;
import com.scc.ticketmanagement.services.MessageService;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.services.TicketService;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * Created by Thien on 11/2/2016.
 */
@RestController
public class MessengerRESTController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageService messageService;

    @Autowired
    PageService pageService;

    @Autowired
    ContactRepository contactService;

    @Autowired
    MessageItemService messageItemService;

    @Autowired
    private TicketIteamServiceImp ticketIteamServiceImp;

    @Autowired
    TicketService ticketService;

    @RequestMapping(value = "/getAllConversations", method = RequestMethod.GET)
    public List<MessageEntity> getAllConversations() {
        return messageService.getAllConversations();
    }

    @RequestMapping(value = "/messenger/getAllConversationsByPageId", method = RequestMethod.GET)
    public List<Conversation> getAllConversations(@RequestParam("pageId") String pageId) {

        List<Conversation> result = messageService.getAllConversationsByPageId(pageId);
        Comparator<Conversation> comp = (Conversation a, Conversation b) -> {
            return b.getSentTime().compareTo(a.getSentTime());
        };

        Collections.sort(result, comp);
        return result;
    }

    @RequestMapping(value = "/messenger/getAllConversationsByPageIdAndSenderName", method = RequestMethod.POST)
    public List<Conversation> getAllConversationsByPageIdAndSenderName(@RequestParam("pageId") String pageId,
                                                                       @RequestParam("senderName") String senderName) {

        List<Conversation> result = messageService.getAllConversationsByPageId(pageId);

        result.removeIf(e -> (!e.getSenderName().toLowerCase().contains(senderName.toLowerCase())));

        Comparator<Conversation> comp = (Conversation a, Conversation b) -> {
            return b.getSentTime().compareTo(a.getSentTime());
        };

        Collections.sort(result, comp);
        return result;
    }

    @RequestMapping(value = "/messenger/getAllConversationsByPageIdAndSenderId", method = RequestMethod.POST)
    public List<Conversation> getAllConversationsByPageIdAndSenderId(@RequestParam("pageId") String pageId,
                                                                     @RequestParam("senderId") String senderId) {

        List<Conversation> result = messageService.getAllConversationsByPageId(pageId);

        result.removeIf(e -> (!e.getSenderId().contains(senderId)));

        Comparator<Conversation> comp = (Conversation a, Conversation b) -> {
            return b.getSentTime().compareTo(a.getSentTime());
        };

        Collections.sort(result, comp);
        return result;
    }

    @RequestMapping(value = "/messenger/getConversationBySenderIdAndPageId", method = RequestMethod.POST)
    public List<MessageEntity> getAllConversations(@RequestParam("pageId") String pageId,
                                                   @RequestParam("senderId") String senderId,
                                                   @RequestParam("pageNum") Integer pageNum) {
        Page<MessageEntity> messages = messageService.getMessageDescWithPage(pageId, senderId, pageNum);
        messages.getTotalPages();
        return messages.getContent();
    }

    @RequestMapping(value = "/messenger/getConversationBySenderIdWithPage", method = RequestMethod.POST)
    public List<ExMessage> getConversationBySenderIdWithPage(@RequestParam("pageId") String pageId,
                                                             @RequestParam("senderId") String senderId,
                                                             @RequestParam("pageNum") Integer pageNum) {
        Page<MessageEntity> messages = messageService.getMessageDescWithPageSize(pageId, senderId, pageNum);

        List<ExMessage> result = new ArrayList<>();
        ExMessage exMessage;
        for (MessageEntity message : messages.getContent()) {
            exMessage = new ExMessage();
            exMessage.setId(message.getId());
            exMessage.setContent(message.getContent());
            exMessage.setCreatedAt(message.getCreatedAt());
            exMessage.setReceiverName(message.getReceiverName());
            exMessage.setReceiverid(message.getReceiverid());
            exMessage.setSenderName(message.getSenderName());
            exMessage.setSenderid(message.getSenderid());
            exMessage.setSeq(message.getSeq());
            exMessage.setMessageRead(message.getMessageRead());
            exMessage.setSentimentScrore(message.getSentimentScrore());
            exMessage.setTicket(ticketService.getTicketByMessageId(message.getId()).size() != 0);
            result.add(exMessage);
        }

        return result;
    }

    @RequestMapping(value = "/messenger/sendMessageToCustomer", method = RequestMethod.POST)
    public String sendMessage(@RequestParam("pageId") String pageId,
                              @RequestParam("receiverId") String receiverId,
                              @RequestParam("content") String content) {

        System.out.println(pageId);
        System.out.println(receiverId);
        System.out.println(content);
        String accessToken = pageService.getPageAccessTokenByPageId(pageId);
        String sentMessage = "";
        if (accessToken != null && !accessToken.equals("")) {
            try {
                sentMessage = FacebookUtility.sendMessage(content, receiverId, accessToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sentMessage;
    }

    @RequestMapping(value = "/messenger/getCustomerInfo", method = RequestMethod.POST)
    public ContactEntity getCustomerInfo(@RequestParam("customerId") String customerId) {
        return contactService.getContactById(customerId);
    }

    @RequestMapping(value = "/messenger/getProfileId", method = RequestMethod.GET)
    public String getProfileId(@RequestParam("avtId") String avtId) throws AuthenticationException {
        return FacebookUtility.getProfileId(avtId);
    }

    @RequestMapping(value = "/messenger/setMessageRead", method = RequestMethod.POST)
    public MessageEntity setRead(@RequestParam("pageId") String pageId,
                                 @RequestParam("senderId") String senderId) {
        MessageEntity messageEntity = messageService.getLastMessage(pageId, senderId);
        if (messageEntity != null && messageEntity.getMessageRead() == false) {
            messageService.setMessageRead(messageEntity.getId());
        }
        return messageEntity;
    }

    @RequestMapping(value = "/messenger/addTicket", method = RequestMethod.POST)
    public TicketitemEntity startTicket(@RequestParam("ticketId") Integer ticketId,
                                        @RequestParam("messageId") String messageId,
                                        HttpServletRequest request) {
        System.out.println(ticketId);
        System.out.println(messageId);

        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);
        TicketitemEntity result = null;
        MessageitemEntity messageitemEntity = messageItemService.startTicket(messageId);
        if (messageitemEntity != null) {
            result = ticketIteamServiceImp.addMessageItemToTicket(ticketId, messageitemEntity.getItemId(), user.getUserid());

        }
        return result;
    }

    @RequestMapping(value = "/messenger/setConversationRead", method = RequestMethod.POST)
    public boolean setConversationRead(@RequestParam("pageId") String pageId,
                                       @RequestParam("senderId") String senderId) {
        messageService.setConversationRead(pageId, senderId);
        return true;
    }

    @RequestMapping(value = "/messenger/getNumberOfUnreadMessagesInConversation", method = RequestMethod.GET)
    public Integer getNumberOfUnreadMessagesInConversation(@RequestParam("pageId") String pageId,
                                                           @RequestParam("senderId") String senderId) {
        return messageService.getNumberOfUnreadMessageInConversation(pageId, senderId);
    }

    @RequestMapping(value = "/messenger/getNumberOfUnreadMessageInPage", method = RequestMethod.GET)
    public Integer getNumberOfUnreadMessageInPage(@RequestParam("pageId") String pageId) {
        return messageService.getNumberOfUnreadMessageInPage(pageId);
    }

    @RequestMapping(value = "/messenger/getTicketByMessage", method = RequestMethod.POST)
    public List<TicketEntity> getTicketByMessage(@RequestParam("messageId") String messageId) {
        return ticketService.getTicketByMessageId(messageId);
    }
}
