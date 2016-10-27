package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.services.BrandPageService;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.AccessTokenUtility;
import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Thien on 10/12/2016.
 */
@Controller
public class PageController {

    @Autowired
    PageService pageService;
    @Autowired
    UserService userService;
    @Autowired
    BrandPageService brandPageService;

    @RequestMapping("/page/getPageAccessToken")
    public String getPageAccessToken() {

        return "page/getPageAccessToken";
    }

    @RequestMapping("/page/index")
    public String index(HttpServletRequest request, Model model) {

        if (!this.isCurrentUserAuthorized(request)) {
            return "page/403";
        }
        HttpSession session = request.getSession(false);
        int brandId = this.getCurrentUserBrandId(session);

        List<PageEntity> pages = pageService.getPagesByBrandId(brandId);
        model.addAttribute("pages", pages);
        return "page/index";
    }

    @RequestMapping(value = "page/processPageAccessToken")
    public String processPageAccessToken(HttpServletRequest request,
                                         @RequestParam("pageAccessToken") String pageToken,
                                         @RequestParam("pageName") String pageName,
                                         @RequestParam("pageId") String pageId,
                                         @RequestParam("pageCategory") String pageCategory) {

        if (!this.isCurrentUserAuthorized(request)) {
            return "page/403";
        }

        //get Brand Id here
        HttpSession session = request.getSession(false);
        int brandId = this.getCurrentUserBrandId(session);

        System.out.println(pageCategory);


        if (!pageToken.equals("") && !pageName.equals("") && !pageId.equals("")) {
            try {
                System.out.println("brand id: " + brandId);

                String longLivedToken = AccessTokenUtility.getExtendedAccessToken(pageToken);
                System.out.println(longLivedToken);
                pageService.createPage(pageName, pageId, longLivedToken, pageCategory);
                brandPageService.addBrandPage(brandId, pageId);
            } catch (AuthenticationException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Cant get brandId for username: ");
                return "redirect:/page/error404";
            }

        }
        return "redirect:/page/index";
    }

    @RequestMapping(value = "/page/deactivatePage", method = RequestMethod.POST)
    public String index(HttpServletRequest request,
                        @RequestParam("pageId") String pageId,
                        @RequestParam("btnAction") String button) {

        if (!this.isCurrentUserAuthorized(request)) {
            return "page/403";
        }

        HttpSession session = request.getSession(false);
        int brandId = this.getCurrentUserBrandId(session);


        if (button.equals("Deactivate")) {
            pageService.deactivatePage(pageId);
            brandPageService.removeBrandPage(brandId, pageId);
        } else if (button.equals("Activate")) {
            pageService.activatePage(pageId);
            brandPageService.addBrandPage(brandId, pageId);
        }
        return "redirect:/page/index";
    }

    @RequestMapping("/page/create")
    public String create(HttpServletRequest request) {
        if (!this.isCurrentUserAuthorized(request)) {
            return "page/403";
        }
        return "page/create";
    }

    @RequestMapping("/page/403")
    public String error404() {

        return "page/403";
    }

    private int getCurrentUserBrandId(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return 0;
        }
        return userService.getBrandIdByUsername(username);
    }

    private boolean isCurrentUserAuthorized(HttpServletRequest request) {
        boolean isAuthorized = false;
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                UserEntity user = userService.getUserByUsername(username);
                if (user.getRoleid() == Constant.ROLE_SUPERVISOR || user.getRoleid() == Constant.ROLE_BRAND) {
                    isAuthorized = true;
                }
            }//end if username != null
        }//end if session != null

        return isAuthorized;
    }
}
