package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.NotificationEntity;
import com.scc.ticketmanagement.repositories.NotificationRepository;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by QuyBean on 11/2/2016.
 */

@RestController
public class NotificationRESTController {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;

    @RequestMapping("/notification/unread_count")
    public int countUnread(HttpServletRequest request) {

        int count = 0;
        int userid = getUseridBySession(request);
        if (userid != 0) {
            List<NotificationEntity> list = notificationRepository.finaAllByUserid(userid);
            for (NotificationEntity noti : list) {
                if (!noti.getReadStatus())
                    count++;
            }
        }
        return count;
    }

    @RequestMapping("/notification/getunread")
    public List<NotificationEntity> getAllNoti(HttpServletRequest request)
    {
        try{
        int userid = getUseridBySession(request);
        return notificationRepository.finaAllByUserid(userid);}
        catch (NullPointerException e)
        {
            return  null;
        }
    }

    public int getUseridBySession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (session.getAttribute("username") != null) {
                int userid = userService.getUserByUsername((String) session.getAttribute("username")).getUserid();
                return userid;
            }
        }
        return 0;
    }
}
