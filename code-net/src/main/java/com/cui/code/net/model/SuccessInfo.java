package com.cui.code.net.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 预约成功后的信息
 *
 * @author CUI
 * @since 2019-12-26
 */
@Data
@AllArgsConstructor
public class SuccessInfo {
    /**
     * 预约成功的 id
     */
    private String id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 卡号
     */
    private String cardNo;
}
