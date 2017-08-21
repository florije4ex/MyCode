package com.cui.code.rpc.facade;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * PRC本地代理:
 * <br>将本地的接口调用转为JDK动态代理，在代理中实现远程调用
 * <br>创建socket客户端，连接指定远程服务提供者
 * <br>将需要调用的服务接口、方法名、参数类型、参数依次发给远程服务提供方
 * <br>阻塞等待返回结果
 * <p>
 * Created by cuishixiang on 2017-08-21.
 */
public class RpcImporter<S> {

    public S importer(final Class<?> serviceClass, final InetSocketAddress socketAddress) {
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{serviceClass.getInterfaces()[0]}, (proxy, method, args) -> {
            Socket socket = new Socket();
            socket.connect(socketAddress);

            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(serviceClass.getName());
            outputStream.writeUTF(method.getName());
            outputStream.writeObject(method.getParameterTypes());
            outputStream.writeObject(args);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return inputStream.readObject();
        });
    }
}
