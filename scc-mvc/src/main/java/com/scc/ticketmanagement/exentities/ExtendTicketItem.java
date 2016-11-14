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
    private String sendername;
    private String senderavt;
    private boolean page;
    private String endmessage;
    private String addedby;


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



    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getSenderavt() {
        return senderavt;
    }

    public void setSenderavt(String senderavt) {
        this.senderavt = senderavt;
    }
    public String getEndmessage() {
        return endmessage;
    }

    public void setEndmessage(String endmessage) {
        this.endmessage = endmessage;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }
}
