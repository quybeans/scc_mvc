package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.Entities.PostEntity;
import com.scc.ticketmanagement.exentities.ExPost;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by QuyBean on 11/4/2016.
 */

@RestController
public class PostRESTController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PageRepository pageRepository;

    @RequestMapping("post/sentimentcount")
    public int[] sentimentcount(String postid) {
        int[] rs = new int[]{0, 0};
        rs[0] = postRepository.findPosCountByPostId(postid);
        rs[1] = postRepository.findNegCountByPostId(postid);
        return rs;
    }

    @RequestMapping("post/findbycontent")
    public List<ExPost> findPostByContent(String content, String pagelist) {
        if (!pagelist.equals("")) {
            List<String> listPage = Arrays.asList(pagelist.split(","));

            if (listPage.size() > 0) {
                List<PostEntity> list = postRepository.findByCreatedByInAndContentContaining(listPage, content);
                List<PageEntity> pageEntities = pageRepository.findByPageidIn(listPage);

                List<ExPost> rs = new ArrayList<>();

                for (PostEntity post : list) {
                    ExPost newP = new ExPost();
                    newP.setCreatedBy(post.getCreatedBy());
                    newP.setContent(post.getContent());
                    newP.setId(post.getId());
                    newP.setLikesCount(post.getLikesCount());
                    newP.setCommentsCount(post.getCommentsCount());
                    newP.setSharesCount(post.getSharesCount());
                    newP.setCreatedAt(post.getCreatedAt());
                    newP.setNegCount(postRepository.findNegCountByPostId(post.getId()));
                    newP.setPosCount(postRepository.findPosCountByPostId(post.getId()));

                    for (PageEntity page : pageEntities) {
                        if (post.getCreatedBy().equals(page.getPageid()) ) {
                            newP.setCreatedByName(page.getName());
                        }
                    }
                    rs.add(newP);
                }

                return rs;
            }
        }
        return null;
    }

    @RequestMapping("post/findbycontent/test")
    public List<PostEntity> findPostByContents(List<String> pageid, String content) {
        return postRepository.findByCreatedByInAndContentContaining(pageid, content);
    }
}
