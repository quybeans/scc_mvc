package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId")
    Page<CommentEntity> findAllByPostId (@Param("postId")String postid, Pageable pageable);

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId " +
            "ORDER BY CASE WHEN (u.sentimentScore = 2) THEN -1 ELSE u.sentimentScore END")
    Page<CommentEntity> findAllByPostIdwNegSort(@Param("postId")String postid,
                                             Pageable pageable);

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId order by u.sentimentScore DESC")
    List<CommentEntity> findCommentByPostIdSen(@Param("postId") String postId);

}
