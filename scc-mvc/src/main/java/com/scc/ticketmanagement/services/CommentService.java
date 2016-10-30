package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.CommentEntity;

import java.util.List;

/**
 * Created by user on 10/4/2016.
 */
public interface CommentService {
    CommentEntity getCommentByID(String id);
    List<CommentEntity> findAll();
}
