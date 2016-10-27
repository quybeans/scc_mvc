package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Thien on 10/11/2016.
 */
public interface PageRepository extends JpaRepository<PageEntity, String> {
    @Query("select p from PageEntity p where p.pageid = :pageid")
    PageEntity getPageById(@Param("pageid") String pageid);

    @Query("select p from PageEntity p where p.pageid IN " +
            "(SELECT m.pageid FROM BrandpageEntity m, BrandEntity b WHERE m.brandid = b.id AND b.id = :brandId)")
    List<PageEntity> getAllPageByBrandId(@Param("brandId") Integer brandId);
}
