package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.MessageitemEntity;
import com.scc.ticketmanagement.repositories.MessageitemRepository;
import com.scc.ticketmanagement.services.MessageItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thien on 11/8/2016.
 */
@Service
public class MessageItemServiceImplement implements MessageItemService{
    @Autowired
    MessageitemRepository repository;

    @Override
    public MessageitemEntity get(int id) {
        return this.repository.get(id);
    }

    @Override
    public MessageitemEntity update(MessageitemEntity messageitemEntity) {
        return this.repository.saveAndFlush(messageitemEntity);
    }

    @Override
    public MessageitemEntity startTicket(String messageId) {
        MessageitemEntity item = new MessageitemEntity();
        item.setMessageIdStart(messageId);
        return repository.saveAndFlush(item);
    }

    @Override
    public MessageitemEntity endTicket(int itemId, String messageId) {
        MessageitemEntity item = this.repository.get(itemId);
        item.setMessageIdEnd(messageId);
        return repository.saveAndFlush(item);
    }

}
