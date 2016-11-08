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
}
