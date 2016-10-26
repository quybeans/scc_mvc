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
    public void createPage(String pageName, String pageId, String pageAccessToken) {
        PageEntity entity = null;
        try {
            entity = this.getPageById(pageId);
            if (entity == null) {
                entity = new PageEntity();
                entity.setPageid(pageId);
                entity.setName(pageName);
                entity.setAccesstoken(pageAccessToken);
                entity.setActive(true);
            } else {
                entity.setAccesstoken(pageAccessToken);
                entity.setActive(true);
            }
            pageRepository.save(entity);
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
