package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.TicketitemEntity;
import com.scc.ticketmanagement.repositories.TicketitemRepository;
import com.scc.ticketmanagement.services.TicketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Thien on 11/10/2016.
 */
@Service
public class TicketIteamServiceImp implements TicketItemService {
    @Autowired
    TicketitemRepository ticketitemRepository;

    @Override
    public TicketitemEntity addMessageItemToTicket(Integer ticketid, Integer messageItemId) {
        TicketitemEntity item = new TicketitemEntity();
        item.setCommentid("0");
        item.setPostid("0");
        item.setCreatedAt(new Timestamp(new Date().getTime()));
        item.setMessageid(messageItemId);
        item.setTicketid(ticketid);
        return ticketitemRepository.saveAndFlush(item);
    }

    @Override
    public TicketitemEntity getTicketItemByCommentId(String cmtId) {
        return ticketitemRepository.getTicketItemByCommentID(cmtId);
    }

    @Override
    public TicketitemEntity addCommentItemToTicket(Integer ticketId, String commentId, int createdBy) {
        TicketitemEntity item = new TicketitemEntity();
        item.setCommentid(commentId);
        item.setMessageid(0);
        item.setCreatedAt(new Timestamp(new Date().getTime()));
        item.setPostid("0");
        item.setTicketid(ticketId);
        item.setCreateBy(createdBy);

        return ticketitemRepository.saveAndFlush(item);
    }
}
