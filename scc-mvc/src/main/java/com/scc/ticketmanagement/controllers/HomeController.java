package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.services.FacebookaccountService;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.Constant;
import com.scc.ticketmanagement.utilities.AccessTokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Thien on 9/23/2016.
 */
@Controller
public class HomeController {
    @Autowired
    ProfileService profileService;

    @Autowired
    UserService userService;
    @Autowired
    FacebookaccountService fbService;




    @RequestMapping("/testadmin")
    public String test() {
        return "Admins";
    }

    @RequestMapping("/login")
    public String login() {
        return "Login";
    }

    @RequestMapping("/Head")
    public String head() {
        return "Head";
    }

    @RequestMapping("/SideBar")
    public String sidebar() {
        return "SideBar";
    }

    @RequestMapping("/MasterPage")
    public String masterpage() {
        return "MasterPage";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    @RequestMapping("/loginacc")
    public String loginacc(HttpSession session
            , @RequestParam("txtUsername") String username, @RequestParam("txtPassword") String password) {
        UserEntity userEntity = userService.findUser(username, password);
        if(userEntity==null){
            return "login";
        }

        session.setAttribute("username", userEntity.getUsername());

        if (userEntity.getProfileid()!=null) {
            ProfileEntity profileEntity = profileService.getProfileByID(userEntity.getProfileid());

            session.setAttribute("fullname", profileEntity.getFirstname() + " " + profileEntity.getLastname());


        }
        if(userEntity.getRoleid()== Constant.ROLE_ADMIN){
            return "redirect:admin";
        }else if (userEntity.getRoleid() == Constant.ROLE_STAFF) {
            return "CustomerCare";
        }else if (userEntity.getRoleid() == Constant.ROLE_SUPERVISOR) {
            return "Report";
        }
        return "404";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session!=null){
            UserEntity userEntity =(UserEntity) session.getAttribute("user");
            if(userEntity!=null){
                if(userEntity.getRoleid()== Constant.ROLE_ADMIN){
                    return "redirect:admin";
                }else if (userEntity.getRoleid() == Constant.ROLE_STAFF) {
                    return "CustomerCare";
                }else if (userEntity.getRoleid() == Constant.ROLE_SUPERVISOR) {
                    return "Report";
                }
            }
            return "login";
        }
        return "login";

    }

    @RequestMapping("/LogOut")
    public String LogOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session!=null){
            session.invalidate();
        }
        return "login";
    }

    @RequestMapping("/loginViaFB")
    public String loginViaFB() {
        return "loginViaFB";
    }

    @RequestMapping(value = "/loginViaFacebook")
    String loginFb(@RequestParam("shortLivedToken") String token,@RequestParam("uid") String uid) {
        String longLivedToken = "";
        int userId = 1;
        try {
            longLivedToken =  AccessTokenUtility.getExtendedAccessToken(token);
            fbService.createFacebookaccount(uid,longLivedToken,userId);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println(longLivedToken);
        return "redirect:admin";
    }

    @RequestMapping("/engagement")
    public String home()
    {
        return "engagement";
    }

}
