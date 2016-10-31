package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.TicketconversationEntity;
import com.scc.ticketmanagement.repositories.TicketConversationRepository;
import com.scc.ticketmanagement.services.TicketConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 9/30/2016.
 */
@Service
public class TicketConversationImp implements TicketConversationService {

    @Autowired
    TicketConversationRepository ticketConversationRepository;

    @Override
    public List<TicketconversationEntity> getTicketConversation(Integer ticketid){
        return ticketConversationRepository.getTicketConversation(ticketid);
    }
}
