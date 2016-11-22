package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.UserCommentEntity;
import com.scc.ticketmanagement.Entities.UserCommentEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by quybeans on 21/11/2016.
 */
public interface UserCommentRepository extends JpaRepository<UserCommentEntity, UserCommentEntityPK> {

    //Named Query

    //Custom Query
    @Query("SELECT COUNT(uc) FROM UserCommentEntity uc WHERE uc.userid=:userid")
    long countAllCommentByUserId(@Param("userid") int userid);

    @Query("SELECT uc.userid FROM UserCommentEntity uc WHERE uc.commentid =:cmtId")
    int findUserIdByComment(@Param("cmtId") String cmtId);

}
