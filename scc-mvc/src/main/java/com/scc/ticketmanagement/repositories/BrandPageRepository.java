package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.BrandpageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
