package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.UserMessageEntity;
import com.scc.ticketmanagement.repositories.UserMessageRepository;
import com.scc.ticketmanagement.services.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thien on 11/26/2016.
 */
@Service
public class UserMessageServiceImp implements UserMessageService {

    @Autowired
    UserMessageRepository userMessageRepository;

    @Override
    public UserMessageEntity create(String messageId, int userId) {
        UserMessageEntity mapping = new UserMessageEntity();
        mapping.setMessageId(messageId);
        mapping.setUserId(userId);
        return userMessageRepository.save(mapping);
    }

    @Override
    public UserMessageEntity get(String messageId) {
        return userMessageRepository.getUserMessageByMessageId(messageId);
    }
}
