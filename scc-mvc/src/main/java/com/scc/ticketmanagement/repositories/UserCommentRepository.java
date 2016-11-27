package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.UserCommentEntity;
import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.UserCommentEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by quybeans on 21/11/2016.
 */
public interface UserCommentRepository extends JpaRepository<UserCommentEntity, UserCommentEntityPK> {

//    //Named Query
//
//    //Custom Query
//    @Query("SELECT COUNT(uc) FROM UserCommentEntity uc WHERE uc.userid=:userid")
//    long countAllCommentByUserId(@Param("userid") int userid);
//
    @Query("SELECT uc.userid FROM UserCommentEntity uc WHERE uc.commentid =:cmtId")
    int findUserIdByComment(@Param("cmtId") String cmtId);

    @Query("SELECT COUNT(uc) FROM UserCommentEntity uc WHERE uc.userid = :userid AND uc.commentid IN"+
    "(SELECT c.id FROM CommentEntity c WHERE c.createdAt between :timestart AND :timeend)")
    int countCommentMadeByUserByTime(@Param("userid") int userId, @Param("timestart") Timestamp timeStart, @Param("timeend") Timestamp timeEnd);
}
