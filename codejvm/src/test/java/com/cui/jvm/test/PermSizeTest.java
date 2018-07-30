package com.cui.jvm.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法区测试（永久代）
 *
 * @author cuishixiang
 * @date 2018-07-14
 */
public class PermSizeTest {

    /**
     * 方法区的OOM测试
     * jdk8之前的参数设置：-XX:PermSize=10M -XX:MaxPermSize=10M
     * Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
     * <p>
     * 在JDK8里面移除了永生代，而对于存放类的元数据的内存大小的设置变为Metaspace参数，可以通过参数-XX:MetaspaceSize 和-XX:MaxMetaspaceSize设定大小，但如果不指定MaxMetaspaceSize的话，Metaspace的大小仅受限于native memory的剩余大小。也就是说永久代的最大空间一定得有个指定值，而如果MaxPermSize指定不当，就会OOM
     * <p>
     * 从JDK7开始符号引用(Symbols)转移到了native heap;字面量(interned strings)转移到了java heap;类的静态变量(class statics)转移到了java heap。但永久代仍然存在于JDK7，并没有完全的移除，一直到JDK8才彻底移除
     *
     * 测试无效……😅
     */
    @Test
    public void testRuntimeConstantPoolOOM() {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

}
