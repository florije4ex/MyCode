package com.cui.code.tomcat.servlet.webserver;

/**
 * 静态资源处理
 * Created by cuishixiang on 2017-11-16.
 */
public class StaticeResourceProcessor {

    /**
     * 静态资源处理方法
     *
     * @param request  请求
     * @param response 响应
     */
    public void process(Request request, Response response) {
        response.sendStaticResource();
    }
}
