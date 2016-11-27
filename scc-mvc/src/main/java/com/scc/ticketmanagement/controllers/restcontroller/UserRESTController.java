package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.exentities.ExUser;
import com.scc.ticketmanagement.repositories.BrandRepository;
import com.scc.ticketmanagement.repositories.UserCommentRepository;
import com.scc.ticketmanagement.services.ProfileService;
//import com.scc.ticketmanagement.services.UserCommentService;
import com.scc.ticketmanagement.services.UserCommentService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by QuyBean on 11/10/2016.
 */

@RestController
public class UserRESTController {

    @Autowired
    private UserService userService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserCommentRepository userCommentRepository;


    @RequestMapping("user/getUserDetail")
    public UserEntity getUser(int userId) {
        UserEntity userEntity = userService.getUserByID(userId);
        userEntity.setPassword("");
        return userEntity;
    }

    @RequestMapping("user/profile")
    public ProfileEntity getUserProfile(int userId) {
        UserEntity userEntity = userService.getUserByID(userId);
        return profileService.getProfileByID(userId);
    }

    @RequestMapping("user/switchActive")
    public void switchActiveUser(int userId) {
        UserEntity userEntity = userService.getUserByID(userId);
        if (userEntity.isActive())
            userService.changeActive(userEntity.getUserid(), false);
        else userService.changeActive(userEntity.getUserid(), true);
    }

    @RequestMapping("user/checkExistedUsername")
    public boolean checkUsername(String username) {
        if (userService.getUserByUsername(username) != null) return true;
        else return false;
    }

    @RequestMapping("user/getAllUser")
    public List<ExUser> checkUsername(HttpServletRequest request) {
        try {
            if (request.getSession(false).getAttribute("username").equals("admin")) {
                List<UserEntity> listold = userService.findAll();
                List<ExUser> listnew = new ArrayList<>();
                for (UserEntity user : listold) {
                    ExUser newU = new ExUser();
                    newU.setBrandid(user.getBrandid());
                    newU.setPassword(user.getPassword());
                    newU.setCreatedby(user.getCreatedby());
                    newU.setCreatedByName(userService.getUserByID(user.getCreatedby()).getUsername());
                    newU.setBrandid(user.getBrandid());
                    newU.setBrandIdName(brandRepository.findOne(user.getBrandid()).getName());
                    newU.setActive(user.isActive());
                    newU.setUsername(user.getUsername());
                    newU.setPassword(user.getPassword());
                    newU.setRoleid(user.getRoleid());
                    newU.setUserid(user.getUserid());

                    listnew.add(newU);
                }
                return listnew;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    @RequestMapping("user/countcoummentlast7day")
    public List<Long> countCmtLast7Days(int userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = Calendar.getInstance().getTime();
        dateFormat.format(date);
        List<Long> rs = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, 1);
        for (int i = 0; i <= 6; i++) {
            cal.add(Calendar.DAY_OF_YEAR, -1);
            rs.add(countComment(userId,dateFormat.format(cal.getTime())));
        }
        Collections.reverse(rs);
        return rs;
    }

    @RequestMapping("user/countAllComment")
    public long countComment(int userId, String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date parsedDate = dateFormat.parse(date);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());


            Calendar cal = Calendar.getInstance(); // locale-specific
            cal.setTime(timestamp);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 59);

            Calendar cal2 = Calendar.getInstance(); // locale-specific
            cal2.setTime(timestamp);
            cal2.set(Calendar.HOUR_OF_DAY, 0);
            cal2.set(Calendar.MINUTE, 0);
            cal2.set(Calendar.SECOND, 0);
            cal2.set(Calendar.MILLISECOND, 0);


            return userCommentRepository.countCommentMadeByUserByTime(userId, new Timestamp(cal2.getTimeInMillis()), new Timestamp(cal.getTimeInMillis()));

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
