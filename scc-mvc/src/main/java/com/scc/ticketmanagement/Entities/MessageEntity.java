package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Thien on 11/8/2016.
 */
@Entity
@Table(name = "message", schema = "scc", catalog = "")
public class MessageEntity {
    private String id;
    private String content;
    private Timestamp createdAt;
    private String receiverName;
    private String receiverid;
    private String senderName;
    private String senderid;
    private int seq;
    private Boolean messageRead;
    private int sentimentScrore;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "created_at")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "receiver_name")
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Basic
    @Column(name = "receiverid")
    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    @Basic
    @Column(name = "sender_name")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Basic
    @Column(name = "senderid")
    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    @Basic
    @Column(name = "seq")
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "message_read")
    public Boolean getMessageRead() {
        return messageRead;
    }

    public void setMessageRead(Boolean messageRead) {
        this.messageRead = messageRead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (seq != that.seq) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (receiverName != null ? !receiverName.equals(that.receiverName) : that.receiverName != null) return false;
        if (receiverid != null ? !receiverid.equals(that.receiverid) : that.receiverid != null) return false;
        if (senderName != null ? !senderName.equals(that.senderName) : that.senderName != null) return false;
        if (senderid != null ? !senderid.equals(that.senderid) : that.senderid != null) return false;
        if (messageRead != null ? !messageRead.equals(that.messageRead) : that.messageRead != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (receiverName != null ? receiverName.hashCode() : 0);
        result = 31 * result + (receiverid != null ? receiverid.hashCode() : 0);
        result = 31 * result + (senderName != null ? senderName.hashCode() : 0);
        result = 31 * result + (senderid != null ? senderid.hashCode() : 0);
        result = 31 * result + seq;
        result = 31 * result + (messageRead != null ? messageRead.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "sentiment_scrore")
    public int getSentimentScrore() {
        return sentimentScrore;
    }

    public void setSentimentScrore(int sentimentScrore) {
        this.sentimentScrore = sentimentScrore;
    }
}
