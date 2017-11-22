package com.cui.code.tomcat.module.webserver.startup;

import com.cui.code.tomcat.module.webserver.connector.http.HttpConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;

/**
 * web server的启动入口，只做一件事，简单职责
 * <p>
 * Created by cuishixiang on 2017-11-22.
 */
public class Bootstrap {
    private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * web server启动
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        logger.info("web server开始启动，当前时间：{}", DateFormat.getDateTimeInstance().format(new Date()));
        HttpConnector connector = new HttpConnector();
        connector.start();

    }
}
