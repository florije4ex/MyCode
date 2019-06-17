package com.code.net.test.dto;

import java.util.Date;

/**
 * 每日号源信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
public class DutyCalendar {
    private Date dutyDate;
    private String dutyWeek;
    private String localDate;
    // 剩余号数：-1无号，0约满，>0有号
    private int remainAvailableNumber;

    public Date getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }

    public String getDutyWeek() {
        return dutyWeek;
    }

    public void setDutyWeek(String dutyWeek) {
        this.dutyWeek = dutyWeek;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public int getRemainAvailableNumber() {
        return remainAvailableNumber;
    }

    public void setRemainAvailableNumber(int remainAvailableNumber) {
        this.remainAvailableNumber = remainAvailableNumber;
    }

    @Override
    public String toString() {
        return "DutyCalendar{" +
                "dutyDate=" + dutyDate +
                ", dutyWeek='" + dutyWeek + '\'' +
                ", localDate='" + localDate + '\'' +
                ", remainAvailableNumber=" + remainAvailableNumber +
                '}';
    }
}
