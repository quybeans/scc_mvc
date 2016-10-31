package com.scc.ticketmanagement.entity;

/**
 * Created by QuyBeans on 06-Oct-16.
 */
public class FaceBookPage {
    private String pageId;
    private String pageName;
    private String pageToken;


    public FaceBookPage(String pageId, String pageName, String pageToken) {
        this.pageId = pageId;
        this.pageName = pageName;
        this.pageToken = pageToken;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }
}
