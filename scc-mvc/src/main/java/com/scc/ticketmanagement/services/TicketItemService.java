package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.TicketitemEntity;

/**
 * Created by Thien on 11/10/2016.
 */
public interface TicketItemService {
    TicketitemEntity addMessageItemToTicket(Integer ticketid, Integer messageItemId);

    TicketitemEntity getTicketItemByCommentId(String cmtId);

    TicketitemEntity addCommentItemToTicket(Integer ticketId, String commentId, int createdBy);
}
