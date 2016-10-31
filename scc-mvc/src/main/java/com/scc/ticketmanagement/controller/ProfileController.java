package com.scc.ticketmanagement.controller;

import com.scc.ticketmanagement.entity.ProfileEntity;
import com.scc.ticketmanagement.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by user on 10/5/2016.
 */
@Controller
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @RequestMapping("/UserProfile")
    public String getUserProfile(@RequestParam("profileid") Integer profileID,
                                 Model model){
        ProfileEntity profileEntity = profileService.getProfileByID(profileID);
        model.addAttribute("profile",profileEntity);
        return "Profile";
    }

    @RequestMapping("/UpdateProfile")
    public String updateProfile(@RequestParam("txtFirstName") String firstname,
                                @RequestParam("txtLastName") String lastname,
                                @RequestParam("txtAddress") String address,
                                @RequestParam("txtGender") String gender,
                                @RequestParam("txtEmail") String email,
                                @RequestParam("txtPhone") String phone,
                                @RequestParam("profileid") Integer profileid){
        profileService.updateProfile(profileid,firstname,lastname,address,gender,phone,email);
        return "redirect:UserProfile?profileid="+profileid;
    }
}
