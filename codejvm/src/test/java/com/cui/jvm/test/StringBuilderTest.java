package com.cui.jvm.test;

import java.io.IOException;
import java.util.Properties;

/**
 * 测试StringBuilder的连接，查看class文件
 * Created by cuishixiang on 2017-11-10.
 */
public class StringBuilderTest {

    private static Properties properties = new Properties();

    static {
        try {
            properties.load(StringConcatenationWithPropertiesTest.class.getResourceAsStream("/temp.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testStringBuilderConnectString() {
        String str = new StringBuilder(properties.getProperty("name")).append(properties.get("age")).toString();
    }
}
