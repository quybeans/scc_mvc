package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.ContactEntity;
import com.scc.ticketmanagement.Entities.MessageitemEntity;
import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.Entities.TicketitemEntity;
import com.scc.ticketmanagement.ServiceImp.TicketIteamServiceImp;
import com.scc.ticketmanagement.exentities.Conversation;
import com.scc.ticketmanagement.repositories.ContactRepository;
import com.scc.ticketmanagement.services.MessageItemService;
import com.scc.ticketmanagement.services.MessageService;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thien on 11/2/2016.
 */
@RestController
public class MessengerRESTController {

    @Autowired
    MessageService messageService;

    @Autowired
    PageService pageService;

    @Autowired
    ContactRepository contactService;

    @Autowired
    MessageItemService messageItemService;

    @Autowired
    TicketIteamServiceImp ticketIteamServiceImp;

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

    @RequestMapping(value = "/messenger/getConversationBySenderIdAndPageId", method = RequestMethod.POST)
    public List<MessageEntity> getAllConversations(@RequestParam("pageId") String pageId,
                                                   @RequestParam("senderId") String senderId,
                                                   @RequestParam("pageNum") Integer pageNum) {
        Page<MessageEntity> messages = messageService.getMessageDescWithPage(pageId, senderId, pageNum);
        messages.getTotalPages();
        return messages.getContent();
    }

    @RequestMapping(value = "/messenger/getConversationBySenderIdWithPage", method = RequestMethod.POST)
    public List<MessageEntity> getConversationBySenderIdWithPage(@RequestParam("pageId") String pageId,
                                                                 @RequestParam("senderId") String senderId,
                                                                 @RequestParam("pageNum") Integer pageNum) {
        Page<MessageEntity> messages = messageService.getMessageDescWithPageSize(pageId, senderId, pageNum);

        return messages.getContent();
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
                                        @RequestParam("messageId") String messageId) {
        System.out.println(ticketId);
        System.out.println(messageId);
        TicketitemEntity result = null;
        MessageitemEntity messageitemEntity = messageItemService.startTicket(messageId);
        if (messageitemEntity != null) {
            result = ticketIteamServiceImp.addMessageItemToTicket(ticketId, messageitemEntity.getItemId());

        }
        return result;
    }
}
