package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.CommentEntity;
import com.scc.ticketmanagement.repository.CommentRepository;
import com.scc.ticketmanagement.service.CommentService;
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
    public CommentEntity getCommentByID(String id) {
        return commentRepository.findOne(id);
    }

    @Override
    public List<CommentEntity> findAll() {
        return commentRepository.findAll();
    }
}
