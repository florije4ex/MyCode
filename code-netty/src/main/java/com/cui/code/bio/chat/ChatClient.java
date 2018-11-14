package com.cui.code.bio.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 聊天客户端
 *
 * @author cuishixiang
 * @date 2018-11-08
 */
@Slf4j
public class ChatClient {

    private BufferedReader chatReader;
    private BufferedReader responseReader;
    private PrintWriter printWriter;
    private Socket socket;

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        if (args.length > 0) {
            ip = args[0];
        }
        ChatClient chatClient = new ChatClient();
        chatClient.login(ip);
        chatClient.chat();
    }

    // 开始聊天
    private void chat() {
        try {
            String line;
            while ((line = chatReader.readLine()) != null) {
                if (line.startsWith(ChatProtocolConstant.PRIVATE_TO_START)
                        && line.indexOf(ChatProtocolConstant.PRIVATE_USER_SPLIT) > 0) {

                    String[] split = line.substring(1).split(ChatProtocolConstant.PRIVATE_USER_SPLIT);
                    printWriter.println(ChatProtocolConstant.PRIVATE_ROUND + split[0] + ChatProtocolConstant.PRIVATE_SPLIT
                            + split[1] + ChatProtocolConstant.PRIVATE_ROUND);
                } else {
                    printWriter.println(ChatProtocolConstant.PUBLIC_ROUND + line + ChatProtocolConstant.PUBLIC_ROUND);
                }
                //todo 下线功能
                //todo 刷新好友列表功能
                //todo 建立群组功能
            }
        } catch (IOException e) {
            log.error("异常：", e);
            closeIO();
            System.exit(1);
        }
    }

    // 登陆
    private void login(String ip) {
        try {
            socket = new Socket(ip, ChatProtocolConstant.PORT);
            chatReader = new BufferedReader(new InputStreamReader(System.in));
            responseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                System.out.print("请输入用户名：");
                String user = chatReader.readLine();
                printWriter.println(ChatProtocolConstant.USER_LOGIN + user + ChatProtocolConstant.USER_LOGIN);
                String msg = responseReader.readLine();
                if (msg.equals(ChatProtocolConstant.USER_NAME_REPEAT)) {
                    log.info("该用户名\"{}\"重复，请重新输入!", user);
                } else if (msg.startsWith(ChatProtocolConstant.LOGIN_SUCCESS)) {
                    System.out.println(msg.substring(ChatProtocolConstant.PROTOCOL_LENGTH));
                    System.out.println("使用说明：\n群消息：直接输入后回车；\n私信：@用户名::内容，示例：@hezekang::吃饭了吗");
                    break;
                } else {
                    System.out.println("未知消息");
                    break;
                }
            }
            new Thread(new ChatClientHandler(responseReader)).start();
        } catch (IOException e) {
            log.error("客户端异常：", e);
            closeIO();
            System.exit(1);
        }
    }

    private void closeIO() {
        try {
            if (chatReader != null) {
                chatReader.close();
            }
            if (printWriter != null) {
                printWriter.close();
            }
            if (responseReader != null) {
                responseReader.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            log.error("关闭资源异常", e);
        }
    }
}
