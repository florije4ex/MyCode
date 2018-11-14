package com.cui.code.bio.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 聊天消息处理器
 *
 * @author cuishixiang
 * @date 2018-11-08
 */
@Slf4j
public class ChatServerHandler implements Runnable {

    private Socket socket;

    public ChatServerHandler(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        PrintWriter printWriter = null;
        String userFrom = null;
        try (BufferedReader chatReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            String line;
            userFrom = ChatServer.getUserNameByValue(printWriter);
            while ((line = chatReader.readLine()) != null) {
                dealMessage(printWriter, userFrom, line);
            }
        } catch (IOException e) {
            log.error("消息处理异常：", e);
            ChatServer.removeByValue(printWriter);
            printWriter.close();
            for (PrintWriter clientWriter : ChatServer.clientMap.values()) {
                clientWriter.println("系统通知：" + userFrom + "已下线");
            }
        }
    }

    private void dealMessage(PrintWriter printWriter, String userFrom, String line) {
        String userOrMsg = ChatProtocolConstant.getRealMsg(line);
        if (line.startsWith(ChatProtocolConstant.USER_LOGIN)
                && line.endsWith(ChatProtocolConstant.USER_LOGIN)) {
            if (ChatServer.clientMap.containsKey(userOrMsg)) {
                log.info("用户名重复：{}", userOrMsg);
                printWriter.println(ChatProtocolConstant.USER_NAME_REPEAT);
            } else {
                log.info("用户登录：{}", userOrMsg);
                ChatServer.clientMap.put(userOrMsg, printWriter);
                printWriter.println(ChatProtocolConstant.LOGIN_SUCCESS + "当前登录用户数：" + ChatServer.clientMap.size()
                        + ChatServer.clientMap.keySet());
                for (PrintWriter clientWriter : ChatServer.clientMap.values()) {
                    clientWriter.println("新人上线：" + userOrMsg);
                }
            }
        } else if (line.startsWith(ChatProtocolConstant.PRIVATE_ROUND)
                && line.endsWith(ChatProtocolConstant.PRIVATE_ROUND)) {
            String[] split = userOrMsg.split(ChatProtocolConstant.PRIVATE_SPLIT);
            String userTo = split[0];
            String msg = split[1];
            if (!ChatServer.clientMap.containsKey(userTo)) {
                log.info("用户未上线：{}", userTo);
                printWriter.println(userTo + "用户未上线。请留言");
            } else {
                PrintWriter toUserWriter = ChatServer.clientMap.get(userTo);
                toUserWriter.println(userFrom + "给你发来私信：" + msg);
            }
        } else {
            for (PrintWriter clientWriter : ChatServer.clientMap.values()) {
                clientWriter.println(userFrom + "发来公告：" + userOrMsg);
            }
        }
    }

}
