package com.code.net.test;

import java.util.Date;

/**
 * 预订卡信息
 *
 * @author cuishixiang
 * @date 2018-12-12
 */
public class BookCardInfo {
    private String cardId;
    private String cardNo;
    private String bookDate;
    private String subscribeId;
    private String subscribeCalendarId;
    /**
     * 是否定时抢票
     */
    private boolean timing;
    /**
     * 定时开抢时间
     */
    private Date timingStartTime;

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
}
