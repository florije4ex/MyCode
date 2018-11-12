package com.cui.code.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * 数组相关测试
 *
 * @author cuishixiang
 * @date 2018-11-12
 */
public class ArrayTest {

    /**
     * 数组的打印
     */
    @Test
    public void testPrintArray() {
        Long[] ids = {12L, 34L};
        System.out.println(ids);

        long[] ids2 = {11L, 22L};
        System.out.println(ids2);

        System.out.println(Arrays.toString(ids));
    }
}
