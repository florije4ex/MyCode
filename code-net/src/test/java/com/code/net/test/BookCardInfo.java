package com.code.net.test;

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
     * 预约日期，格式必须是：yyyy-MM-dd
     */
    private String bookDate;
    /**
     * 景区id
     */
    private String subscribeId;
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
     * 预约截止时间
     */
    private Date endTime;
    /**
     * 预订卡号
     */
    private List<String> cardNoList = new ArrayList<>();
    /**
     * 预订人的卡片列表
     */
    private List<CardInfo> cardInfoList = new ArrayList<>();


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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}

/**
 * 预订卡信息
 */
class CardInfo {
    private String cardId;
    private String cardNo;

    public CardInfo(String cardId, String cardNo) {
        this.cardId = cardId;
        this.cardNo = cardNo;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
