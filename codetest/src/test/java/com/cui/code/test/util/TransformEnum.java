package com.cui.code.test.util;

/**
 * 坐标转换枚举值
 *
 * @author cuishixiang
 * @date 2018-12-10
 */
public enum TransformEnum {
    /**
     * WGS84坐标转国测局坐标（高德坐标）
     */
    WGS84ToGcj02,
    /**
     * 不需要转换
     */
    NO_TRANSFORM;
}
