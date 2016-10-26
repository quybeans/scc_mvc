package com.scc.ticketmanagement.repositories;

/**
 * Created by QuyBeans on 10-Oct-16.
 */
import com.scc.ticketmanagement.Entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostRepository extends JpaRepository<PostEntity, String> {

    //Find All posts by Page
    List<PostEntity> findByCreatedBy(String id);
}
