package com.cui.code.net.model.hospital;

import com.cui.code.net.model.SubscribeIdEnum;

import java.util.Date;

/**
 * 挂号预约信息
 *
 * @author cuiswing
 * @date 2019-05-18
 */
public class HospitalBookInfo {
    private String mobileNo;
    private String password;
    private String hospitalId;
    private String departmentId;
    private Date bookDate;
    private String name;
    private String dutySourceId;
    private String doctorName;
    private String dutyTime;

    private String cookies;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDutySourceId() {
        return dutySourceId;
    }

    public void setDutySourceId(String dutySourceId) {
        this.dutySourceId = dutySourceId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDutyTime() {
        return dutyTime;
    }

    public void setDutyTime(String dutyTime) {
        this.dutyTime = dutyTime;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }


    @Override
    public String toString() {
        return "HospitalBookInfo{" +
                "mobileNo='" + mobileNo + '\'' +
                ", password='" + password + '\'' +
                ", hospitalId='" + hospitalId + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", bookDate=" + bookDate +
                ", name='" + name + '\'' +
                ", dutySourceId='" + dutySourceId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", dutyTime='" + dutyTime + '\'' +
                ", cookies='" + cookies + '\'' +
                '}';
    }
}
