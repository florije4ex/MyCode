package com.code.net.test.dto;

import java.security.PrivateKey;
import java.util.Date;

/**
 * 值班医生的具体信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
public class DutyDoctorInfo {
    private String departmentId;
    private String departmentName;
    private String deptCode;
    private String doctorId;
    private String doctorName;
    private String doctorTitleName;
    private String drCode;
    private String dutyCode;
    private int dutyCodeIndex;
    private String dutyCodeName;
    private Date dutyDate;
    private int dutySourceId;
    private int dutyStatus;
    private String dutyType;
    private int hospitalId;
    private int isShowFee;
    private String mapDoctorId;
    private String periodDutySources;
    private String planCode;
    private String portrait;
    // 剩余号
    private int remainAvailableNumber;
    // 技能说明："牙槽(只看拔牙)"
    private String skill;
    // 医事服务费
    private int totalFee;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTitleName() {
        return doctorTitleName;
    }

    public void setDoctorTitleName(String doctorTitleName) {
        this.doctorTitleName = doctorTitleName;
    }

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public String getDutyCode() {
        return dutyCode;
    }

    public void setDutyCode(String dutyCode) {
        this.dutyCode = dutyCode;
    }

    public int getDutyCodeIndex() {
        return dutyCodeIndex;
    }

    public void setDutyCodeIndex(int dutyCodeIndex) {
        this.dutyCodeIndex = dutyCodeIndex;
    }

    public String getDutyCodeName() {
        return dutyCodeName;
    }

    public void setDutyCodeName(String dutyCodeName) {
        this.dutyCodeName = dutyCodeName;
    }

    public Date getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(Date dutyDate) {
        this.dutyDate = dutyDate;
    }

    public int getDutySourceId() {
        return dutySourceId;
    }

    public void setDutySourceId(int dutySourceId) {
        this.dutySourceId = dutySourceId;
    }

    public int getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(int dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
    }

    public int getIsShowFee() {
        return isShowFee;
    }

    public void setIsShowFee(int isShowFee) {
        this.isShowFee = isShowFee;
    }

    public String getMapDoctorId() {
        return mapDoctorId;
    }

    public void setMapDoctorId(String mapDoctorId) {
        this.mapDoctorId = mapDoctorId;
    }

    public String getPeriodDutySources() {
        return periodDutySources;
    }

    public void setPeriodDutySources(String periodDutySources) {
        this.periodDutySources = periodDutySources;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getRemainAvailableNumber() {
        return remainAvailableNumber;
    }

    public void setRemainAvailableNumber(int remainAvailableNumber) {
        this.remainAvailableNumber = remainAvailableNumber;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    @Override
    public String toString() {
        return "DutyDoctorInfo{" +
                "departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorTitleName='" + doctorTitleName + '\'' +
                ", drCode='" + drCode + '\'' +
                ", dutyCode='" + dutyCode + '\'' +
                ", dutyCodeIndex=" + dutyCodeIndex +
                ", dutyCodeName='" + dutyCodeName + '\'' +
                ", dutyDate=" + dutyDate +
                ", dutySourceId=" + dutySourceId +
                ", dutyStatus=" + dutyStatus +
                ", dutyType='" + dutyType + '\'' +
                ", hospitalId=" + hospitalId +
                ", isShowFee=" + isShowFee +
                ", mapDoctorId='" + mapDoctorId + '\'' +
                ", periodDutySources='" + periodDutySources + '\'' +
                ", planCode='" + planCode + '\'' +
                ", portrait='" + portrait + '\'' +
                ", remainAvailableNumber=" + remainAvailableNumber +
                ", skill='" + skill + '\'' +
                ", totalFee=" + totalFee +
                '}';
    }
}
