package com.cui.jvm.test;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器
 * 比较两个类是否相等只有当这两个类都由同一个类加载器加载时才相等，
 * 这里测试：自定义的类加载器加载了一个class，但是生成的对象并不是想象中的class实例，因为系统中存在两个ClassLoaderTest类，一个是由系统类加载器加载的，另一个是自定义类加载器加载的，所以是两个独立的类
 *
 * @author cuishixiang
 * @date 2019-03-14
 */
public class ClassLoaderTest {

    @Test
    public void testClassLoader() {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream inputStream = getClass().getResourceAsStream(filename);
                if (inputStream == null) {
                    return super.loadClass(name);
                }
                byte[] bytes;
                try {
                    bytes = new byte[inputStream.available()];
                    inputStream.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object instance = null;
        try {
            instance = classLoader.loadClass("com.cui.jvm.test.ClassLoaderTest").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(instance.getClass());
        System.out.println(instance instanceof ClassLoaderTest);

    }
}
