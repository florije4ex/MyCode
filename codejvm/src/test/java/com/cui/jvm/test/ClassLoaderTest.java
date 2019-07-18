package com.cui.jvm.test;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

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

    /**
     * 从file文件系统中加载远程class文件
     * 如果是自己直接写的话，目录应该以/ 结尾，并且在package名称的第一级时截止
     * loadClass方法的参数应该是class的全限定名
     */
    @Test
    public void testURLClassLoaderFromFile() {
        try {
            File file = new File("/Users/cuiswing/IdeaProjects/others/MyCode/codetest/target/test-classes/");
            System.out.println("file exist：" + file.exists());
            URL url = file.toURL();
            // URL url = new URL("file:/Users/cuiswing/IdeaProjects/others/MyCode/codetest/target/test-classes/");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            Class<?> thisClass = classLoader.loadClass("com.cui.code.test.model.EvilUser");
            Object evilUser = thisClass.newInstance();
            System.out.println(evilUser);
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从http web服务器中加载远程class文件
     * 测试结果：NoPackageTest.class 如果没有指定 package 包名，可以直接打到jar包里面，
     * 如果是带包名的，必须把包名的文件路径也一起打进jar包里面去才行
     * <p>
     * 如果是直接返回class文件也没法通过：http://localhost:8080/evil/NoPackageTest.class
     */
    @Test
    public void testURLClassLoaderFromHttp() {
        try {
            URL url = new URL("http://localhost:8080/evil/evil.jar");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
            Class<?> thisClass = classLoader.loadClass("com.cui.code.test.model.EvilUser");
            Object evilUser = thisClass.newInstance();
            System.out.println(evilUser);
            // System.out.println(thisClass.getMethods()[0].getName());
            classLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
