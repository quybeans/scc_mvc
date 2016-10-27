package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thien on 10/11/2016.
 */
@Service
public class PageImp implements PageService {
    @Autowired
    private PageRepository pageRepository;


    @Override
    public void createPage(String pageName, String pageId, String pageAccessToken, String category) {
        PageEntity page = null;
        try {
            page = this.getPageById(pageId);
            if (page == null) {
                page = new PageEntity();
                page.setPageid(pageId);
                page.setName(pageName);
                page.setAccesstoken(pageAccessToken);
                page.setCategory(category);
                page.setType("Facebook");
                page.setActive(true);
            } else {
                page.setAccesstoken(pageAccessToken);
                page.setActive(true);
            }
            pageRepository.save(page);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e){

        }


    }

    @Override
    public PageEntity getPageById(String pageId) {
        return pageRepository.getPageById(pageId);
    }

    @Override
    public List<PageEntity> getAllPages() {
        return pageRepository.findAll();
    }

    @Override
    public void deactivatePage(String pageUid) {
        PageEntity page = pageRepository.findOne(pageUid);
        page.setActive(false);
        pageRepository.save(page);
    }

    @Override
    public void activatePage(String pageUid) {
        PageEntity page = pageRepository.findOne(pageUid);
        page.setActive(true);
        pageRepository.save(page);
    }
}
