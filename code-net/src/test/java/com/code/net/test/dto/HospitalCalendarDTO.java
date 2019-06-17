package com.code.net.test.dto;

import sun.jvm.hotspot.HotSpotTypeDataBase;

import java.util.Date;
import java.util.List;

/**
 * 医院科室可预约信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
public class HospitalCalendarDTO extends HospitalBaseDTO {

    private Date currentTime;
    private List<DutyCalendar> dutyCalendars;
    private Date dutySourceTxDate;
    private Date fhDateTime;
    private long fhTimebetween;
    private boolean hasNumber;
    private boolean isOpenFhtx;
    private int lastWeek;
    private String monthBetween;
    private boolean showTWC;
    private Date tgDateTime;
    private long tgTimebetween;
    private Date todayDate;
    private int week;


    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public List<DutyCalendar> getDutyCalendars() {
        return dutyCalendars;
    }

    public void setDutyCalendars(List<DutyCalendar> dutyCalendars) {
        this.dutyCalendars = dutyCalendars;
    }

    public Date getDutySourceTxDate() {
        return dutySourceTxDate;
    }

    public void setDutySourceTxDate(Date dutySourceTxDate) {
        this.dutySourceTxDate = dutySourceTxDate;
    }

    public Date getFhDateTime() {
        return fhDateTime;
    }

    public void setFhDateTime(Date fhDateTime) {
        this.fhDateTime = fhDateTime;
    }

    public long getFhTimebetween() {
        return fhTimebetween;
    }

    public void setFhTimebetween(long fhTimebetween) {
        this.fhTimebetween = fhTimebetween;
    }

    public boolean isHasNumber() {
        return hasNumber;
    }

    public void setHasNumber(boolean hasNumber) {
        this.hasNumber = hasNumber;
    }

    public boolean isOpenFhtx() {
        return isOpenFhtx;
    }

    public void setOpenFhtx(boolean openFhtx) {
        isOpenFhtx = openFhtx;
    }

    public int getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(int lastWeek) {
        this.lastWeek = lastWeek;
    }

    public String getMonthBetween() {
        return monthBetween;
    }

    public void setMonthBetween(String monthBetween) {
        this.monthBetween = monthBetween;
    }

    public boolean isShowTWC() {
        return showTWC;
    }

    public void setShowTWC(boolean showTWC) {
        this.showTWC = showTWC;
    }

    public Date getTgDateTime() {
        return tgDateTime;
    }

    public void setTgDateTime(Date tgDateTime) {
        this.tgDateTime = tgDateTime;
    }

    public long getTgTimebetween() {
        return tgTimebetween;
    }

    public void setTgTimebetween(long tgTimebetween) {
        this.tgTimebetween = tgTimebetween;
    }

    public Date getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(Date todayDate) {
        this.todayDate = todayDate;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    @Override
    public String toString() {
        return "HospitalCalendarDTO{" +
                "currentTime=" + currentTime +
                ", dutyCalendars=" + dutyCalendars +
                ", dutySourceTxDate=" + dutySourceTxDate +
                ", fhDateTime=" + fhDateTime +
                ", fhTimebetween=" + fhTimebetween +
                ", hasNumber=" + hasNumber +
                ", isOpenFhtx=" + isOpenFhtx +
                ", lastWeek=" + lastWeek +
                ", monthBetween='" + monthBetween + '\'' +
                ", showTWC=" + showTWC +
                ", tgDateTime=" + tgDateTime +
                ", tgTimebetween=" + tgTimebetween +
                ", todayDate=" + todayDate +
                ", week=" + week +
                '}';
    }
}
