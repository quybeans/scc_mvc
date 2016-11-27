package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.ProfileRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by user on 10/5/2016.
 */
@Controller
public class ProfileController {
    @Autowired
    UserService userService;

    @Autowired
    ProfileService profileService;

    @RequestMapping("/profile/index")
    public String getUserProfile(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);
        String returnUrl = "redirect:/login";
        if (session!=null){
            String currentUsername =  (String) session.getAttribute("username");
            if (currentUsername != null){
                int userId = userService.getUserByUsername(currentUsername).getUserid();
                ProfileEntity profileEntity = profileService.getProfileByID(userId);
                model.addAttribute("profiles",profileEntity);
                returnUrl = "/profile/index";
            }

        }

        return returnUrl;
    }

    @RequestMapping("/UpdateProfile")
    public String updateProfile(@RequestParam("txtFirstName") String firstname,
                                @RequestParam("txtLastName") String lastname,
                                @RequestParam("txtAddress") String address,
                                @RequestParam("txtGender") int gender,
                                @RequestParam("txtEmail") String email,
                                @RequestParam("txtPhone") String phone,
                                @RequestParam("profileid") Integer profileid){
        profileService.updateProfile(profileid,firstname,lastname,address,gender,phone,email);
        return "redirect:UserProfile?profileid="+profileid;
    }

    @RequestMapping("/getuserprofile")
    @ResponseBody
    public ProfileEntity getuserprofile(@RequestParam("userid") Integer userid){
        UserEntity user = userService.getUserByID(userid);
        return profileService.getProfileByID(user.getUserid());
    }
}
