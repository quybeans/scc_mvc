package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.exentities.ExUser;
import com.scc.ticketmanagement.repositories.BrandRepository;
import com.scc.ticketmanagement.repositories.UserCommentRepository;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserCommentService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    public  List<ExUser> checkUsername(HttpServletRequest request) {
        try {
            if (request.getSession(false).getAttribute("username").equals("admin")) {
                List<UserEntity> listold = userService.findAll();
                List<ExUser> listnew = new ArrayList<>();
                for(UserEntity user: listold)
                {
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
            return null;
        }
        return null;
    }

    @RequestMapping("user/countAllComment")
    public long checkUsername(int userid)
    {
        return userCommentRepository.countAllCommentByUserId(userid);
    }

}
