package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.services.FacebookaccountService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.AccessTokenUtility;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;

/**
 * Created by user on 10/9/2016.
 */
@Controller
public class FacebookAccountController {
    @Autowired
    FacebookaccountService fbService;

    @Autowired
    UserService userService;

    @RequestMapping("/facebook-account/index")
    public String manageFb(HttpServletRequest request, Model model)
    {
        HttpSession session = request.getSession(false);

        String username =(String) session.getAttribute("username");
        UserEntity user = userService.getUserByUsername(username);

        model.addAttribute("facebookAccounts",fbService.getFacebookaccountsByUserID(user.getUserid()));



        return "facebook-account/index";
    }

    @RequestMapping(value = "/facebook-account/loginViaFacebook", method = RequestMethod.POST)
    String loginFb(javax.servlet.http.HttpServletRequest request,
                   @RequestParam("shortLivedToken") String token,
                   @RequestParam("uid") String uid,
                   @RequestParam("fbUsername") String username) {
        String longLivedToken = "";

        HttpSession session = request.getSession(false);
        if (session  == null){
            return "redirect:/login";
        }
        String currentUser = (String) session.getAttribute("username");
        UserEntity user = userService.getUserByUsername(currentUser);
        int userId =  user.getUserid();

        System.out.println("USername: " + username);
        try {
            longLivedToken = AccessTokenUtility.getExtendedAccessToken(token);
            fbService.createFacebookaccount(uid, longLivedToken, userId,username);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println(longLivedToken);
        return "redirect:/facebook-account/index";
    }

    @RequestMapping(value = "/facebook-account/deactivateFbAccount", method = RequestMethod.POST)
    public String deactivate(@RequestParam("facebookAccountId") String accountId){
        fbService.deactivateFbAccount(accountId);
        return "redirect:/facebook-account/index";
    }
}
