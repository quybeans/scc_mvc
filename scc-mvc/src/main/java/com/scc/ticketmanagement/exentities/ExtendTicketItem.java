package com.scc.ticketmanagement.exentities;

import com.scc.ticketmanagement.Entities.*;

import java.sql.Timestamp;

/**
 * Created by user on 11/7/2016.
 */
public class ExtendTicketItem extends TicketitemEntity {

    private CommentEntity comment;
    private TicketHistory history;
    private MessageEntity message;
    private PostEntity post;

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public TicketHistory getHistory() {
        return history;
    }

    public void setHistory(TicketHistory history) {
        this.history = history;
    }

    public MessageEntity getMessage() {
        return message;
    }

    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
