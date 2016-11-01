package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 10/1/2016.
 */
public interface CommentRepository extends JpaRepository<CommentEntity,String> {

    //find user by username and password
    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId")
    List<CommentEntity> findCommentByPostId(@Param("postId") String postId);

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId order by u.sentimentScore DESC")
    List<CommentEntity> findCommentByPostIdSen(@Param("postId") String postId);


}
