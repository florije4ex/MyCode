package com.cui.code.tomcat.simple.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 服务器-响应客户端的来访
 * <p>
 * Created by cuishixiang on 2017-11-16.
 */
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    // webroot目录用来存放html或者一些其他文件
    public static final String WEB_ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "webroot";

    // 关闭服务器命令
    private static final String SHUTDOWN_COMMAND = "/shutdown";

    // 是否关机
    private boolean shutdown = false;

    /**
     * web server的启动入口
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        logger.info("WEB_ROOT:{}", WEB_ROOT);
        server.await();
    }

    /**
     * 一直等待客户端的连接请求
     */
    private void await() {
        ServerSocket serverSocket = null;
        int port = 8088;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (UnknownHostException e) {
            logger.error("异常：未知的主机！", e);
        } catch (IOException e) {
            logger.error("web server 启动异常，退出JVM：", e);
            System.exit(1);
        }

        //循环，等待请求
        while (!shutdown) {
            try (Socket socket = serverSocket.accept();
                 InputStream input = socket.getInputStream();
                 OutputStream output = socket.getOutputStream();
            ) {
                //将客户端的输入流传给Request对象处理
                Request request = new Request(input);
                request.parse();

                //将输出流传给response对象处理
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                socket.close();
                shutdown = request.getUri().equalsIgnoreCase(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                logger.error("出问题啦：", e);
            }
        }
    }
}
