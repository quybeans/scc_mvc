package com.scc.ticketmanagement.Entities;

import javax.persistence.*;

/**
 * Created by Thien on 11/8/2016.
 */
@Entity
@Table(name = "messageitem", schema = "scc", catalog = "")
public class MessageitemEntity {
    private int itemId;
    private String messageIdStart;
    private String messageIdEnd;

    @Id
    @Column(name = "item_id")
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "message_id_start")
    public String getMessageIdStart() {
        return messageIdStart;
    }

    public void setMessageIdStart(String messageIdStart) {
        this.messageIdStart = messageIdStart;
    }

    @Basic
    @Column(name = "message_id_end")
    public String getMessageIdEnd() {
        return messageIdEnd;
    }

    public void setMessageIdEnd(String messageIdEnd) {
        this.messageIdEnd = messageIdEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageitemEntity that = (MessageitemEntity) o;

        if (itemId != that.itemId) return false;
        if (messageIdStart != null ? !messageIdStart.equals(that.messageIdStart) : that.messageIdStart != null)
            return false;
        if (messageIdEnd != null ? !messageIdEnd.equals(that.messageIdEnd) : that.messageIdEnd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + (messageIdStart != null ? messageIdStart.hashCode() : 0);
        result = 31 * result + (messageIdEnd != null ? messageIdEnd.hashCode() : 0);
        return result;
    }
}
