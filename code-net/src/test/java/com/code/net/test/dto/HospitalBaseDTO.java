package com.code.net.test.dto;

import lombok.Data;

/**
 * 基础字段DTO
 *
 * @author cuiswing
 * @date 2019-05-16
 */
@Data
public class HospitalBaseDTO<T> {
    private Integer resCode;
    private String msg;
    private T data;
}
