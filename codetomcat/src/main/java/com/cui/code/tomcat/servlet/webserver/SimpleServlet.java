package com.cui.code.tomcat.servlet.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 写个Servlet的简单实现类来当做web app
 * Created by cuishixiang on 2017-11-16.
 */
public class SimpleServlet implements Servlet {
    private static final Logger logger = LoggerFactory.getLogger(SimpleServlet.class);


    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("{} inited.", getClass());
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 处理请求
     *
     * @param request  请求对象，实际是自定义的Request对象
     * @param response 响应对象，实际是自定义的Response对象
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        logger.info("{}开始处理请求", getClass());
        PrintWriter writer = response.getWriter();
        writer.println("我已经处理完毕！");
        writer.print("请继续其他操作");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
