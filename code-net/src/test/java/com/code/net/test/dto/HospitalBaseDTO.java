package com.code.net.test.dto;

/**
 * 基础字段DTO
 *
 * @author cuiswing
 * @date 2019-05-16
 */
public class HospitalBaseDTO {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "HospitalBaseDTO{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
