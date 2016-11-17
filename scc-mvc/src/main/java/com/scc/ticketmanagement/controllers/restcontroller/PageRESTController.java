package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.repositories.CommentRepository;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.repositories.PostRepository;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @RequestMapping("page/allpage")
    public List<PageEntity> getALlPage(HttpServletRequest request)
    {
       int userid =  getUseridBySession(request);

        if (userid!=0)
        {
            List<PageEntity> pages = pageRepository.getAllShowPostPageByBrandId(userService.getUserByID(userid).getBrandid());
            List<PageEntity> rs = new ArrayList<>();
            for (PageEntity page : pages)
            {
                if (page.isCrawler())
                rs.add(page);
            }
            return rs;
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

    @RequestMapping("page/countAllPost")
    public long countAllPostByPage(String pageId)
    {
       return postRepository.countPostByPageId(pageId);
    }

    @RequestMapping("page/countAllComment")
    public long countAllCommentByPage(String pageId)
    {
        return commentRepository.countAllCommentFromPage(pageId);
    }

    @RequestMapping("page/overview")
    public List<Long> pageOverview (String pageId)
    {
        long postCount = postRepository.countPostByPageId(pageId);
        long cmtCount = commentRepository.countAllCommentFromPage(pageId);
        List<Long> l = new ArrayList<>();
        l.add(postCount);
        l.add(cmtCount);

        return l;
    }

}
