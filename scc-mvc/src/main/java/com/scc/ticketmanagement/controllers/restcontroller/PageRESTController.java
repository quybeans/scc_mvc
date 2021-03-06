package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by QuyBean on 11/2/2016.
 */

@RestController
public class PageRESTController {

    @Autowired
    UserService userService;

    @Autowired
    PageRepository pageRepository;

    @RequestMapping("page/allpage")
    public List<PageEntity> getALlPage(HttpServletRequest request)
    {
       int userid =  getUseridBySession(request);

        if (userid!=0)
        {
            List<PageEntity> pages = pageRepository.getAllPageByBrandId(userService.getUserByID(userid).getBrandid());
            return pages;
        }
        return null;
    }

    public int getUseridBySession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            if (session.getAttribute("username") != null) {
                int userid = userService.getUserByUsername((String) session.getAttribute("username")).getUserid();
                return userid;
            }
        }
        return 0;
    }
}
