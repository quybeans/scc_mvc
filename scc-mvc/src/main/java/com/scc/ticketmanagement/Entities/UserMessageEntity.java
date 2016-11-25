package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by Thien on 11/26/2016.
 */
@Entity
@Table(name = "user_message", schema = "scc", catalog = "")
@IdClass(UserMessageEntityPK.class)
public class UserMessageEntity {
    private String messageId;
    private int userId;

    @Id
    @Column(name = "message_id")
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMessageEntity that = (UserMessageEntity) o;

        if (userId != that.userId) return false;
        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + userId;
        return result;
    }
}
