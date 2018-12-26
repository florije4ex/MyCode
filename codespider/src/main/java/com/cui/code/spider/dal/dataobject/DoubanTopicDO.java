package com.cui.code.spider.dal.dataobject;

import java.util.Date;

/**
 * @author cuishixiang
 * @date 2018-12-25
 */
public class DoubanTopicDO {

    private Integer id;
    private String groupCode;
    private Integer topicId;
    private String topicName;
    private String authorId;
    private String authorName;
    private Integer replyCount;
    private Date lastReplyTime;
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

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Date getLastReplyTime() {
        return lastReplyTime;
    }

    public void setLastReplyTime(Date lastReplyTime) {
        this.lastReplyTime = lastReplyTime;
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
        final StringBuffer sb = new StringBuffer("DoubanTopicDO{");
        sb.append("id=").append(id);
        sb.append(", groupCode='").append(groupCode).append('\'');
        sb.append(", topicId=").append(topicId);
        sb.append(", topicName='").append(topicName).append('\'');
        sb.append(", authorId=").append(authorId);
        sb.append(", authorName='").append(authorName).append('\'');
        sb.append(", replyCount=").append(replyCount);
        sb.append(", lastReplyTime=").append(lastReplyTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", notes='").append(notes).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
