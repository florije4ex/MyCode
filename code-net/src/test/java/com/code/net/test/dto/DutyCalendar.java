package com.code.net.test.dto;

import lombok.Data;

import java.util.Date;

/**
 * 每日号源信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
@Data
public class DutyCalendar {
    private Boolean countDown;
    /**
     * 预约日期
     */
    private Date dutyDate;
    /**
     * 预约状态：1-可预约，4-约满，32-无号
     */
    private Integer dutyStatus;
    /**
     * 周几：1~7
     */
    private String dutyWeek;
    private Date fhTime;
    private Integer fhTimeBetween;
    // 剩余号数：-1无号，0约满，>0有号
    private int remainAvailableNumber114;
    private int remainAvailableNumberWeb;
    private int tgTimeBetween;
    private int time24Between;
}
