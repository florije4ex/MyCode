package com.cui.code.spider.dal.dataobject;

import java.util.Date;

/**
 * @author cuishixiang
 * @date 2019-01-23
 */
public class DoubanTopicDetailDO {

    private Integer id;
    private String groupCode;
    private Integer topicId;
    private String topicName;
    private String topicContent;
    private String authorId;
    private String authorName;
    private Integer likeCount;
    private Integer collectionCount;
    private Date postedTime;
    private Date createTime;
    private Date updateTime;
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicContent() {
        return topicContent;
    }

    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Date getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(Date postedTime) {
        this.postedTime = postedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DoubanTopicDetailDO{" +
                "id=" + id +
                ", groupCode='" + groupCode + '\'' +
                ", topicId=" + topicId +
                ", topicName='" + topicName + '\'' +
                ", topicContent='" + topicContent + '\'' +
                ", authorId='" + authorId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", likeCount=" + likeCount +
                ", collectionCount=" + collectionCount +
                ", postedTime=" + postedTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}
