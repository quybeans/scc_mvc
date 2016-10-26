package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.repositories.CommentRepository;
import com.scc.ticketmanagement.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by user on 10/4/2016.
 */
@Service
public class CommentImp implements CommentService{
    @Autowired
    CommentRepository commentRepository;

    @Override
    public CommentEntity getCommentByID(Integer id) {
        return commentRepository.findOne(id);
    }

    @Override
    public List<CommentEntity> findAll() {
        return commentRepository.findAll();
    }
}
