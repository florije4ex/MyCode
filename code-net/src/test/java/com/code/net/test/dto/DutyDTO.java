package com.code.net.test.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 某一日期的医生值班信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
@Data
public class DutyDTO {
    private Integer dutyCode;
    private Integer dutyImgType;
    private String dutyImgUrl;
    private List<DutyDoctorInfo> detail;
}
