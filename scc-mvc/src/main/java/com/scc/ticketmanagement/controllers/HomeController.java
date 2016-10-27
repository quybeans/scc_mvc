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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping("/doLogin")
    public String doLogin(Model model,
                          HttpSession session,
                          @RequestParam("txtUsername") String username,
                          @RequestParam("txtPassword") String password) {

        UserEntity user = userService.getUserByUsername(username, password);
        if (user == null) {
            model.addAttribute("error", "Incorrect username or password");
            return "login";
        }

        session.setAttribute("username", user.getUsername());

        if (user.getProfileid() != null) {
            ProfileEntity profileEntity = profileService.getProfileByID(user.getProfileid());

            session.setAttribute("fullname", profileEntity.getFirstname() + " " + profileEntity.getLastname());


        }
        if (user.getRoleid() == Constant.ROLE_ADMIN) {
            return "redirect:admin";
        } else if (user.getRoleid() == Constant.ROLE_STAFF) {
            return "CustomerCare";
        } else if (user.getRoleid() == Constant.ROLE_SUPERVISOR) {
            return "Report";
        }
        return "404";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String username = (String) session.getAttribute("username");

            UserEntity user = userService.getUserByUsername(username);
            if (user != null) {
                switch (user.getRoleid()) {
                    case Constant.ROLE_ADMIN:
                        return "redirect:admin";
                    case Constant.ROLE_STAFF:
                        return "CustomerCare";
                    case Constant.ROLE_SUPERVISOR:
                        return "Report";
                }//end switch
            }//end if user
        }//end if session
        return "login";
    }

    @RequestMapping("/LogOut")
    public String LogOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "login";
    }

//    @RequestMapping("/loginViaFB")
//    public String loginViaFB(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session  == null){
//            return "redirect:/login";
//        }
//        return "loginViaFB";
//    }
//
//    @RequestMapping(value = "/loginViaFacebook", method = RequestMethod.POST)
//    String loginFb(HttpServletRequest request,
//            @RequestParam("shortLivedToken") String token,
//                   @RequestParam("uid") String uid,
//                   @RequestParam("fbUsername") String username) {
//        String longLivedToken = "";
//
//        HttpSession session = request.getSession(false);
//        if (session  == null){
//            return "redirect:/login";
//        }
//        String currentUser = (String) session.getAttribute("username");
//        UserEntity user = userService.getUserByUsername(currentUser);
//        int userId =  user.getUserid();
//
//        System.out.println("USername: " + username);
//        try {
//            longLivedToken = AccessTokenUtility.getExtendedAccessToken(token);
//            fbService.createFacebookaccount(uid, longLivedToken, userId,username);
//        } catch (AuthenticationException e) {
//            e.printStackTrace();
//        }
//        System.out.println(longLivedToken);
//        return "redirect:admin";
//    }

    @RequestMapping("/engagement")
    public String home() {
        return "engagement";
    }

}
