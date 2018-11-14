package com.cui.code.nio;

import com.cui.code.bio.chat.ChatProtocolConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * NIO 聊天服务器
 *
 * @author cuishixiang
 * @date 2018-11-13
 */
@Slf4j
public class NioCharServer {

    /**
     * 保存用户和对应输出流之间的对应关系
     */
    public static Map<String, SelectionKey> clientMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        log.info("聊天室服务器启动{}，监听端口：{}", NioCharServer.class.getName(), ChatProtocolConstant.PORT);
        NioCharServer nioCharServer = new NioCharServer();
        nioCharServer.handle();
    }

    private void handle() {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            Selector selector = Selector.open();
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", ChatProtocolConstant.PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            // select() 操作是阻塞的
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    // 这个是只有ServerSocketChannel 才有的操作，其他Channel永远返回的是都是false
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        selectionKey.interestOps(SelectionKey.OP_ACCEPT);
                    }

                    // 如果selectKey对应的Channel有数据可读
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        StringBuilder msg = new StringBuilder();
                        try {
                            while (socketChannel.read(byteBuffer) > 0) {
                                byteBuffer.flip();
                                msg.append(ChatProtocolConstant.charset.decode(byteBuffer));
                            }
                        } catch (IOException e) {
                            selectionKey.cancel();
                            if (selectionKey.channel() != null) {
                                selectionKey.channel().close();
                            }
                        }

                        String userFrom = getUserNameByValue(selectionKey);

                        if (msg.length() > 0) {
                            String line = msg.toString();
                            String userOrMsg = ChatProtocolConstant.getRealMsg(line);

                            if (line.startsWith(ChatProtocolConstant.USER_LOGIN)
                                    && line.endsWith(ChatProtocolConstant.USER_LOGIN)) {
                                if (NioCharServer.clientMap.containsKey(userOrMsg)) {
                                    log.info("用户名重复：{}", userOrMsg);
                                    socketChannel.write(ChatProtocolConstant.charset.encode(ChatProtocolConstant.USER_NAME_REPEAT));
                                } else {
                                    log.info("用户登录：{}", userOrMsg);
                                    socketChannel.write(ChatProtocolConstant.charset.encode(ChatProtocolConstant.LOGIN_SUCCESS + "当前登录用户："
                                            + (NioCharServer.clientMap.size() + 1) + NioCharServer.clientMap.keySet()));
                                    for (SelectionKey sk : NioCharServer.clientMap.values()) {
                                        ((SocketChannel) sk.channel()).write(ChatProtocolConstant.charset.encode("新人上线：" + userOrMsg));
                                    }
                                    NioCharServer.clientMap.put(userOrMsg, selectionKey);
                                }
                            } else if (line.startsWith(ChatProtocolConstant.PRIVATE_ROUND)
                                    && line.endsWith(ChatProtocolConstant.PRIVATE_ROUND)) {
                                String[] split = userOrMsg.split(ChatProtocolConstant.PRIVATE_SPLIT);
                                String userTo = split[0];
                                String privateMsg = split[1];
                                if (!NioCharServer.clientMap.containsKey(userTo)) {
                                    log.info("用户未上线：{}", userTo);
                                    socketChannel.write(ChatProtocolConstant.charset.encode(userTo + "用户未上线。请留言"));
                                } else {
                                    SelectionKey toSk = NioCharServer.clientMap.get(userTo);
                                    ((SocketChannel) (toSk.channel())).write(ChatProtocolConstant.charset.encode(userFrom + "给你发来私信：" + privateMsg));
                                }
                            } else {
                                for (SelectionKey key : selector.keys()) {
                                    SelectableChannel channel = key.channel();
                                    if (channel instanceof SocketChannel) {
                                        ((SocketChannel) channel).write(ChatProtocolConstant.charset.encode(userFrom + "发来公告：" + userOrMsg));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("服务器异常", e);
        }
    }

    /**
     * 根据selectionKey获取用户名
     *
     * @param selectionKey
     * @return
     */
    public static String getUserNameByValue(SelectionKey selectionKey) {
        for (Map.Entry<String, SelectionKey> entry : clientMap.entrySet()) {
            if (entry.getValue() == selectionKey) {
                return entry.getKey();
            }
        }
        return null;
    }
}
