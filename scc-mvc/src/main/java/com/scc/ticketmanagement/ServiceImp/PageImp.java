package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.PageEntity;
import com.scc.ticketmanagement.repositories.PageRepository;
import com.scc.ticketmanagement.services.PageService;
import com.scc.ticketmanagement.utilities.CommonUtility;
import com.scc.ticketmanagement.utilities.Constant;
import com.scc.ticketmanagement.utilities.FacebookUtility;
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
                page.setCrawler(false);
                page.setType("Facebook");
                page.setActive(FacebookUtility.subscribePageToWebhook(pageId,pageAccessToken));
            } else {
                page.setAccesstoken(pageAccessToken);
                page.setCategory(category);
                page.setType("Facebook");
                page.setActive(FacebookUtility.subscribePageToWebhook(pageId,pageAccessToken));

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
    public List<PageEntity> getPagesByBrandId(int brandId) {
        return pageRepository.getAllPageByBrandId(brandId);
    }

    @Override
    public List<PageEntity> getAllActivePageByBrandId(int brandId) {
        return this.pageRepository.getAllActivePageByBrandId(brandId);
    }

    @Override
    public List<PageEntity> getCrawlerPagesByBrandId(int brandId) {
        return pageRepository.getAllCrawlerPageByBrandId(brandId);
    }

    @Override
    public void deactivatePage(String pageUid) {
        PageEntity page = pageRepository.findOne(pageUid);
        page.setActive(false);
        pageRepository.save(page);
    }

    @Override
    public void deactivateCrawlerPage(String pageUid) {
        PageEntity page = pageRepository.findOne(pageUid);
        page.setCrawler(false);
        pageRepository.save(page);
    }

    @Override
    public void activatePage(String pageUid) {
        PageEntity page = pageRepository.findOne(pageUid);
        page.setActive(true);
        pageRepository.save(page);
    }

    @Override
    public void activateCrawlerPage(String pageUid) {
        PageEntity page = pageRepository.findOne(pageUid);
        page.setCrawler(true);
        pageRepository.save(page);
    }

    @Override
    public String getPageAccessTokenByPageId(String pageId) {
        return pageRepository.getPageById(pageId).getAccesstoken();
    }



}
