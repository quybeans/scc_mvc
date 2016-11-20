package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.UserCommentEntity;
import com.scc.ticketmanagement.Entities.UserCommentEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by quybeans on 21/11/2016.
 */
public interface UserCommentRepository extends JpaRepository<UserCommentEntity, UserCommentEntityPK> {
}
