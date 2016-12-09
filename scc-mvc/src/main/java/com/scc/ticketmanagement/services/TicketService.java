package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.MessageEntity;
import com.scc.ticketmanagement.Entities.TicketEntity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface TicketService {

    List<TicketEntity> findAll();
    void changeStatusTicket(Integer ticketID,Integer statusID);
    void deactiveTicket(Integer ticketID);
    void createTicket(Integer createby);
    void assignticket(Integer id,Integer assignee);
    TicketEntity getTicketByID(Integer ticketid);
    List<TicketEntity> getTicketUser(Integer userid);
    List<TicketEntity> getListTicketByConversation(String messageId);
    List<TicketEntity> getListTicketByConversation(List<MessageEntity> conversation);
    Integer countUserClosedTicket(Integer assignee,Timestamp createdtime);
    Integer countUnhandleTicket(Integer assignee);
}
