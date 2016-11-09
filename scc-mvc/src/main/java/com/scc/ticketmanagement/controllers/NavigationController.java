package com.scc.ticketmanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by QuyBean on 11/9/2016.
 */

@Controller
public class NavigationController {

    @RequestMapping("/brand/index")
    public String LogOut(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return "brand/index";
    }
}
