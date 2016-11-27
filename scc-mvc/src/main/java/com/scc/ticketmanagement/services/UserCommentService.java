package com.scc.ticketmanagement.services;
import java.sql.Timestamp;


/**
 * Created by quybeans on 21/11/2016.
 */
public interface UserCommentService {
   long countAllCommentByUserId(int userId);
   int getCommmentMadeByUserId(String cmtId);
   long countCommentMadeByUserByDate(int userId, Timestamp date);

}
