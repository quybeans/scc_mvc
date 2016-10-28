package com.scc.ticketmanagement.controllers;


import com.scc.ticketmanagement.Entities.*;
import com.scc.ticketmanagement.exentities.ExComment;
import com.scc.ticketmanagement.exentities.ExPost;
import com.scc.ticketmanagement.exentities.ExtendComments;
import com.scc.ticketmanagement.exentities.FaceBookPage;
import com.scc.ticketmanagement.repositories.*;
import com.scc.ticketmanagement.utilities.FacebookUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    private BrandPageRepository brandPageRepository;

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @RequestMapping("allPostsByBrand")
    public List<ExPost> postsByByBrand(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session!=null){
            String username = (String)session.getAttribute("username");
            // List<PostEntity> listP =  postRepository.findByCreatedBy(pageid);
            int brandid = userRepository.getBrandIdByUsername(username);
            List<String> listPage =  brandPageRepository.getAllPagesByBrandid(brandid);
            List<ExPost> rs = new ArrayList<>();
            for(String pageid : listPage)
            {
                PageEntity page = pageRepository.findOne(pageid);
                List<PostEntity> list =  postRepository.findByCreatedBy(pageid);
                for (PostEntity post : list)
                {
                    ExPost newP = new ExPost();
                    newP.setCreatedBy(post.getCreatedBy());
                    newP.setContent(post.getContent());
                    newP.setId(post.getId());
                    newP.setLikesCount(post.getLikesCount());
                    newP.setCommentsCount(post.getCommentsCount());
                    newP.setSharesCount(post.getSharesCount());
                    newP.setCreatedAt(post.getCreatedAt());
                    newP.setCreatedByName(page.getName());

                    rs.add(newP);
                }

            }
            Collections.reverse(rs);
            return rs;
        }
        return null;
    }

    @RequestMapping("posts")
    public List<PostEntity> posts(){

        List<PostEntity> listP =  postRepository.findAll();
        Collections.reverse(listP);
        return listP;
    }

    @RequestMapping("commentbypost")
    public List<CommentEntity> commentsByPost(@RequestParam("postId") String postId){
        return commentRepository.findCommentByPostId(postId);
    }
    @RequestMapping("commentbypostWithSen")
    public List<ExtendComments> commentbypostWithSen(@RequestParam("postId") String postId){
        List<ExtendComments> showcomments = new ArrayList<ExtendComments>();
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
            if (ticketRepository.findBycommentid(cmt.getId())!=null){
                cmt.setIsticket(true);
            }

            showcomments.add(cmt);
        }
        return showcomments;
    }
    //This one include createdByName, NO LONGER USE
    @RequestMapping("newcommentbypost")
    public List<ExComment> newcommentsByPost(@RequestParam("postId") String postId){

        List<CommentEntity> list =commentRepository.findCommentByPostId(postId);
        List<ExComment> exlist = new ArrayList<>();
        for ( CommentEntity old : list)
        {
            ExComment newComment = new ExComment();

            newComment.setId(old.getId());

            newComment.setPostId(old.getPostId());
            newComment.setContent(old.getContent());

            newComment.setCreatedAt(old.getCreatedAt());
            newComment.setCreatedBy(old.getCreatedBy());
            //Created by Name add here
            newComment.setCreatedByName(FacebookUtility.getFBName(old.getCreatedBy(),page.getPageToken()));

            exlist.add(newComment);
        }

        return exlist;
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
    public boolean postsById(@RequestParam("objId") String objId,
                             @RequestParam("message") String message,
                             @RequestParam("token") String token){
        return FacebookUtility.commentOnObj(objId,message,token);
    }

    @RequestMapping("allFbAccount")
    public List<FacebookaccountEntity> allFbAccount(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        if (session!=null) {
            String username = (String) session.getAttribute("username");
            int userid = userRepository.findUseridByUser(username);
            List<FacebookaccountEntity> accounts = facebookaccountRepository.getFacebookaccountByUserId(userid);
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

}
