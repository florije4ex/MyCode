package com.cui.code.tomcat.module.webserver;

import com.cui.code.tomcat.module.webserver.connector.http.HttpRequest;
import com.cui.code.tomcat.module.webserver.connector.http.HttpResponse;

import java.io.IOException;

/**
 * Created by cuishixiang on 2017-11-22.
 */
public class StaticResourceProcessor {
    /**
     * 静态资源处理方法
     *
     * @param request  请求
     * @param response 响应
     */
    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
