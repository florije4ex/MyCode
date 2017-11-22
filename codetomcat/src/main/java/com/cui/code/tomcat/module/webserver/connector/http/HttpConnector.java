package com.cui.code.tomcat.module.webserver.connector.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * connector 等待http连接
 * Created by cuishixiang on 2017-11-22.
 */
public class HttpConnector implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(HttpConnector.class);
    // 是否关机
    private boolean shutdown = false;

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int port = 8088;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (UnknownHostException e) {
            logger.error("异常：未知的主机！", e);
            System.exit(1);
        } catch (IOException e) {
            logger.error("web server 启动异常，退出JVM：", e);
            System.exit(1);
        }

        while (!shutdown) {
            try{
                Socket socket = serverSocket.accept();

                HttpProcessor processor=new HttpProcessor(this);
                processor.process(socket);
            } catch (IOException e) {
                logger.error("出问题啦：", e);
            }
        }
    }

    /**
     * 启动新线程来等待连接
     */
    public void start() {
        new Thread(this).start();
    }
}
