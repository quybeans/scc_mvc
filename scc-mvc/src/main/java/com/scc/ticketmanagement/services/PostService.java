package com.scc.ticketmanagement.services;

import com.scc.ticketmanagement.Entities.PostEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by QuyBean on 11/5/2016.
 */
public interface PostService {

    Page<PostEntity> getPostByPageIdwPage(List<String> pagelist,Integer pagenumber);
}
