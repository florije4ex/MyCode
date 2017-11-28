package com.cui.code.zkrmi;

import com.cui.code.rmi.provider.OrderService;
import com.cui.code.rmi.provider.impl.OrderServiceImpl;

/**
 * 服务发布
 * <p>
 * 启动前需要zookeeper已启动且需要创建节点：create /registry null
 * <p>
 * 多次发布服务需要指定不同的端口：<br>
 * java ServerPublisher localhost 1199<br>
 * java ServerPublisher localhost 1299
 * <p>
 * Created by cuishixiang on 2017-11-28.
 */
public class ServerPublisher {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("please using command: java ServerPublisher <rmi_host> <rmi_port>");
            System.exit(-1);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        ServiceProvider provider = new ServiceProvider();

        OrderService orderService = new OrderServiceImpl();
        provider.publish(orderService, host, port);

        Thread.sleep(Long.MAX_VALUE);
    }
}
