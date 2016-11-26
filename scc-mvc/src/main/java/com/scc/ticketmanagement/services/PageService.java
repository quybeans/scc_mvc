package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.BrandpageEntity;
import com.scc.ticketmanagement.Entities.PageEntity;

import java.util.List;

/**
 * Created by Thien on 10/11/2016.
 */
public interface PageService {
    void createPage(String pageName, String pageId, String pageAccessToken, String category);
    PageEntity getPageById(String uid);
    List<PageEntity> getAllPages();
    List<PageEntity> getPagesByBrandId(int brandId);
    List<PageEntity> getAllActivePageByBrandId(int brandId);
    List<PageEntity> getCrawlerPagesByBrandId(int brandId);

    void deactivatePage(String pageUid);
    void deactivateCrawlerPage(String pageUid);
    void activatePage(String pageUid);
    void activateCrawlerPage(String pageUid);
    String getPageAccessTokenByPageId(String pageId);

    BrandpageEntity getBrandPageByBrandIdAndPageId(int brandId, String pageId);
}
