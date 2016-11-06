package com.scc.ticketmanagement.ServiceImp;

import com.scc.ticketmanagement.Entities.CommentEntity;
import com.scc.ticketmanagement.Entities.PostEntity;
import com.scc.ticketmanagement.repositories.CommentRepository;
import com.scc.ticketmanagement.repositories.PostRepository;
import com.scc.ticketmanagement.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by QuyBean on 11/5/2016.
 */
@Service
@Transactional
public class PostServiceImp implements PostService {
    private static final int PAGE_SIZE = 20;


    @Autowired
    PostRepository postRepository;


    @Override
    public Page<PostEntity> getPostByPageIdwPage(List<String> pagelist, Integer pagenumber) {

        PageRequest request = new PageRequest(pagenumber - 1, PAGE_SIZE, Sort.Direction.DESC, "createdAt");
        return postRepository.findByCreatedByIn(pagelist, request);

    }
}
