package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.repositories.CommentRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by user on 10/31/2016.
 */
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/getcommentbyid")
    public CommentEntity getcommentbyid(@RequestParam("commentid") String commentid){
        CommentEntity Comment = commentRepository.findOne(commentid);
        return Comment;
    }

    @RequestMapping("/getreply")
    public List<CommentEntity> getreply(@RequestParam("commentid") String commentid){
        return commentRepository.findCommentByPostId(commentid);
    }


}
