package com.cui.tomcat.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket连接测试
 * <p>
 * Created by cuishixiang on 2017-11-15.
 */
public class SocketTest {

    /**
     * 直接使用socket输出和获取数据
     */
    @Test
    public void testSocket() {
        try {
            Socket socket = new Socket("www.baidu.com", 80);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("GET /index.html HTTP/1.1");
            printWriter.println("host:www.baidu.com");
            printWriter.println("Connection:close");
            printWriter.println();
            //两种输出方式，这种效果也是一样的
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
//            outputStreamWriter.write("GET / HTTP/1.1\n");
//            outputStreamWriter.write("host:www.baidu.com\n");
//            outputStreamWriter.write("Connection:close\n");
//            outputStreamWriter.write("\n");
//            outputStreamWriter.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                if (reader.ready()) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    break;
                }
                Thread.sleep(100L);
            }
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
