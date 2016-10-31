package com.scc.ticketmanagement.service;

import com.scc.ticketmanagement.entity.TicketconversationEntity;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
public interface TicketConversationService {
    List<TicketconversationEntity> getTicketConversation(Integer ticketid);
}
