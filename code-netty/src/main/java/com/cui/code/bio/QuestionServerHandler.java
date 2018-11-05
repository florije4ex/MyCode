package com.cui.code.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 问题处理器
 *
 * @author cuishixiang
 * @date 2018-11-05
 */
@Slf4j
public class QuestionServerHandler implements Runnable {

    private Socket socket;

    public QuestionServerHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * 问题的具体处理
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true)
        ) {
            int count = 1;
            String question;
            while ((question = bufferedReader.readLine()) != null) {
                log.info("question: {}: {}", count, question);
                //先做分词，再语义分析，前后对话情景分析，搜索匹配答案？
                if (question.contains("name") || question.contains("姓名")) { // or other…… balabala
                    printWriter.println("my name is 小冰");
                    log.info("answer {}: {}", count, "my name is 小冰");
                } else if (question.contains("sex") || question.contains("性别")) {
                    printWriter.println("女");
                    log.info("answer {}: {}", count, "女");
                } else if (question.contains("age") || question.contains("年龄")) {
                    printWriter.println("my age is 8");
                    log.info("answer {}: {}", count, "my age is 8");
                } else if (question.contains("weather") || question.contains("天气")) {
                    printWriter.println("心情好，当然是晴天");
                    log.info("answer {}: {}", count, "心情好，当然是晴天");
                } else {
                    //……
                    printWriter.println("我还是个宝宝🤗，不知道怎么回答你的问题~");
                    log.info("answer {}: {}", count, "我还是个宝宝🤗，不知道怎么回答你的问题~");
                }
                count++;
            }
        } catch (IOException e) {
            log.error("处理出错", e);
        }
    }
}
