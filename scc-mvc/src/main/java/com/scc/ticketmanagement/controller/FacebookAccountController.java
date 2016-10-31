package com.scc.ticketmanagement.controller;

import com.scc.ticketmanagement.entity.FacebookaccountEntity;
import com.scc.ticketmanagement.entity.UserEntity;
import com.scc.ticketmanagement.service.FacebookaccountService;
import com.scc.ticketmanagement.service.UserService;
import com.scc.ticketmanagement.service.UserfacebookaccountService;
import com.scc.ticketmanagement.utility.AccessTokenUtility;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by user on 10/9/2016.
 */
@Controller
public class FacebookAccountController {
    @Autowired
    FacebookaccountService fbService;

    @Autowired
    UserfacebookaccountService userfacebookaccountService;

    @Autowired
    UserService userService;

    @RequestMapping("/facebook-account/index")
    public String manageFb(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        String username = (String) session.getAttribute("username");
        UserEntity user = userService.getUserByUsername(username);
        List<FacebookaccountEntity> facebookAccounts =  fbService.getFacebookAccountsByUserId(user.getUserid());
        model.addAttribute("facebookAccounts", facebookAccounts);


        return "facebook-account/index";
    }

    @RequestMapping(value = "/facebook-account/loginViaFacebook", method = RequestMethod.POST)
    String loginFb(HttpServletRequest request,
                   @RequestParam("shortLivedToken") String token,
                   @RequestParam("uid") String uid,
                   @RequestParam("fbUsername") String username) {
        String longLivedToken = "";

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        String currentUser = (String) session.getAttribute("username");
        UserEntity user = userService.getUserByUsername(currentUser);
        int userId = user.getUserid();

        System.out.println("USername: " + username);
        try {
            longLivedToken = AccessTokenUtility.getExtendedAccessToken(token);
            fbService.createFacebookaccount(uid, longLivedToken, userId, username);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        System.out.println(longLivedToken);
        return "redirect:/facebook-account/index";
    }

    @RequestMapping(value = "/facebook-account/deactivateFbAccount", method = RequestMethod.POST)
    public String deactivate(HttpServletRequest request,
                             @RequestParam("facebookAccountId") int accountId,
                             @RequestParam("btnAction") String button) {
        System.out.println(button);
        HttpSession session = request.getSession(false);
        int userId = -1;
        if (session!=null){
            String username = (String) session.getAttribute("username");
            UserEntity user = userService.getUserByUsername(username);
            userId = user.getUserid();
        }
        if (button.equals("Deactivate")) {
            userfacebookaccountService.deactivate(userId, accountId);
        } else if (button.equals("Activate")) {
            userfacebookaccountService.activate(userId, accountId);
        }
        return "redirect:/facebook-account/index";
    }
}
