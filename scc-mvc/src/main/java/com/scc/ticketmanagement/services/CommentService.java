package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.CommentEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by user on 10/4/2016.
 */
public interface CommentService {
    CommentEntity getCommentByID(String id);

    List<CommentEntity> findAll();

    Page<CommentEntity> getCommentPage(Integer pagenumber);

    Page<CommentEntity> getCommentByPostId(int pagenumber, String postid);

    Page<CommentEntity> getCommentByPostIdwSort(int pagenumber, Integer sortBy, String postid);

    Page<CommentEntity> getCommentByPostIdwTimeSort(int pagenumber, String postid);

    Page<CommentEntity> searchCommentByPostIdandContent(int pagenumber,String postid, String content);

    int countReply(String postid);
}
