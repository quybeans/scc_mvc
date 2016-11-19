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

    @Query("select COUNT(p) from PageEntity p")
    long countPage();

    @Query("select p from PageEntity p where p.pageid IN " +
            "(SELECT m.pageid FROM BrandpageEntity m, BrandEntity b WHERE m.brandid = b.id AND b.id = :brandId)" +
            "AND p.accesstoken !='' ")
    List<PageEntity> getAllPageByBrandId(@Param("brandId") Integer brandId);

    @Query("select p from PageEntity p where p.pageid IN " +
            "(SELECT m.pageid FROM BrandpageEntity m, BrandEntity b WHERE m.brandid = b.id AND b.id = :brandId AND m.manage = TRUE)" +
            "AND p.accesstoken !='' AND p.active = TRUE")
    List<PageEntity> getAllActivePageByBrandId(@Param("brandId") Integer brandId);

    @Query("select p from PageEntity p where p.pageid IN " +
            "(SELECT m.pageid FROM BrandpageEntity m, BrandEntity b WHERE m.brandid = b.id AND b.id = :brandId AND m.crawl=TRUE)")
    List<PageEntity> getAllShowPostPageByBrandId(@Param("brandId") Integer brandId);

    @Query("select p from PageEntity p where p.pageid IN " +
            "(SELECT m.pageid FROM BrandpageEntity m, BrandEntity b WHERE m.brandid = b.id AND b.id = :brandId)")
    List<PageEntity> getAllCrawlerPageByBrandId(@Param("brandId") Integer brandId);

    List<PageEntity> findByPageidIn(List<String> pageid);
}
