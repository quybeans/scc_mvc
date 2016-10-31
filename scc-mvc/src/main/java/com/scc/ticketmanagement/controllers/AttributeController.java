package com.scc.ticketmanagement.controllers;

import com.scc.ticketmanagement.Entities.AttributeEntity;
import com.scc.ticketmanagement.Entities.CommentattributeEntity;
import com.scc.ticketmanagement.Entities.UserEntity;
import com.scc.ticketmanagement.exentities.ExAttribute;
import com.scc.ticketmanagement.repositories.AttributeRepository;
import com.scc.ticketmanagement.repositories.CommentattributeRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10/30/2016.
 */
@RestController
public class AttributeController {
    @Autowired
    CommentattributeRepository commentattributeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @RequestMapping("/getallbrandtags")
    public List<ExAttribute> getallbrandtags(HttpServletRequest request,
                                                 @RequestParam("commentid") String commentid){
        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("username");
        UserEntity user = userRepository.findUserByUsername(loginUser);

        List<CommentattributeEntity> listCmtAtt=commentattributeRepository.getAttributebyCommentID(commentid);
        List<AttributeEntity> listAtt = attributeRepository.getAttributeByBrandID(user.getBrandid());
        List<ExAttribute> listExAtt = new ArrayList<ExAttribute>();
        for (AttributeEntity att: listAtt) {
            ExAttribute exAtt = new ExAttribute();
            exAtt.setId(att.getId());
            exAtt.setBrandid(att.getBrandid());
            exAtt.setName(att.getName());
            for (CommentattributeEntity ca: listCmtAtt) {
                if(ca.getAttributeid()==att.getId()){
                    exAtt.setCommentatt(true);
                }
            }
            listExAtt.add(exAtt);
        }
        return listExAtt;
    }

    @RequestMapping("/tagcomment")
    public CommentattributeEntity tagcomment(@RequestParam("commentid") String commentid,
                                             @RequestParam("tagid") Integer tag){
        CommentattributeEntity commentattributeEntity = new CommentattributeEntity();
        commentattributeEntity.setAttributeid(tag);
        commentattributeEntity.setCommentid(commentid);
        return commentattributeRepository.save(commentattributeEntity);
    }
}
