package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.BrandEntity;

import com.scc.ticketmanagement.Entities.ProfileEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.services.ProfileService;
import com.scc.ticketmanagement.services.UserService;

import com.scc.ticketmanagement.utilities.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by QuyBean on 10/31/2016.
 */

@RestController
public class BrandRESTController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private BrandPageRepository brandPageRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

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

    @RequestMapping("brand/getBrandManager")
    public ProfileEntity getBrandManagerInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {

            String username = (String) session.getAttribute("username");
            if (username != null) {
                UserEntity brandManager = userService.findUserByBrandIdAndRole(userService.getBrandIdByUsername(username), Constant.ROLE_BRAND);
                brandManager.setPassword("");
                ProfileEntity profileEntity = profileService.getProfileByID(brandManager.getUserid());
                return profileEntity;
            }
        }
        return null;
    }

    @RequestMapping("brand/isBrandManager")
    public boolean isBrandManager(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                UserEntity user = userService.getUserByUsername(username);
                if (user.getRoleid() == Constant.ROLE_BRAND) return true;
                else return false;
            }
        }
        return false;
    }

    @RequestMapping("/brand/bigOverview")
    public List<Long> overView(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                int brandid = userService.getBrandIdByUsername(username);
                long pageCount = brandPageRepository.countPageByBrandId(brandid);
                long messagecount = 1;
                long userCount = userRepository.countUserByBrandID(brandid);
                long ticketCount = ticketRepository.coutnTicketByBrandId(brandid);
                long commentCount = commentRepository.countCommentByBrandId(brandid);
                long postCount = postRepository.countPostByBrandId(brandid);
                //1: page, 2: mess, 3:user, 4: ticket
                List<Long> rs = new ArrayList<>();
                rs.add(pageCount);
                rs.add(messagecount);
                rs.add(userCount);
                rs.add(ticketCount);
                rs.add(commentCount);
                rs.add(postCount);

                return rs;
            }
        }
        return null;
    }

    @RequestMapping("/brand/commentstatic")
    public List<Long> commentStatic(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                int brandid = userService.getBrandIdByUsername(username);
                long negCount = commentRepository.countCommentByBrandIdwSen(brandid,2);
                long posCount = commentRepository.countCommentByBrandIdwSen(brandid,Constant.SENTIMENT_POSITIVE);
                long quesCoount = commentRepository.countCommentByBrandIdwSen(brandid,Constant.SENTIMENT_QUESTION);

                //1: posCount, 2: negCount, 3:quesCoount
                List<Long> rs = new ArrayList<>();
                rs.add(posCount);
                rs.add(negCount);
                rs.add(quesCoount);

                return rs;
            }
        }
        return null;
    }


}

