package com.cui.jvm.test;

import org.junit.Test;

/**
 * 对象内存分配测试：
 * 1、新对象几乎都分配在Eden区
 * 2、大对象直接分配到Old区，对于Serial和ParNew两款收集器，可以设置这个大小值，-XX:PretenureSizeThreshold=1024   单位是byte
 * 3、
 * @author cuishixiang
 * @date 2018-07-15
 */
public class AllocationGCTest {
    private static final int _1MB = 1024 * 1024;

    /**
     * 测试内存分配：新对象默认分配在Eden区中，如果Eden没有足够空间时，将触发一次Minor GC
     * <p>
     * 限制这个Heap大小为20M，不可扩展，其中10M分配给新生代，Eden：Survivor=8：1，即Eden分8M,From、TO Survivor各分1M
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:+PrintGCDateStamps
     *
     * -XX:+PrintFlagsFinal and -XX:+PrintFlagsInitial
     */
    @Test
    public void testAllocation() {
        byte[] alloc1, alloc2, alloc3, alloc4;
        alloc1 = new byte[2 * _1MB];
        alloc2 = new byte[2 * _1MB];
        alloc3 = new byte[2 * _1MB];
        alloc4 = new byte[4 * _1MB];

    }
}
