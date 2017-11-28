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
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        int port = 1099;
        String url = "rmi://localhost:1099/com.cui.code.rmi.provider.impl.OrderServiceImpl";
        LocateRegistry.createRegistry(port);
        Naming.rebind(url, new OrderServiceImpl());
    }
}
