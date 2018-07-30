package com.cui.jvm.test;

import org.junit.Test;

/**
 * GC test
 *
 * @author cuishixiang
 * @date 2018-07-14
 */
public class GCTest {

    private Object reference;
    private static final int _1MB = 1024 * 1024;
    /**
     * 用这个成员变量占点内存，以便在GC日志中查看是否被回收
     */
    private byte[] bigSize = new byte[2 * _1MB];

    /**
     * 测试引用计数内存回收：循环引用的对象无法回收
     * <p>
     * -XX:+PrintGCDetails
     */
    @Test
    public void testReferenceCount() {
        GCTest objA = new GCTest();
        GCTest objB = new GCTest();
        objA.reference = objB;
        objB.reference = objA;

        objA = null;
        objB = null;

        //GC后查看objA和objB是否被回收了
        System.gc();
    }


}
