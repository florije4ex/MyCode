package com.cui.tomcat.test;

import com.test.Myclass;
import org.junit.Test;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * ClassLoader测试
 * Created by cuishixiang on 2017-11-17.
 */
public class ClassLoaderTest {

    /**
     * class加载测试：loadClass必须要类的全限定名
     */
    @Test
    public void testURLClassLoader() {
        URLClassLoader loader = null;
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            System.out.println(path);
            String path4 = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
            System.out.println(path4);
            String path1 = this.getClass().getResource("").getPath();
            System.out.println(path1);
            String path2 = this.getClass().getResource("/").getPath();
            System.out.println(path2);
            String path3 = this.getClass().getResource(".").getPath();
            System.out.println(path3);


            String respository = new URL("file", null, path).toString();
            urls[0] = new URL(null, respository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Class className = loader.loadClass("com.test.Myclass");
            System.out.println(className.getName());
            Myclass myclass = (Myclass) className.newInstance();
            myclass.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
