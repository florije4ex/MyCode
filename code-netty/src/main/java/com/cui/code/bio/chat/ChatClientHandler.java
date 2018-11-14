package com.cui.code.bio.chat;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author cuishixiang
 * @date 2018-11-08
 */
@Slf4j
public class ChatClientHandler implements Runnable {
    private BufferedReader responseReader;

    public ChatClientHandler(BufferedReader reader) {
        this.responseReader = reader;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = responseReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            log.error("聊天室客户端异常：", e);
        }
    }
}
