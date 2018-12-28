package com.cui.code.spider.dal.dataobject;

/**
 * @author cuishixiang
 * @date 2018-12-28
 */
public class DoubanGroupMemberDO {
    private String groupCode;
    private String memberId;
    private String memberName;
    private Integer type;
    private String location;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "DoubanGroupMemberDO{" +
                "groupCode='" + groupCode + '\'' +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", type=" + type +
                ", location='" + location + '\'' +
                '}';
    }
}
