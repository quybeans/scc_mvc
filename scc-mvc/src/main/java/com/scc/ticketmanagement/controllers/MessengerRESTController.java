package com.scc.ticketmanagement.controllers;

import com.google.api.client.json.Json;
import com.scc.ticketmanagement.Entities.ContactEntity;
import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.exentities.Conversation;
import com.scc.ticketmanagement.facebook.Contact;
import com.scc.ticketmanagement.repositories.ContactRepository;
import com.scc.ticketmanagement.services.ContactService;
import com.scc.ticketmanagement.services.MessageService;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.utilities.AccessTokenUtility;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
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

    @RequestMapping(value = "/getAllConversations", method = RequestMethod.GET)
    public List<MessageEntity> getAllConversations() {
        return messageService.getAllConversations();
    }

    @RequestMapping(value = "/messenger/getAllConversationsByPageId", method = RequestMethod.GET)
    public List<Conversation> getAllConversations(@RequestParam("pageId") String pageId) {
        return messageService.getAllConversationsByPageId(pageId);
    }

    @RequestMapping(value = "/messenger/getConversationBySenderIdAndPageId", method = RequestMethod.POST)
    public List<MessageEntity> getAllConversations(@RequestParam("pageId") String pageId,
                                                   @RequestParam("senderId") String senderId) {
        return messageService.getMessageAsc(pageId, senderId);
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

}
