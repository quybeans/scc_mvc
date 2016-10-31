package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.BrandEntity;
import com.scc.ticketmanagement.repositories.BrandRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by QuyBean on 10/31/2016.
 */

@RestController
public class BrandRESTController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    UserService userService;

    @RequestMapping("brand/getBrandInfo")
    public BrandEntity getBrandInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {

            String username = (String) session.getAttribute("username");
            if (username != null) {
                return brandRepository.findOne(userService.getBrandIdByUsername(username));
            }
        }
        return null;
    }

}
