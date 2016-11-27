package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.UserCommentEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.repositories.UserCommentRepository;
import com.scc.ticketmanagement.services.CommentService;
import com.scc.ticketmanagement.services.UserCommentService;
import com.scc.ticketmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by QuyBean on 11/4/2016.
 */

@RestController
public class CommentRESTController {

    @Autowired
    CommentService commentService;

    @Autowired
    private UserCommentRepository userCommentRepository;

    @Autowired
    private UserService userService;

    @RequestMapping("comment/bypostid")
    public List<CommentEntity> getAllComment(int page, String postid) {
        Page<CommentEntity> result = commentService.getCommentByPostId(page, postid);
        return result.getContent();
    }

    @RequestMapping("comment/bypostid/count")
    public long countComment(String postid) {
        Page<CommentEntity> result = commentService.getCommentByPostId(1, postid);
        return result.getTotalPages();
    }

    @RequestMapping("comment/bypostid/negSort")
    public List<CommentEntity> commentNegSort(String postid, int page) {
        Page<CommentEntity> result = commentService.getCommentByPostIdwSort(page, 1, postid);
        return result.getContent();
    }

    @RequestMapping("comment/bypostid/timeSort")
    public List<CommentEntity> commentTimeSort(String postid, int page) {
        Page<CommentEntity> result = commentService.getCommentByPostIdwTimeSort(page, postid);
        return result.getContent();
    }

    @RequestMapping("comment/bypostid/search")
    public List<CommentEntity> searchcommentByContent(String postid, String content, int page) {
        Page<CommentEntity> result = commentService.searchCommentByPostIdandContent(page, postid, content);
        return result.getContent();
    }

    @RequestMapping("comment/reply/count")
    public int searchcommentByContent(String commentId) {
        int result = commentService.countReply(commentId);
        return result;
    }

    @RequestMapping("comment/checkUserReply")
    public String checkReply(HttpServletRequest request, String commentId) {
        try {
            HttpSession session = request.getSession(false);
            String username = (String)session.getAttribute("username");
            int currentBrandid = userService.getUserByUsername(username).getBrandid();

            int userid = userCommentRepository.findUserIdByComment(commentId);
            UserEntity userEntity = userService.getUserByID(userid);
            if (userEntity!=null && userEntity.getBrandid()==currentBrandid)
            {
                return userEntity.getUsername();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
