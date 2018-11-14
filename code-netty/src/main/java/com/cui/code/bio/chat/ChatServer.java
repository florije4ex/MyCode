package com.cui.code.bio.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 聊天室服务器
 *
 * @author cuishixiang
 * @date 2018-11-08
 */
@Slf4j
public class ChatServer {

    /**
     * 保存用户和对应输出流之间的对应关系
     */
    public static Map<String, PrintWriter> clientMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        log.info("聊天室服务器启动{}，监听端口：{}", ChatServer.class.getName(), ChatProtocolConstant.PORT);
        try (ServerSocket serverSocket = new ServerSocket(ChatProtocolConstant.PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ChatServerHandler(socket)).start();
            }
        } catch (IOException e) {
            log.error("服务器异常：", e);
        }
    }

    /**
     * 根据输出流获取用户名
     *
     * @param printWriter
     * @return
     */
    public static String getUserNameByValue(PrintWriter printWriter) {
        for (Map.Entry<String, PrintWriter> entry : clientMap.entrySet()) {
            if (entry.getValue() == printWriter) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void removeByValue(PrintWriter printWriter) {
        for (Map.Entry<String, PrintWriter> entry : clientMap.entrySet()) {
            if (entry.getValue() == printWriter) {
                clientMap.remove(entry.getKey());
                break;
            }
        }
    }
}
