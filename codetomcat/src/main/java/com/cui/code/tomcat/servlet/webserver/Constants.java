package com.cui.code.tomcat.servlet.webserver;

/**
 * 常量类
 * Created by cuishixiang on 2017-11-16.
 */
public final class Constants {
    // webroot目录用来存放html或者一些其他文件
    public static final String WEB_ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "webroot";

}
