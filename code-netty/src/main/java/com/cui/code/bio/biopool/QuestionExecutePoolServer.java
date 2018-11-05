package com.cui.code.bio.biopool;

import com.cui.code.bio.QuestionServerHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * block IO server add thread pool
 * 问答系统
 *
 * @author cuishixiang
 * @date 2018-11-05
 */
@Slf4j
public class QuestionExecutePoolServer {

    /**
     * 启动服务
     *
     * @param args 传一个监听的端口号进来,没有就用默认的8181
     */
    public static void main(String[] args) {
        int port = 8182;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        log.info("{} 监听端口：{}", QuestionExecutePoolServer.class.getName(), port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            QuestionServerHandlerExecutePool questionServerHandlerExecutePool = new QuestionServerHandlerExecutePool(50, 1000);

            while (true) {
                Socket socket = serverSocket.accept();
                questionServerHandlerExecutePool.execute(new QuestionServerHandler(socket));
            }
        } catch (Exception e) {
            log.error("出现异常：", e);
        }
    }
}
