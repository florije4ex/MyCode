package com.cui.code.bio;

import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * block IO server
 * 问答系统
 *
 * @author cuishixiang
 * @date 2018-11-05
 */
@Slf4j
public class QuestionServer {

    /**
     * 启动服务
     *
     * @param args 传一个监听的端口号进来,没有就用默认的8181
     */
    public static void main(String[] args) {
        int port = 8181;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        log.info("{} 监听端口：{}", QuestionServer.class.getName(), port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new QuestionServerHandler(socket)).start();
            }
        } catch (Exception e) {
            log.error("出现异常：", e);
        }

        try (ServerSocket serverSocket2 = new ServerSocket(8182)) {
            while (true) {
                Socket socket2 = serverSocket2.accept();
                new Thread(new QuestionServerHandler(socket2)).start();
            }
        } catch (Exception e) {
            log.error("出现异常：", e);
        }


    }
}
