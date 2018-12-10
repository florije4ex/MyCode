package com.cui.code.test;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * String基础测试
 *
 * @author cuishixiang
 * @date 2018-06-04
 */
public class StringTest {

    /**
     * 手机号、身份证号隐藏中间几位数
     */
    @Test
    public void testReplaceAll() {
        String phone = "13300001234";
        phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        System.out.println(phone);

        String idCard = "430409200001017733";
        idCard = idCard.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1*****$2");
        System.out.println(idCard);
    }


    /**
     * MessageFormat 的格式化是从下标{0}开始的
     */
    @Test
    public void testMessageFormat() {
        String format = MessageFormat.format("{0},{1},{3}", 1, "222", 3L, 4.0);
        String format2 = MessageFormat.format("{2},{1},{2}", 1, "222", 3L, 4.0);
        String format3 = MessageFormat.format("{1},{2},{3}", 1, "222", 3L, Arrays.asList(1, 2, 3));

        System.out.println(format);
        System.out.println(format2);
        System.out.println(format3);
    }
}
