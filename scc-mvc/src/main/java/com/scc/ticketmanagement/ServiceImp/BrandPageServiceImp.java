package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.BrandpageEntity;
import com.scc.ticketmanagement.repositories.BrandPageRepository;
import com.scc.ticketmanagement.services.BrandPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thien on 10/24/2016.
 */
@Service
public class BrandPageServiceImp implements BrandPageService {

    @Autowired
    BrandPageRepository brandPageRepository;

    @Override
    public void addBrandPageToManage(int brandId, String pageId) {
        BrandpageEntity brandpage = null;
        try {
            brandpage = brandPageRepository.getBrandPageByBrandIdAndPageId(brandId, pageId);
            if (brandpage == null) {
                brandpage = new BrandpageEntity();
                brandpage.setBrandid(brandId);
                brandpage.setPageid(pageId);
            }

            brandpage.setCrawl(true);
            brandpage.setManage(true);
            brandPageRepository.save(brandpage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeBrandPageToManage(int brandId, String pageId) {
        BrandpageEntity brandpage = brandPageRepository.getBrandPageByBrandIdAndPageId(brandId, pageId);
        if (brandpage != null){
            brandpage.setManage(false);
            brandpage.setCrawl(false);
            brandPageRepository.save(brandpage);
        }

    }

    @Override
    public void removeBrandPageToCrawl(int brandId, String pageId) {
        BrandpageEntity brandpage = brandPageRepository.getBrandPageByBrandIdAndPageId(brandId, pageId);
        if (brandpage != null){
            brandpage.setCrawl(false);
            brandPageRepository.save(brandpage);
        }
    }

    @Override
    public int deleteBrandPageByBrandIdAndPageId(int brandId, String pageId) {
        return brandPageRepository.deleteBrandPageByPageIdAndBrandId(brandId, pageId);
    }

    @Override
    public void addBrandPageToCrawl(int brandId, String pageId) {
        BrandpageEntity brandpage = null;
        try {
            brandpage = brandPageRepository.getBrandPageByBrandIdAndPageId(brandId, pageId);
            if (brandpage == null) {
                brandpage = new BrandpageEntity();
                brandpage.setBrandid(brandId);
                brandpage.setPageid(pageId);

            }
            brandpage.setCrawl(true);
            brandPageRepository.save(brandpage);


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
