package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.CommentattributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 10/30/2016.
 */
public interface CommentattributeRepository extends JpaRepository<CommentattributeEntity,Integer> {
    @Query("select a From CommentattributeEntity a where a.commentid=:commentid")
    List<CommentattributeEntity> getAttributebyCommentID(@Param("commentid") String commentid);

    @Query("select a From CommentattributeEntity a where a.commentid=:commentid and a.attributeid=:attributeid")
    CommentattributeEntity get1ByCommentIDandAttID(@Param("commentid") String commentid,
                                                   @Param("attributeid") Integer attributeid );

    @Query("select a From CommentattributeEntity a where a.attributeid=:attributeid")
    List<CommentattributeEntity> findByAttID(@Param("attributeid") Integer attributeid);
}
