package com.scc.ticketmanagement.Entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by QuyBean on 11/2/2016.
 */
@Entity
@Table(name = "notification", schema = "scc", catalog = "")
public class NotificationEntity {
    private int id;
    private Boolean readStatus;
    private Timestamp createdat;
    private String message;
    private int userid;
    private Integer type;
    private Integer senderid;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "read_status")
    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    @Basic
    @Column(name = "createdat")
    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "senderid")
    public Integer getSenderid() {
        return senderid;
    }

    public void setSenderid(Integer senderid) {
        this.senderid = senderid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotificationEntity that = (NotificationEntity) o;

        if (id != that.id) return false;
        if (userid != that.userid) return false;
        if (readStatus != null ? !readStatus.equals(that.readStatus) : that.readStatus != null) return false;
        if (createdat != null ? !createdat.equals(that.createdat) : that.createdat != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (senderid != null ? !senderid.equals(that.senderid) : that.senderid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (readStatus != null ? readStatus.hashCode() : 0);
        result = 31 * result + (createdat != null ? createdat.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + userid;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (senderid != null ? senderid.hashCode() : 0);
        return result;
    }
}
