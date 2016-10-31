package com.scc.ticketmanagement.entity;

import javax.persistence.*;

/**
 * Created by QuyBeans on 11-Oct-16.
 */
@Entity
@Table(name = "comment", schema = "scc", catalog = "")
public class CommentEntity {
    private String id;
    private String content;
    private String createdAt;
    private String createdBy;
    private String postId;
    private String createdByName;
    private Integer sentimentScore;

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
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "post_id")
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Basic
    @Column(name = "created_by_name")
    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    @Basic
    @Column(name = "sentiment_score")
    public Integer getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(Integer sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (postId != null ? !postId.equals(that.postId) : that.postId != null) return false;
        if (createdByName != null ? !createdByName.equals(that.createdByName) : that.createdByName != null)
            return false;
        if (sentimentScore != null ? !sentimentScore.equals(that.sentimentScore) : that.sentimentScore != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (postId != null ? postId.hashCode() : 0);
        result = 31 * result + (createdByName != null ? createdByName.hashCode() : 0);
        result = 31 * result + (sentimentScore != null ? sentimentScore.hashCode() : 0);
        return result;
    }
}
