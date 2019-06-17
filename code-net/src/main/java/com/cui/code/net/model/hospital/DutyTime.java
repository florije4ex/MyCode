package com.cui.code.net.model.hospital;

/**
 * 值班时间段
 *
 * @author cuiswing
 * @date 2019-06-11
 */
public enum DutyTime {
    MORNING(1, "上午"),
    AFTERNOON(2, "下午"),
    EVENING(4, "下午");

    private int code;
    private String time;

    DutyTime(int code, String time) {
        this.code = code;
        this.time = time;
    }

    /**
     * 通过值班区段时间获取值班时间Code
     *
     * @param time 值班区段时间
     * @return 值班时间Code
     */
    public static DutyTime getDutyTimeByTime(String time) {
        for (DutyTime dutyTime : DutyTime.values()) {
            if (dutyTime.getTime().equals(time)) {
                return dutyTime;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getTime() {
        return time;
    }
}
