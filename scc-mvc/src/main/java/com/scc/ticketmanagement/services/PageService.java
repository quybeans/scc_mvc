package com.scc.ticketmanagement.services;

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
    void deactivatePage(String pageUid);
    void activatePage(String pageUid);
}
