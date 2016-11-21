package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.PostEntity;
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
    //Named Query
    Page<CommentEntity> findByPostIdOrderByCreatedAtDesc(String postId,Pageable pageable);
    Page<CommentEntity> findByPostIdAndContentLike(String postId, String content, Pageable pageable);
    //Custom query
    //find user by username and password
    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId")
    List<CommentEntity> findCommentByPostId(@Param("postId") String postId);

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId")
    Page<CommentEntity> findAllByPostId (@Param("postId")String postid, Pageable pageable);

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId " +
            "ORDER BY CASE WHEN (u.sentimentScore = 2) THEN -2 when (u.sentimentScore =3) THEN -1 ELSE u.sentimentScore END")
    Page<CommentEntity> findAllByPostIdwNegSort(@Param("postId")String postid,
                                             Pageable pageable);

    @Query("SELECT u FROM CommentEntity u WHERE u.postId = :postId order by u.sentimentScore DESC")
    List<CommentEntity> findCommentByPostIdSen(@Param("postId") String postId);

    @Query("SELECT COUNT(*) FROM CommentEntity u WHERE u.postId= :postId")
    int countReplyByPostId(@Param("postId") String postId);

    @Query("SELECT COUNT(*) FROM CommentEntity c where c.postId in (SELECT p.id FROM PostEntity p where p.createdBy = :pageId)")
    long countAllCommentFromPage(@Param("pageId") String pageId);

    @Query("SELECT COUNT(*) from CommentEntity c  where c.postId in(" +
            "(select p.id from PostEntity p where p.createdBy in" +
            "(SELECT bp.pageid from BrandpageEntity bp where bp.brandid= :brandid)))")
    long countCommentByBrandId(@Param("brandid") int brandId);

    @Query("SELECT COUNT(*) from CommentEntity c  where c.sentimentScore =:sentiment AND c.postId in(" +
            "(select p.id from PostEntity p where p.createdBy in" +
            "(SELECT bp.pageid from BrandpageEntity bp where bp.brandid =:brandid)))")
    long countCommentByBrandIdwSen(@Param("brandid") int brandId,@Param("sentiment") int sentiment);
}
