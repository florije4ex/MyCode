package com.cui.code.net.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 预订卡信息
 */
@Data
@AllArgsConstructor
public class CardInfo {
    /**
     * 姓名
     */
    private String cardName;
    /**
     * 持卡 id
     */
    private String cardId;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 身份证号
     */
    private String idCard;
}
