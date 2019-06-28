package com.cui.code.net.model.hospital;

import lombok.Data;

import java.util.Date;

/**
 * 挂号预约信息
 *
 * @author cuiswing
 * @date 2019-05-18
 */
@Data
public class HospitalBookInfo {
    private String mobileNo;
    private String password;
    /**
     * 医院名称，主要用于从配置文件中读取后转换为医院id
     * 必须是 properties/hospital.properties 文件中写的名称
     */
    private String hospitalName;
    private Integer hospitalId;
    private String departmentId;
    private Date bookDate;
    private String name;
    // 值班id
    private String dutySourceId;
    private String doctorName;
    private String dutyTime;

    private String cookies;
}
