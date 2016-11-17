package com.scc.ticketmanagement.repositories;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<PostEntity, String> {

    //Find All posts by Page

    List<PostEntity> findByCreatedByOrderByCreatedAtDesc(String id);
    List<PostEntity> findByCreatedBy(String id);

    @Query("SELECT COUNT(c) from CommentEntity c where c.sentimentScore=1 and c.postId= :postId")
     int findPosCountByPostId(@Param("postId") String postId);

    @Query("SELECT COUNT(c) from CommentEntity c where c.sentimentScore=3 and c.postId= :postId")
    int findQuestCountByPostId(@Param("postId") String postId);

    @Query("SELECT COUNT(c) from CommentEntity c where c.sentimentScore=2 and c.postId= :postId")
    int findNegCountByPostId(@Param("postId") String postId);

    @Query("SELECT COUNT(c) from PostEntity c where c.createdBy=:pageid")
    int countPostByPageId(@Param("pageid") String pageid);

    @Query("SELECT p from PostEntity p where p.content like %:content%")
    List<PostEntity> findPostByContent(@Param("content")String content);

    Page<PostEntity> findByCreatedByIn(List<String> listpage, Pageable page);

    List<PostEntity> findByCreatedByInAndContentContaining(List<String> listpage, String content);
}
