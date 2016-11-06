package com.scc.ticketmanagement.repositories;

import com.scc.ticketmanagement.Entities.TicketitemEntity;
import com.scc.ticketmanagement.Entities.TicketitemEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by user on 11/2/2016.
 */
public interface TicketitemRepository extends JpaRepository<TicketitemEntity,TicketitemEntityPK> {


    @Query("select t from TicketitemEntity t where t.ticketid=:id")
    List<TicketitemEntity> getTicketItemByTicketID(@Param("id") Integer id);

    @Query("select t from TicketitemEntity t where t.commentid=:commentid")
    TicketitemEntity getTicketItemByCommentID(@Param("commentid") String commentid);
}
