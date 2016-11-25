package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.BrandpageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Thien on 10/24/2016.
 */
public interface BrandPageRepository extends JpaRepository<BrandpageEntity, Integer> {

    @Query("SELECT b FROM BrandpageEntity b WHERE b.brandid = :brandId AND b.pageid = :pageId")
    BrandpageEntity getBrandPageByBrandIdAndPageId(@Param("brandId") Integer brandId, @Param("pageId") String pageId);

    //Find all page by brandId
    @Query("SELECT b.pageid FROM BrandpageEntity b WHERE b.brandid = :brandId")
    List<String> getAllPagesByBrandid(@Param("brandId") Integer brandId);

    //Count pages that brand have
    @Query("SELECT COUNT(b) FROM BrandpageEntity b WHERE b.brandid = :brandId")
    int countPageByBrandId(@Param("brandId") Integer brandId);

    @Modifying
    @Transactional
    @Query("DELETE FROM BrandpageEntity b WHERE b.brandid =:brandId AND b.pageid =:pageId")
    int deleteBrandPageByPageIdAndBrandId(@Param("brandId") Integer brandId , @Param("pageId") String pageId);
}
