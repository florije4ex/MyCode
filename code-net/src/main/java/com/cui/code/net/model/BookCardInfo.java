package com.cui.code.net.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 预订卡信息
 *
 * @author cuishixiang
 * @date 2018-12-12
 */
@Data
public class BookCardInfo {
    /**
     * 登陆后的JSESSIONID
     */
    private String JSESSIONID;
    /**
     * 可接受的预约日期列表，格式必须是：yyyy-MM-dd
     */
    private List<String> bookDateList;
    /**
     * 实际预约到的日期
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
    /**
     * 预约成功后的相关信息
     */
    private List<SuccessInfo> successInfoList = new ArrayList<>();

    public void addCardInfo(CardInfo cardInfo) {
        this.cardInfoList.add(cardInfo);
    }
}

