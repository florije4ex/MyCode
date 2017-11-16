package com.cui.code.tomcat.simple.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 封装客户端带过来的信息
 * <p>
 * Created by cuishixiang on 2017-11-16.
 */
public class Request {
    private static final Logger logger = LoggerFactory.getLogger(Request.class);

    private InputStream input;
    private String uri;

    Request(InputStream input) {
        this.input = input;
    }

    /**
     * 解析socket的输入流，并将其请求的uri保存起来
     */
    protected void parse() {
        StringBuilder requestSb = new StringBuilder(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            logger.error("输入流读取异常：", e);
            i = -1;
        }

        for (int j = 0; j < i; j++) {
            requestSb.append((char) buffer[j]);
        }
        uri = parseUri(requestSb.toString());
    }

    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    public String getUri() {
        return uri;
    }
}
