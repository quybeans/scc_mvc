package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.Entities.Page;
import com.scc.ticketmanagement.exentities.ExComment;
import com.scc.ticketmanagement.exentities.ExPost;
import com.scc.ticketmanagement.exentities.ExtendComments;
import com.scc.ticketmanagement.exentities.FaceBookPage;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.services.CommentService;
import com.scc.ticketmanagement.services.UserService;
import com.scc.ticketmanagement.utilities.Constant;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by QuyBeans on 05-Oct-16.
 */

@RestController
public class WebServiceController {


    private static final String ACCESS_TOKEN_KITTY = "EAACEdEose0cBABbQIYIAjEagu1dsZAlAGzzx8xCasf6EH1TrlNuhfXMGRDn5I8lZAU2ReDRc2udIpn5RpZCnlrlxrKjnNOrngPOFjhG7XhwaozLnimI5PxjCzkrLkRzii9vbCshoyrvsA9ipxIKYeWjZAqi0rr98TZBEZCMk4ASgZDZD";
    public static FaceBookPage page = new FaceBookPage("177872845972148","Kitty Bang Bang",ACCESS_TOKEN_KITTY);


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FacebookaccountRepository facebookaccountRepository;

    @Autowired
    private PageRepository pageRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private UserCommentRepository userCommentRepository;

    @RequestMapping("commentbypost")
    public List<CommentEntity> commentsByPost(@RequestParam("postId") String postId){
        return commentRepository.findCommentByPostId(postId);
    }

    @RequestMapping("commentbypostWithSen")
    public List<ExtendComments> commentbypostWithSen(@RequestParam("postId") String postId,HttpServletRequest request){
        List<ExtendComments> showcomments = new ArrayList<ExtendComments>();
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);
        List<CommentEntity> commentlist =commentRepository.findCommentByPostIdSen(postId);


            for (CommentEntity c: commentlist) {
                ExtendComments cmt = new ExtendComments();
                cmt.setContent(c.getContent());
                cmt.setCreatedAt(c.getCreatedAt());
                cmt.setCreatedBy(c.getCreatedBy());
                cmt.setCreatedByName(c.getCreatedByName());
                cmt.setId(c.getId());
                cmt.setPostId(c.getPostId());
                cmt.setSentimentScore(c.getSentimentScore());
                if(user.getRoleid()== Constant.ROLE_STAFF){
                    cmt.setStaff(true);
                }
                showcomments.add(cmt);
            }

        return showcomments;
    }


    @RequestMapping("postById")
    public ExPost postsById(@RequestParam("postId") String postId){
        ExPost newP = new ExPost();
        PostEntity post= postRepository.findOne(postId);
        PageEntity page = pageRepository.findOne(post.getCreatedBy());

        newP.setCreatedBy(post.getCreatedBy());
        newP.setContent(post.getContent());
        newP.setId(post.getId());
        newP.setLikesCount(post.getLikesCount());
        newP.setCommentsCount(post.getCommentsCount());
        newP.setSharesCount(post.getSharesCount());
        newP.setCreatedAt(post.getCreatedAt());
        newP.setCreatedByName(page.getName());

        return newP;
    }

    @RequestMapping("getPageNameById")
    public String pageNameById(@RequestParam("pageId") String pageId)
    {
        PageEntity page = pageRepository.findOne(pageId);
        return page.getName();
    }
    //Post a comment
    @RequestMapping("commentOnObj")
    public String postsById(HttpServletRequest request,
                             @RequestParam("objId") String objId,
                             @RequestParam("message") String message,
                             @RequestParam("token") String token){

        HttpSession session = request.getSession(false);
        if (session!=null) {
            String username = (String) session.getAttribute("username");
            UserEntity user = userService.getUserByUsername(username);
            UserCommentEntity userCommentEntity = new UserCommentEntity();
            CommentEntity commentEntity = new CommentEntity();


            String commentid = FacebookUtility.commentOnObj(objId,message,token);
            if(commentid!=null) {
                userCommentEntity.setUserid(user.getUserid());
                userCommentEntity.setCommentid(FacebookUtility.getShortObjectId(commentid));
                userCommentEntity.setPostid(FacebookUtility.getShortObjectId(objId));

                commentEntity.setId(FacebookUtility.getShortObjectId(commentid));
                commentEntity.setPostId(FacebookUtility.getShortObjectId(objId));
                commentEntity.setCreatedByName("Pending from crawler");
                commentEntity.setContent(message);
                commentEntity.setCreatedBy("0");
                commentEntity.setCreatedAt( new Timestamp(System.currentTimeMillis()));

                commentRepository.save(commentEntity);
                userCommentRepository.save(userCommentEntity);
                return objId;
            }
        }
        return null;
    }

    @RequestMapping("allFbAccount")
    public List<FacebookaccountEntity> allFbAccount(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session!=null) {
            String username = (String) session.getAttribute("username");
            int userid = userRepository.findUseridByUser(username);
            List<FacebookaccountEntity> accounts = facebookaccountRepository.getFacebookAccountsByUserId(userid);
            return accounts;

        }
        return null;
    }

    @RequestMapping("allPageAccount")
    public List<PageEntity> allPageAccount(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session!=null) {
            String username = (String) session.getAttribute("username");
            int userid = userRepository.findUseridByUser(username);
            UserEntity user = userRepository.findOne(userid);
            List<PageEntity> accounts = pageRepository.getAllPageByBrandId(user.getBrandid());
            return accounts;

        }
        return null;
    }

    @RequestMapping("sendMessage")
    public String sendMessage(String content, String id, String token)
    {
        try {
            return FacebookUtility.sendMessage(content, id, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "nothing";
    }




}
