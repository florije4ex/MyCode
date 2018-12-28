package com.cui.code.spider.dal.dataobject;

import java.util.Date;

/**
 * @author cuishixiang
 * @date 2018-12-24
 */
public class DoubanGroupDO {
    private Integer id;
    private String code;
    private String name;
    private String logoUrl;
    private Integer attentionUser;
    private Date groupCreateDate;
    private String ownerId;
    private String ownerName;
    private Date createTime;
    private Date updateTime;
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getAttentionUser() {
        return attentionUser;
    }

    public void setAttentionUser(Integer attentionUser) {
        this.attentionUser = attentionUser;
    }

    public Date getGroupCreateDate() {
        return groupCreateDate;
    }

    public void setGroupCreateDate(Date groupCreateDate) {
        this.groupCreateDate = groupCreateDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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
        return "DoubanGroupDO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", attentionUser=" + attentionUser +
                ", groupCreateDate=" + groupCreateDate +
                ", ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", notes='" + notes + '\'' +
                '}';
    }
}
