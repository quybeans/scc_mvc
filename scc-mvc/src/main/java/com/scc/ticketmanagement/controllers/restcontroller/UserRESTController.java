package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by QuyBean on 11/10/2016.
 */

@RestController
public class UserRESTController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @RequestMapping("user/getUserDetail")
    public UserEntity getUser(int userId)
    {
        UserEntity userEntity =userService.getUserByID(userId);
        userEntity.setPassword("");
        return userEntity;
    }

    @RequestMapping("user/profile")
    public ProfileEntity getUserProfile(int userId)
    {
        UserEntity userEntity =userService.getUserByID(userId);
        return  profileService.getProfileByID(userEntity.getProfileid());
    }

    @RequestMapping("user/switchActive")
    public void switchActiveUser(int userId)
    {
        UserEntity userEntity = userService.getUserByID(userId);
        if(userEntity.isActive())
        userService.changeActive(userEntity.getUserid(),false);
        else userService.changeActive(userEntity.getUserid(),true);
    }

    @RequestMapping("user/checkExistedUsername")
    public boolean checkUsername(String username)
    {
       if (userService.getUserByUsername(username)!=null) return true;
        else  return false;
    }

}
