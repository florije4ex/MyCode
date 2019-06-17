package com.code.net.test.dto;

import java.util.List;
import java.util.Map;

/**
 * 某一日期的医生值班信息
 *
 * @author cuiswing
 * @date 2019-05-16
 */
public class DutyDTO extends HospitalBaseDTO {
    private boolean hasError;
    private Map<Integer, List<DutyDoctorInfo>> data;

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<Integer, List<DutyDoctorInfo>> getData() {
        return data;
    }

    public void setData(Map<Integer, List<DutyDoctorInfo>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DutyDTO{" +
                "hasError=" + hasError +
                ", data=" + data +
                '}';
    }
}
