package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.repositories.UserCommentRepository;
import com.scc.ticketmanagement.services.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by quybeans on 21/11/2016.
 */
public class UserCommentServiceImp implements UserCommentService{

    @Autowired
    UserCommentRepository userCommentRepository;

    @Override
    public long countAllCommentByUserId(int userId) {
//        return userCommentRepository.countAllCommentByUserId(userId);
        return 0;
    }

    @Override
    public int getCommmentMadeByUserId(String cmtId) {
        return userCommentRepository.findUserIdByComment(cmtId);
    }

    @Override
    public long countCommentMadeByUserByDate(int userId, Timestamp date) {

//        System.out.println(date.toString());
//        Calendar cal = Calendar.getInstance(); // locale-specific
//        cal.setTime(date);
//        cal.set(Calendar.HOUR_OF_DAY, 23);
//        cal.set(Calendar.MINUTE, 59);
//        cal.set(Calendar.SECOND, 59);
//        cal.set(Calendar.MILLISECOND, 59);
//        System.out.println(cal.toString());
//        return userCommentRepository.countCommentMadeByUserByTime(userId, date, new Timestamp(cal.getTimeInMillis()));
        return 0;
    }
}
