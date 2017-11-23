package com.cui.jvm.test;

import org.junit.Test;

/**
 * JVM栈测试
 * Created by cuishixiang on 2017-10-16.
 */
public class JvmStackTest {

    private int stackLength;

    //不停地递归调用
    private void recurses() {
        stackLength++;
        recurses();
    }

    /**
     * 测试栈溢出错误
     */
    @Test
    public void testStackLength() {
        try {
            recurses();
        } catch (Throwable e) {//注意这里是error
            System.out.println(stackLength);
//            throw e;
        }
    }
}
