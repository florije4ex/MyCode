package com.cui.jvm.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * jvm运行时测试观察，可使用jconsole可视化查看
 * <p>
 * Created by cuishixiang on 2017-09-01.
 */
public class JvmTest {

    static class OOMObject {
        //创建此对象时在堆内存Eden区中将分配1M的空间，是否可以分配这么长的数组？
        public byte[] bytes = new byte[1024 * 1024];
    }

    /**
     * 观察堆内存填充情况
     */
    @Test
    public void testFillHeap() throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Thread.sleep(100);
            list.add(new OOMObject());
        }
        System.gc();
    }


    /**
     * 测试堆内存溢出异常
     * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
     */
    @Test
    public void testOutOfMemory() {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }


}
