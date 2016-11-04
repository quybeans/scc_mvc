package com.scc.ticketmanagement.repositories;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.PostEntity;
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

    @Query("SELECT COUNT(c) from CommentEntity c where c.sentimentScore!=1 and c.postId= :postId")
    int findNegCountByPostId(@Param("postId") String postId);
}
