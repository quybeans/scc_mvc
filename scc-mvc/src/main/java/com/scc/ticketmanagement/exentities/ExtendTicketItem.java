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
    private String postidofcomment;
    private String endmessage;

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

    public String getPostidofcomment() {
        return postidofcomment;
    }

    public void setPostidofcomment(String postidofcomment) {
        this.postidofcomment = postidofcomment;
    }

    public String getEndmessage() {
        return endmessage;
    }

    public void setEndmessage(String endmessage) {
        this.endmessage = endmessage;
    }
}
