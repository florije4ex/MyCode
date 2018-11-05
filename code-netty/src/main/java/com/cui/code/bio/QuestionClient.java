package com.cui.code.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 问题多多
 *
 * @author cuishixiang
 * @date 2018-11-05
 */
@Slf4j
public class QuestionClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8181);
             BufferedReader questionReader = new BufferedReader(new InputStreamReader(System.in));

             PrintWriter questionWriter = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader answerReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            String question;
            int count = 1;
            while ((question = questionReader.readLine()) != null) {
                log.info("question {}:{}", count, question);
                questionWriter.println(question);
                log.info("answer {}:{}", count, answerReader.readLine());
                count++;
            }
        } catch (IOException e) {
            log.error("异常：", e);
        }

    }
}
