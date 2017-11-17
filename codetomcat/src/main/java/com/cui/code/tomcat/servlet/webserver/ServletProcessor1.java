package com.cui.code.tomcat.servlet.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

/**
 * servlet处理
 * Created by cuishixiang on 2017-11-16.
 */
public class ServletProcessor1 {
    private static final Logger logger = LoggerFactory.getLogger(ServletProcessor1.class);

    /**
     * servlet处理方法
     *
     * @param request  请求
     * @param response 响应
     */
    public void process(Request request, Response response) {
        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf('/') + 1);
        URLClassLoader loader;

        try {
            URL[] urls = new URL[1];
            URLStreamHandler urlStreamHandler = null;
//            File classPath = new File(Constants.WEB_ROOT);
            String currentPath = this.getClass().getResource("").getPath();
            String repository = (new URL("file", null, currentPath)).toString();
            urls[0] = new URL(null, repository, urlStreamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            logger.error("创建类加载器时出错：", e);
            return;
        }

        try {
//            Class myClass = loader.loadClass("com.cui.code.tomcat.servlet.webserver." + servletName);
            Class myClass = Class.forName("com.cui.code.tomcat.servlet.webserver." + servletName);
            Servlet servlet = (Servlet) myClass.newInstance();
            /*
             * 此处的问题：将request和response对象传给了servlet对象的service方法，
			 * 如果使用servlet的程序员知道其原理，然后又将这两个对象向下转型成Request和Response对象，
			 * 则可调用其自带的public方法，这可能造成问题：比如调用response的sendStaticResource()方法，
			 * 但是你又不能将这些public方法私有化private，因为这些方法可能在其他类中被调用
			 *
			改进：使用facade设计模式：RequestFacade，此类也实现ServletRequest，并将request对象传给他，
				然后在此类所有的重载方法中调用request对象的同名方法
			*/
            servlet.service(request, response);
        } catch (ClassNotFoundException e) {
            logger.error("未找到类{}：", servletName, e);
        } catch (Exception e) {
            logger.error("servlet处理时出错：", e);
        }
    }
}
