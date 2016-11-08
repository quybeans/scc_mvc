package com.scc.ticketmanagement.controllers.restcontroller;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.Entities.PostEntity;
import com.scc.ticketmanagement.exentities.ExPost;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.repositories.PostRepository;
import com.scc.ticketmanagement.repositories.UserRepository;
import com.scc.ticketmanagement.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by QuyBean on 11/4/2016.
 */

@RestController
public class PostRESTController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PostService postService;

    @RequestMapping("post/sentimentcount")
    public int[] sentimentcount(String postid) {
        int[] rs = new int[]{0, 0, 0};
        rs[0] = postRepository.findPosCountByPostId(postid);
        rs[1] = postRepository.findNegCountByPostId(postid);
        rs[2] = postRepository.findQuestCountByPostId(postid);
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
//                    newP.setNegCount(postRepository.findNegCountByPostId(post.getId()));
//                    newP.setPosCount(postRepository.findPosCountByPostId(post.getId()));
                        newP.setNegCount(1);
                    newP.setPosCount(2);
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
    @RequestMapping(value = "allPostsByBrand", method = RequestMethod.POST)
    public List<ExPost> postsByByBrand(HttpServletRequest request, String pagelist)
    {
        if (!pagelist.equals("")){
            HttpSession session = request.getSession(false);
            if (session!=null){
                String username = (String)session.getAttribute("username");
                // List<PostEntity> listP =  postRepository.dByCreatedBy(pageid);
                int brandid = userRepository.getBrandIdByUsername(username);
//            List<String> listPage =  brandPageRepository.getAllPagesByBrandid(brandid);
                List<String> listPage = Arrays.asList(pagelist.split(","));
                if (listPage.size()>0){
                    List<ExPost> rs = new ArrayList<>();
                    for(String pageid : listPage)
                    {
                        PageEntity page = pageRepository.findOne(pageid);
                        if(page.isCrawler()) {
                            List<PostEntity> list = postRepository.findByCreatedBy(pageid);
                            for (PostEntity post : list) {
                                ExPost newP = new ExPost();
                                newP.setCreatedBy(post.getCreatedBy());
                                newP.setContent(post.getContent());
                                newP.setId(post.getId());
                                newP.setLikesCount(post.getLikesCount());
                                newP.setCommentsCount(post.getCommentsCount());
                                newP.setSharesCount(post.getSharesCount());
                                newP.setCreatedAt(post.getCreatedAt());
                                newP.setCreatedByName(page.getName());
                                newP.setNegCount(postRepository.findNegCountByPostId(post.getId()));
                                newP.setPosCount(postRepository.findQuestCountByPostId(post.getId()));
                                newP.setNegCount(1);
                                newP.setPosCount(2);
                                rs.add(newP);
                            }
                        }
                    }
                    Collections.sort(rs, new Comparator<ExPost>() {
                        @Override
                        public int compare(ExPost o1, ExPost o2) {
                            return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                        }
                    });

                    return rs;
                }}}
        return null;
    }

    @RequestMapping("post/allpostbypage")
    public List<ExPost> findPostByPageswPage(String pagelist, int pageno) {
        List<String> listPage = Arrays.asList(pagelist.split(","));
        List<PostEntity> postList = postService.getPostByPageIdwPage(listPage,pageno).getContent();

        return transformPostToExPost(postList,listPage);
    }

    //TESTING MAPPING
    @RequestMapping("post/findbycontent/test")
    public Page<PostEntity> findPostByContents(String pages, int pageno) {
        List<String> listPage = Arrays.asList(pages.split(","));
       return postService.getPostByPageIdwPage(listPage,1);
    }

    private List<ExPost>transformPostToExPost(List<PostEntity> postlist, List<String> listPage)
    {
        List<ExPost> rs = new ArrayList<>();
        List<PageEntity> pageEntities = pageRepository.findByPageidIn(listPage);
        for (PostEntity post : postlist) {
            ExPost newP = new ExPost();
            newP.setCreatedBy(post.getCreatedBy());
            newP.setContent(post.getContent());
            newP.setId(post.getId());
            newP.setLikesCount(post.getLikesCount());
            newP.setCommentsCount(post.getCommentsCount());
            newP.setSharesCount(post.getSharesCount());
            newP.setCreatedAt(post.getCreatedAt());
            newP.setNegCount(postRepository.findNegCountByPostId(post.getId()));
            newP.setPosCount(postRepository.findQuestCountByPostId(post.getId()));

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
