package com.scc.ticketmanagement.services;

/**
 * Created by Thien on 10/24/2016.
 */
public interface BrandPageService {
    public void addBrandPageToManage(int brandId, String pageId);
    public void addBrandPageToCrawl(int brandId, String pageId);
    public void removeBrandPageToManage(int brandId, String pageId);
    public void removeBrandPageToCrawl(int brandId, String pageId);
    public int deleteBrandPageByBrandIdAndPageId(int brandId, String pageId);
}
