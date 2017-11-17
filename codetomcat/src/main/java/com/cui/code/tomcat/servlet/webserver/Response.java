package com.cui.code.tomcat.servlet.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * 封装返回给客户端的数据,实现了ServletResponse接口
 * Created by cuishixiang on 2017-11-16.
 */
public class Response implements ServletResponse {
    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    private OutputStream output;
    private Request request;
    PrintWriter writer;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 发送静态资源
     */
    public void sendStaticResource() {
        File file = new File(Constants.WEB_ROOT, request.getUri());
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

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        writer = new PrintWriter(output, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {

    }

    @Override
    public void setContentLengthLong(long len) {

    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
