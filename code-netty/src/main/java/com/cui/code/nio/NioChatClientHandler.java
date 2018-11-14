package com.cui.code.nio;

import com.cui.code.bio.chat.ChatProtocolConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author cuishixiang
 * @date 2018-11-13
 */
@Slf4j
public class NioChatClientHandler implements Runnable {
    private Selector selector;

    public NioChatClientHandler(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (selector.select() > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        StringBuilder content = new StringBuilder();
                        while (channel.read(buffer) > 0) {
                            buffer.flip();
                            content.append(ChatProtocolConstant.charset.decode(buffer));
                        }
                        System.out.println(content);
                        selectionKey.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        } catch (IOException e) {
            log.error("客户端异常", e);
            try {
                selector.close();
            } catch (IOException e1) {
                log.error("selector关闭异常", e);
            }
        }
    }
}
