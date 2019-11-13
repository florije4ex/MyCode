package com.code.net.test.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 医院科室可预约信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
@Data
public class HospitalCalendarListDTO {

    private List<DutyCalendar> calendars;
    /**
     * 医院科室推荐
     */
    private List<Object> dutyReHospital;
    private Date dutySourceTxDate;
    private List<Object> monthCalendarList;
    private Integer totalWeek;
}
