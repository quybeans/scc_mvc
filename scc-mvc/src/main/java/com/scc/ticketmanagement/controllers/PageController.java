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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @RequestMapping("/page/addPageToCrawl")
    public String getPageAccessToken() {

        return "page/addPageToCrawl";
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

        List<PageEntity> crawlerPages = pageService.getCrawlerPagesByBrandId(brandId);
        model.addAttribute("crawlerPages", crawlerPages);
        return "page/index";
    }

    @RequestMapping("/crawl-page/index")
    public String crawlIndex(HttpServletRequest request, Model model) {

        if (!this.isCurrentUserAuthorized(request)) {
            return "page/403";
        }
        HttpSession session = request.getSession(false);
        int brandId = this.getCurrentUserBrandId(session);

        List<PageEntity> crawlerPages = pageService.getCrawlerPagesByBrandId(brandId);
        model.addAttribute("crawlerPages", crawlerPages);
        return "crawl-page/index";
    }

    @RequestMapping(value = "page/processPageAccessToken")
    public String processPageAccessToken(HttpServletRequest request,
                                         @RequestParam("pageAccessToken") String pageToken,
                                         @RequestParam("pageName") String pageName,
                                         @RequestParam("pageId") String pageId,
                                         @RequestParam("pageCategory") String pageCategory) {

        System.out.println(pageToken);
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

                String longLivedToken = AccessTokenUtility.getMessengerExtendedAccessToken(pageToken);
                System.out.println(longLivedToken);
                pageService.createPage(pageName, pageId, longLivedToken, pageCategory);


                brandPageService.addBrandPageToManage(brandId, pageId);
            } catch (AuthenticationException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Cant get brandId for username: ");
                return "redirect:/page/error404";
            }

        }
        return "redirect:/page/index";
    }

    @RequestMapping(value = "page/addPageToCrawl", method = RequestMethod.POST)
    @ResponseBody
    public String getPage(HttpServletRequest request,
                          @RequestParam("pageId") String pageId,
                          @RequestParam("pageName") String pageName,
                          @RequestParam("pageCategory") String pageCategory) {

        if (!this.isCurrentUserAuthorized(request)) {
            return "Unauthorized";
        }

        HttpSession session = request.getSession(false);
        int brandId = this.getCurrentUserBrandId(session);

        System.out.println(pageId);
        System.out.println(pageName);
        System.out.println(pageCategory);

        pageService.createPage(pageName, pageId, "", pageCategory);
        brandPageService.addBrandPageToCrawl(brandId, pageId);
        return "Add page to crawl successfully";
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
            brandPageService.removeBrandPageToManage(brandId, pageId);
        } else if (button.equals("Activate")) {
            pageService.activatePage(pageId);
            brandPageService.addBrandPageToManage(brandId, pageId);
        }else if (button.equals("Delete")) {
            brandPageService.deleteBrandPageByBrandIdAndPageId(brandId, pageId);
        }
        return "redirect:/page/index";
    }

    @RequestMapping(value = "/crawl-page/deactivateCrawlerPage", method = RequestMethod.POST)
    public String deactivateCrawlerPage(HttpServletRequest request,
                                        @RequestParam("pageId") String pageId,
                                        @RequestParam("btnAction") String button) {

        if (!this.isCurrentUserAuthorized(request)) {
            return "page/403";
        }
        HttpSession session = request.getSession(false);
        int brandId = this.getCurrentUserBrandId(session);


        if (button.equals("Deactivate")) {
            pageService.deactivateCrawlerPage(pageId);
            brandPageService.removeBrandPageToCrawl(brandId, pageId);
        } else if (button.equals("Activate")) {
            pageService.activateCrawlerPage(pageId);
            brandPageService.addBrandPageToCrawl(brandId, pageId);
        }else if (button.equals("Delete")) {
            brandPageService.deleteBrandPageByBrandIdAndPageId(brandId, pageId);
        }
        return "redirect:/crawl-page/index";
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
