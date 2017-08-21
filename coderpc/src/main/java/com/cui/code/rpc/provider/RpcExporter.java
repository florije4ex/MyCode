package com.cui.code.rpc.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * PRC服务发布者
 * <br>1、监听客户端的tcp连接，接到请求后将其封装成task，由线程池执行
 * <br>2、将客户端的发过来的流反序列化成对象，并调用其方法获取结果
 * <br>3、将执行结果序列化后发给客户端
 * <p>
 * Created by cuishixiang on 2017-08-21.
 */
public class RpcExporter {
    private static final Logger logger = LoggerFactory.getLogger(RpcExporter.class);

    private static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void exporter(String hostName, int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(hostName, port));
        logger.debug("serverSocket绑定：{}:{}", hostName, port);
        while (true) {
            executor.execute(new ExporterTask(serverSocket.accept()));
        }
    }

    private static class ExporterTask implements Runnable {

        Socket socket;

        public ExporterTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            logger.info("开始执行任务");
            try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                String interfaceName = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class<?> service = Class.forName(interfaceName);
                Class[] parameterTypes = (Class[]) inputStream.readObject();
                Object[] arguments = (Object[]) inputStream.readObject();
                Method method = service.getMethod(methodName, parameterTypes);
                Object result = method.invoke(service.newInstance(), arguments);
                outputStream.writeObject(result);
                logger.debug("任务执行完成：{}", result);
            } catch (Exception e) {
                logger.error("rpc执行任务时异常：", e);
            }
        }
    }

}
