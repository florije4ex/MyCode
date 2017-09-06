package com.cui.mybatis.dataobject;

import java.util.Date;

/**
 * OrdUser实体
 *
 * @author 系统生成
 */
public class OrdUser {
    /**
     * 唯一标识
     */
    private int id;
    /**
     * 唯一标识uuid
     */
    private String uuid;
    /**
     * 创建人
     */
    private int createUser = 0;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private int updateUser = 0;
    /**
     * 修改人时间
     */
    private Date updateTime;
    /**
     * 是否启用
     */
    private Integer disabled = 0;
    /**
     * 描述
     */
    private String remark = "";
    /**
     * 排序
     */
    private int orderBy;
    /**
     * 用户名
     */
    private String username = "";
    /**
     * 登录密码
     */
    private String loginPwd = "";
    /**
     * 交易密码，用户提交订单、还款时输入
     */
    private String dealPwd = "";
    /**
     * 电话
     */
    private String mobile = "";
    /**
     * 信用等级
     */
    private String honorLevel = "";
    /**
     * 薪水
     */
    private Integer salary = 0;
    /**
     * 是否欺诈
     */
    private Integer isQizha = 0;
    /**
     * 微信帐号
     */
    private String openId = "";
    /**
     * 在诺诺系统中的用户编号
     */
    private String nonobankUserid = "";
    /**
     * 徽商电子账户
     */
    private String onlineAccountNo = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(int updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getDealPwd() {
        return dealPwd;
    }

    public void setDealPwd(String dealPwd) {
        this.dealPwd = dealPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHonorLevel() {
        return honorLevel;
    }

    public void setHonorLevel(String honorLevel) {
        this.honorLevel = honorLevel;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getIsQizha() {
        return isQizha;
    }

    public void setIsQizha(Integer isQizha) {
        this.isQizha = isQizha;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNonobankUserid() {
        return nonobankUserid;
    }

    public void setNonobankUserid(String nonobankUserid) {
        this.nonobankUserid = nonobankUserid;
    }

    public String getOnlineAccountNo() {
        return onlineAccountNo;
    }

    public void setOnlineAccountNo(String onlineAccountNo) {
        this.onlineAccountNo = onlineAccountNo;
    }
}
