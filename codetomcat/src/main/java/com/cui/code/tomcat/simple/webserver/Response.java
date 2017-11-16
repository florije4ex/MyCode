package com.cui.code.tomcat.simple.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 封装返回给客户端的数据
 * <p>
 * Created by cuishixiang on 2017-11-16.
 */
public class Response {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private OutputStream output;
    private Request request;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() {
        File file = new File(HttpServer.WEB_ROOT, request.getUri());
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file));
//                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output, "UTF-8"));
            ) {
                String readLine;
                while ((readLine = reader.readLine()) != null) {
//                    bufferedWriter.write(readLine);
                    output.write(readLine.getBytes());
                    output.write("\n".getBytes());
                }
            } catch (IOException e) {
                logger.error("出问题了：", e);
            }
        } else {
            //文件不存在，则返回404的经典错误
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n"
                    + "Content-Type:text/html\r\n"
                    + "Content-Length:23\r\n"
                    + "\r\n"
                    + "<h1>File Not Found</h1>";
            try {
                output.write(errorMessage.getBytes());
            } catch (IOException e) {
                logger.error("输出404错误页面时发生错误：", e);
            }
        }
    }
}
