package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.repositories.UserCommentRepository;
import com.scc.ticketmanagement.services.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by quybeans on 21/11/2016.
 */
public class UserCommentServiceImp implements UserCommentService{

    @Autowired
    UserCommentRepository userCommentRepository;

    @Override
    public long countAllCommentByUserId(int userId) {
        return userCommentRepository.countAllCommentByUserId(userId);
    }
}
