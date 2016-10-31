package com.scc.ticketmanagement.serviceImplement;

import com.scc.ticketmanagement.entity.BrandpageEntity;
import com.scc.ticketmanagement.repository.BrandPageRepository;
import com.scc.ticketmanagement.service.BrandPageService;
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
    public void addBrandPage(int brandId, String pageId) {
        BrandpageEntity brandpage = null;
        try {
            brandpage = brandPageRepository.getBrandPageByBrandIdAndPageId(brandId, pageId);
            if (brandpage == null) {
                brandpage = new BrandpageEntity();
                brandpage.setBrandid(brandId);
                brandpage.setPageid(pageId);
            }

            brandpage.setActive(true);
            brandPageRepository.save(brandpage);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeBrandPage(int brandId, String pageId) {
        BrandpageEntity brandpage = brandPageRepository.getBrandPageByBrandIdAndPageId(brandId, pageId);
        if (brandpage != null){
            brandpage.setActive(false);
            brandPageRepository.save(brandpage);
        }

    }
}
