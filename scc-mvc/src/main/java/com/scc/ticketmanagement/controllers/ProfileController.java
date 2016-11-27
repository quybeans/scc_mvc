package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.ProfileRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by user on 10/5/2016.
 */
@Controller
public class ProfileController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ProfileService profileService;

    @RequestMapping("/profile/index")
    public String getUserProfile(@RequestParam("profileid") Integer profileID,
                                 Model model){
        ProfileEntity profileEntity = profileService.getProfileByID(profileID);
        model.addAttribute("profiles",profileEntity);
        return "/profile/index";
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
        UserEntity user = userRepository.findOne(userid);
        return profileRepository.findOne(user.getUserid());
    }
}
