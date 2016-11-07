package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by QuyBean on 11/4/2016.
 */

@RestController
public class CommentRESTController {

    @Autowired
    CommentService commentService;

    @RequestMapping("comment/bypostid")
    public List<CommentEntity> getAllComment(int page, String postid)
    {
        Page<CommentEntity> result =  commentService.getCommentByPostId(page,postid);
        return result.getContent();
    }

    @RequestMapping("comment/bypostid/count")
    public long countComment(String postid)
    {
        Page<CommentEntity> result =  commentService.getCommentByPostId(1,postid);
        return result.getTotalPages();
    }

    @RequestMapping("comment/bypostid/negSort")
    public List<CommentEntity> commentNegSort(String postid, int page) {
        Page<CommentEntity> result =  commentService.getCommentByPostIdwSort(page,1,postid);
        return result.getContent();
    }
}
