package com.scc.ticketmanagement.repository;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
import com.scc.ticketmanagement.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<PostEntity, String> {

    //Find All posts by Page
    List<PostEntity> findByCreatedBy(String id);
}
