package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.ServiceImp.TicketIteamServiceImp;
import com.scc.ticketmanagement.exentities.Conversation;
import com.scc.ticketmanagement.exentities.ExMessage;
import com.scc.ticketmanagement.exentities.ExtendTicket;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.services.*;
import com.scc.ticketmanagement.utilities.Constant;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * Created by Thien on 11/2/2016.
 */
@RestController
public class MessengerRESTController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    PageService pageService;

    @Autowired
    private TicketitemRepository ticketitemRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    PriorityReposioty priorityReposioty;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    MessageItemService messageItemService;

    @Autowired
    private TicketIteamServiceImp ticketIteamServiceImp;

    @Autowired
    TicketService ticketService;

    @Autowired
    UserMessageService userMessageService;

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
        UserMessageEntity user = null;
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
            exMessage.setTicket(ticketService.getListTicketByConversation(message.getId()).size() != 0);
            user = userMessageService.get(message.getId());
            if (user!=null){
                exMessage.setSendBy(userService.getUserByID(user.getUserId()).getUsername());
            }
            result.add(exMessage);
        }

        return result;
    }

    @RequestMapping(value = "/messenger/sendMessageToCustomer", method = RequestMethod.POST)
    public String sendMessage(HttpServletRequest request, @RequestParam("pageId") String pageId,
                              @RequestParam("receiverId") String receiverId,
                              @RequestParam("content") String content) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            int currentUserId = this.getCurrentUserId(session);

            String accessToken = pageService.getPageAccessTokenByPageId(pageId);
            String sentMessage = "";
            if (accessToken != null && !accessToken.equals("")) {
                try {
                    sentMessage = FacebookUtility.sendMessage(content, receiverId, accessToken);
                    userMessageService.create(sentMessage,currentUserId );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return sentMessage;
        }


        return null;
    }

    @RequestMapping(value = "/messenger/getCustomerInfo", method = RequestMethod.POST)
    public ContactEntity getCustomerInfo(@RequestParam("customerId") String customerId) {
        return contactRepository.getContactById(customerId);
    }

    @RequestMapping(value = "/messenger/getProfileId", method = RequestMethod.GET)
    public String getProfileId(@RequestParam("avtId") String avtId) throws AuthenticationException {
        return FacebookUtility.getProfileId(avtId);
    }

    @RequestMapping(value = "/messenger/setMessageRead", method = RequestMethod.POST)
    public MessageEntity setRead(@RequestParam("pageId") String pageId,
                                 @RequestParam("senderId") String senderId) {
        MessageEntity messageEntity = messageService.getLastMessage(pageId, senderId);
        if (messageEntity != null && !messageEntity.getMessageRead()) {
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
        UserEntity user = userService.getUserByUsername(loginUser);
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
    public List<ExtendTicket> getTicketByMessage(@RequestParam("messageId") String messageId) {
        return getExtendTicketList(ticketService.getListTicketByConversation(messageId));
    }

    @RequestMapping(value = "/messenger/getListTicketByConversation", method = RequestMethod.GET)
    public List<TicketEntity> getTicketByMessage(@RequestParam("senderId") String senderId,
                                                 @RequestParam("pageId") String pageId) {
        List<MessageEntity> conversation = messageService.getMessageAsc(pageId, senderId);
        List<TicketEntity> result = ticketService.getListTicketByConversation(conversation);

        for (ListIterator<TicketEntity> iterator = result.listIterator(); iterator.hasNext(); ) {
            TicketEntity ticket = iterator.next();
            if (Collections.frequency(result, ticket) > 1) {
                iterator.remove();
            }
        }
        Comparator<TicketEntity> comp = (TicketEntity a, TicketEntity b) -> {
            return b.getCreatedtime().compareTo(a.getCreatedtime());
        };

        Collections.sort(result, comp);
        return result;
    }

    @RequestMapping(value = "/messenger/saveNote", method = RequestMethod.POST)
    public Integer saveNote(@RequestParam("customerId") String customerId, @RequestParam("content") String content) {
        return contactRepository.saveNote(customerId, content);
    }

    @RequestMapping(value = "/getNumberOfMessageInBrand", method = RequestMethod.GET)
    public int countMessageInBrand(@RequestParam("brandId") int brandId) {
        return messageService.getNumberOfMessageInBrand(brandId);
    }

    @RequestMapping(value = "/getNumberOfMessageInPage", method = RequestMethod.GET)
    public int counutMessageInPage(@RequestParam("pageId") String pageId) {
        return messageService.getNumberOfMessageInPage(pageId);
    }


    private List<ExtendTicket> getExtendTicketList(List<TicketEntity> ticketlist) {
        List<ExtendTicket> listextendticket = new ArrayList<ExtendTicket>();
        for (TicketEntity tk : ticketlist) {
            ExtendTicket extendticket = new ExtendTicket();
            extendticket.setCountitem(ticketitemRepository.counTicketItem(tk.getId()));
            extendticket.setAssignee(tk.getAssignee());
            extendticket.setName(tk.getName());
            extendticket.setActive(tk.getActive());
            extendticket.setCreatedby(tk.getCreatedby());
            extendticket.setId(tk.getId());
            extendticket.setStatusid(tk.getStatusid());
            extendticket.setCreatedtime(tk.getCreatedtime());
            extendticket.setNote(tk.getNote());


            //Get UserEntity cua ng tao ra ticket
            UserEntity createTicketUser = userService.getUserByID(extendticket.getCreatedby());
            //Get ProfileEntity cua ng tao ra ticket
            ProfileEntity createTicketProfile = profileRepository.findOne(createTicketUser.getUserid());
            //Get Fullname cua ng ta ticket de set vao extendTicket
            extendticket.setCreatebyuser(createTicketProfile.getFirstname() + " " + createTicketProfile.getLastname());

            //Get UserEntity cua ng duoc assign  ticket
            UserEntity assigneeUser = userService.getUserByID(extendticket.getAssignee());
            //Get ProfileEntity cua ng duoc assign  ticket
            ProfileEntity assigneeTicketProfile = profileRepository.findOne(assigneeUser.getUserid());
            //Get Fullname cua ng ta ticket de set vao extendTicket
            extendticket.setAssigneeuser(assigneeTicketProfile.getFirstname() + " " + assigneeTicketProfile.getLastname());
            switch (assigneeUser.getRoleid()) {
                case Constant.ROLE_ADMIN:
                    extendticket.setAssigneerole("Admin");
                    break;
                case Constant.ROLE_BRAND:
                    extendticket.setAssigneerole("Brand Manager");
                    break;
                case Constant.ROLE_STAFF:
                    extendticket.setAssigneerole("Staff");
                    break;
                case Constant.ROLE_SUPERVISOR:
                    extendticket.setAssigneerole("Supervisor");
                    break;
            }

            switch (extendticket.getStatusid()) {
                case Constant.STATUS_ASSIGN:
                    extendticket.setCurrentstatus("Assigned");
                    break;
                case Constant.STATUS_INPROCESS:
                    extendticket.setCurrentstatus("Inprocess");
                    break;
                case Constant.STATUS_CLOSE:
                    extendticket.setCurrentstatus("Close");
                    break;
                case Constant.STATUS_SOLVED:
                    extendticket.setCurrentstatus("Solved");
                    break;
                case Constant.STATUS_EXPIRED:
                    extendticket.setCurrentstatus("Expired");
                    break;
            }

            //lay ten priority cua ticket
            PriorityEntity priority = priorityReposioty.findOne(tk.getPriority());
            extendticket.setCurrentpriority(priority.getName());
            listextendticket.add(extendticket);

        }
        return listextendticket;
    }

    private int getCurrentUserId(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return 0;
        }
        return userService.getUserByUsername(username).getUserid();
    }
}
