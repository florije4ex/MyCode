package com.cui.code.tomcat.servlet.webserver;

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
 * servlet容器的升级版，添加了facade design pattern
 * <p>
 * Created by cuishixiang on 2017-11-16.
 */
public class HttpServer2 {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer2.class);

    // 关闭服务器命令
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // 是否关机
    private boolean shutdown = false;

    /**
     * web server的启动入口
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        HttpServer2 server = new HttpServer2();
        logger.info("WEB_ROOT:{}", Constants.WEB_ROOT);
        server.await();
    }

    private void await() {
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
            try (Socket socket = serverSocket.accept();
                 InputStream input = socket.getInputStream();
                 OutputStream output = socket.getOutputStream();
            ) {
                Request request = new Request(input);
                request.parse();

                Response response = new Response(output);
                response.setRequest(request);
                //判断请求的是静态资源还是servlet，以“/servlet”开头的uri是请求servlet
                if (request.getUri().startsWith("/servlet")) {
                    ServletProcessor2 processor = new ServletProcessor2();
                    processor.process(request, response);
                } else {
                    StaticeResourceProcessor processor = new StaticeResourceProcessor();
                    processor.process(request, response);
                }

                socket.close();
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                logger.error("出问题啦：", e);
            }
        }
    }
}
