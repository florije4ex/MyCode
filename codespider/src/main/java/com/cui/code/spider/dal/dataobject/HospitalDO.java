package com.cui.code.spider.dal.dataobject;

import lombok.Data;

/**
 * 114预约医院
 *
 * @author cuiswing
 * @date 2019-06-28
 */
@Data
public class HospitalDO {
    private String name;
    private String level;
    private Integer hospitalId;
    private String startTime;
    private String phone;
    private String address;
}
