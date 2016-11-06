package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.repositories.CommentRepository;
import com.scc.ticketmanagement.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by user on 10/4/2016.
 */
@Service
@Transactional
public class CommentServiceImp implements CommentService{
    private static final int PAGE_SIZE = 15;

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

    @Override
    public Page<CommentEntity> getCommentPage(Integer pagenumber){
        PageRequest request = new PageRequest(pagenumber -1, PAGE_SIZE, Sort.Direction.DESC,"sentimentScore");
        return commentRepository.findAll(request);
    }

    @Override
    public Page<CommentEntity> getCommentByPostId(int pagenumber, String postid) {
        PageRequest request = new PageRequest(pagenumber -1, PAGE_SIZE, Sort.Direction.DESC,"sentimentScore");
        return commentRepository.findAllByPostId(postid,request);

    }
}
