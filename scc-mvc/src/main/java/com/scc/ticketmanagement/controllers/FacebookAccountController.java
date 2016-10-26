package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.services.FacebookaccountService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by user on 10/9/2016.
 */
@Controller
public class FacebookAccountController {
    @Autowired
    FacebookaccountService fbService;

    @RequestMapping("/manageFacebookAccount")
    public String manageFb(HttpServletRequest request, Model model)
    {
        HttpSession session = request.getSession();
        UserEntity userEntity=(UserEntity) session.getAttribute("user");
        model.addAttribute("accountList",fbService.getFacebookaccountsByUserID(userEntity.getUserid()));
        return "manageFacebookAccount";
    }

    @RequestMapping("/deactivateFbAccount")
    public String deactivate(@RequestParam("uid") String uid){
        fbService.deactivateFbAccount(uid);
        return "manageFacebookAccount";
    }
}
