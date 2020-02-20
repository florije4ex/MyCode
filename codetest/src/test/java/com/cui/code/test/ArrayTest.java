package com.cui.code.test;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 数组复制功能测试：
     * java.util.ArrayList#toArray(java.lang.Object[])
     * 当参数的长度小于 list 长度时，返回的是新的对象；
     * 当参数长度>= list 长度时，直接修改的入参数据
     * 最终的复制功能是由native 方法实现的： java.lang.System#arraycopy(java.lang.Object, int, java.lang.Object, int, int)
     */
    @Test
    public void testArrayCopy() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        System.out.println(list);

        Object[] objects = list.toArray();
        System.out.println(objects);

        Integer[] integers1 = new Integer[0];
        Integer[] copyIntegers = list.toArray(integers1);
        System.out.println(integers1);
        System.out.println(copyIntegers);
        System.out.println(integers1 == copyIntegers);

        Integer[] integers2 = new Integer[list.size()];
        Integer[] copyIntegers2 = list.toArray(integers2);
        System.out.println(integers2);
        System.out.println(copyIntegers2);
        System.out.println(integers2 == copyIntegers2);
    }
}
