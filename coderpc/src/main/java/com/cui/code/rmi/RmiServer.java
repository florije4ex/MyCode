package com.cui.code.rmi;

import com.cui.code.rmi.provider.impl.OrderServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 通过 JNDI 发布 RMI 服务
 * 需要告诉 JNDI 三个基本信息：1. 域名或 IP 地址（host）、2. 端口号（port）、3. 服务名（service），它们构成了 RMI 协议的 URL（或称为“RMI 地址”）：
 * <p>
 * rmi://host:port/service
 * <p>
 * Created by cuishixiang on 2017-11-28.
 */
public class RmiServer {

    /**
     * 启动RMI服务
     * <p>
     * Registry(注册表)是放置所有服务器对象的命名空间。
     * 每次服务端创建一个对象时，它都会使用bind()或rebind()方法注册该对象。
     * 这些是使用称为绑定名称的唯一名称注册的。
     * <p>
     * 要调用远程对象，客户端需要该对象的引用,如(orderService)。
     * 即通过服务端绑定的名称(orderService)从注册表中获取对象(lookup()方法)。
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 1099;
        // 绑定的URL标准格式为：rmi://host:port/name
        String url = "rmi://localhost:1099/com.cui.code.rmi.provider.impl.OrderServiceImpl";
        // 本地主机上的远程对象注册表Registry的实例,默认端口1099
        LocateRegistry.createRegistry(port);
        // 把远程对象OrderServiceImpl 注册到RMI注册服务器上，并命名为orderService
        Naming.rebind(url, new OrderServiceImpl());
        System.out.println("======= 启动RMI服务成功! =======");
    }
}
