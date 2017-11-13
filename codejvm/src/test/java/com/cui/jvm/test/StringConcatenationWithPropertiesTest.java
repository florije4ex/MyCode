package com.cui.jvm.test;

import java.io.IOException;
import java.util.Properties;

/**
 * 测试String的连接符+，数据来自于属性文件，查看class文件
 * Created by cuishixiang on 2017-11-10.
 */
public class StringConcatenationWithPropertiesTest {

    public static Properties properties = new Properties();

    static {
        try {
            properties.load(StringConcatenationWithPropertiesTest.class.getResourceAsStream("/temp.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testPropertiesConnectString() {
        String str = properties.getProperty("name") + properties.getProperty("age");
    }
}
