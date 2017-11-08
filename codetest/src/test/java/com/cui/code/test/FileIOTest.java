package com.cui.code.test;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * 文件以及IO测试
 * Created by cuishixiang on 2017-11-07.
 */
public class FileIOTest {

    @Test
    public void testFile() {
        File file = new File(".");
        System.out.println(file.getName());
        System.out.println(file.getPath());
        System.out.println(file.getAbsoluteFile());
        System.out.println(file.getAbsolutePath());
        System.out.println(file.getParent());
        System.out.println(file.exists());
        System.out.println(file.canRead());
        System.out.println(file.canWrite());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
        System.out.println(file.isAbsolute());
        System.out.println(file.lastModified());
        System.out.println(file.length());

        Arrays.stream(file.list()).forEach(System.out::println);

        System.out.println("separator：" + File.separatorChar);
        System.out.println("pathSeparator：" + File.pathSeparator);
        System.out.println(System.getProperty("user.dir"));
    }
}
