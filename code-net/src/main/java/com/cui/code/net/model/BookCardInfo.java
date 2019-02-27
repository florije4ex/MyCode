package com.cui.code.net.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预订卡信息
 *
 * @author cuishixiang
 * @date 2018-12-12
 */
public class BookCardInfo {
    /**
     * 登陆后的JSESSIONID
     */
    private String JSESSIONID;
    /**
     * 预约日期，格式必须是：yyyy-MM-dd
     */
    private String bookDate;
    /**
     * 景区id
     */
    private String subscribeId;
    /**
     * 景区名称，必须取SubscribeIdEnum中对应的名称
     */
    private String subscribeName;
    /**
     * 预定日期id
     */
    private String subscribeCalendarId;
    /**
     * 预约成功后是否开启邮件通知功能
     */
    private boolean emailNotice;
    /**
     * 是否定时抢票
     */
    private boolean timing;
    /**
     * 定时开抢时间
     */
    private Date timingStartTime;
    /**
     * 方便yaml注入的字段
     */
    private String timingStartTimeConfig;
    /**
     * 预约截止时间
     */
    private Date endTime;
    /**
     * 预约截止时间,注入
     */
    private String endTimeConfig;
    /**
     * 预订卡号
     */
    private List<String> cardNoList = new ArrayList<>();
    /**
     * 预订人的卡片列表
     */
    private List<CardInfo> cardInfoList = new ArrayList<>();


    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getSubscribeName() {
        return subscribeName;
    }

    public void setSubscribeName(String subscribeName) {
        this.subscribeName = subscribeName;
    }

    public String getSubscribeCalendarId() {
        return subscribeCalendarId;
    }

    public void setSubscribeCalendarId(String subscribeCalendarId) {
        this.subscribeCalendarId = subscribeCalendarId;
    }

    public boolean isEmailNotice() {
        return emailNotice;
    }

    public void setEmailNotice(boolean emailNotice) {
        this.emailNotice = emailNotice;
    }

    public boolean isTiming() {
        return timing;
    }

    public void setTiming(boolean timing) {
        this.timing = timing;
    }

    public Date getTimingStartTime() {
        return timingStartTime;
    }

    public void setTimingStartTime(Date timingStartTime) {
        this.timingStartTime = timingStartTime;
    }

    public String getTimingStartTimeConfig() {
        return timingStartTimeConfig;
    }

    public void setTimingStartTimeConfig(String timingStartTimeConfig) {
        this.timingStartTimeConfig = timingStartTimeConfig;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEndTimeConfig() {
        return endTimeConfig;
    }

    public void setEndTimeConfig(String endTimeConfig) {
        this.endTimeConfig = endTimeConfig;
    }

    public List<String> getCardNoList() {
        return cardNoList;
    }

    public void setCardNoList(List<String> cardNoList) {
        this.cardNoList = cardNoList;
    }

    public List<CardInfo> getCardInfoList() {
        return cardInfoList;
    }

    public void setCardInfoList(List<CardInfo> cardInfoList) {
        this.cardInfoList = cardInfoList;
    }

    public void addCardNo(String cardNo) {
        this.cardNoList.add(cardNo);
    }

    public void addCardInfo(CardInfo cardInfo) {
        this.cardInfoList.add(cardInfo);
    }

    @Override
    public String toString() {
        return "BookCardInfo{" +
                "JSESSIONID='" + JSESSIONID + '\'' +
                ", bookDate='" + bookDate + '\'' +
                ", subscribeId='" + subscribeId + '\'' +
                ", subscribeName='" + subscribeName + '\'' +
                ", subscribeCalendarId='" + subscribeCalendarId + '\'' +
                ", emailNotice=" + emailNotice +
                ", timing=" + timing +
                ", timingStartTime=" + timingStartTime +
                ", timingStartTimeConfig='" + timingStartTimeConfig + '\'' +
                ", endTime=" + endTime +
                ", endTimeConfig='" + endTimeConfig + '\'' +
                ", cardNoList=" + cardNoList +
                ", cardInfoList=" + cardInfoList +
                '}';
    }
}

