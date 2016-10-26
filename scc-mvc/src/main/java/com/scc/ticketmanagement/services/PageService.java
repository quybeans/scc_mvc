package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.PageEntity;

import java.util.List;

/**
 * Created by Thien on 10/11/2016.
 */
public interface PageService {
    void createPage(String pageName, String pageId, String pageAccessToken);
    PageEntity getPageById(String uid);
    List<PageEntity> getAllPages();
    void deactivatePage(String pageUid);
    void activatePage(String pageUid);
}
