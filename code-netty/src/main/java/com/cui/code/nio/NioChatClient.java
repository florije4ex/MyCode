package com.cui.code.nio;

import com.cui.code.bio.chat.ChatProtocolConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * NIO 聊天室客户端
 *
 * @author cuishixiang
 * @date 2018-11-13
 */
@Slf4j
public class NioChatClient {

    private BufferedReader chatReader;
    private SocketChannel socketChannel;

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        if (args.length > 0) {
            ip = args[0];
        }
        NioChatClient nioChatClient = new NioChatClient();
        nioChatClient.login(ip);
        nioChatClient.chat();
    }

    private void chat() {
        try {
            String line;
            while ((line = chatReader.readLine()) != null) {
                if (line.startsWith(ChatProtocolConstant.PRIVATE_TO_START)
                        && line.indexOf(ChatProtocolConstant.PRIVATE_USER_SPLIT) > 0) {

                    String[] split = line.substring(1).split(ChatProtocolConstant.PRIVATE_USER_SPLIT);
                    socketChannel.write(ChatProtocolConstant.charset.encode(ChatProtocolConstant.PRIVATE_ROUND
                            + split[0] + ChatProtocolConstant.PRIVATE_SPLIT
                            + split[1] + ChatProtocolConstant.PRIVATE_ROUND));
                } else {
                    socketChannel.write(ChatProtocolConstant.charset.encode(ChatProtocolConstant.PUBLIC_ROUND
                            + line + ChatProtocolConstant.PUBLIC_ROUND));
                }
                //todo 下线功能
                //todo 刷新好友列表功能
                //todo 建立群组功能
            }
        } catch (IOException e) {
            log.error("异常：", e);
            System.exit(1);
        }

    }

    private void login(String ip) {
        try {
            chatReader = new BufferedReader(new InputStreamReader(System.in));

            Selector selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(ip, ChatProtocolConstant.PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);

            while (true) {
                System.out.print("请输入用户名：");
                String user = chatReader.readLine();
                socketChannel.write(ChatProtocolConstant.charset.encode(ChatProtocolConstant.USER_LOGIN + user + ChatProtocolConstant.USER_LOGIN));
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                StringBuilder content = new StringBuilder();
                while (socketChannel.read(byteBuffer) > 0) {
                    byteBuffer.flip();
                    content.append(ChatProtocolConstant.charset.decode(byteBuffer));
                }
                String msg = content.toString();
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
            new Thread(new NioChatClientHandler(selector)).start();
        } catch (IOException e) {
            log.error("客户端异常：", e);
            System.exit(1);
        }

    }
}
