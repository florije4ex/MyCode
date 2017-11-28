package com.cui.code.zkrmi;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

/**
 * RMI服务提供者
 * Created by cuishixiang on 2017-11-28.
 */
public class ServiceProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProvider.class);

    // 用于等待 SyncConnected 事件触发后继续执行当前线程
    private CountDownLatch latch = new CountDownLatch(1);

    // 发布 RMI 服务并注册 RMI 地址到 ZooKeeper 中
    public void publish(Remote remote, String host, int port) {
        String url = publishService(remote, host, port);
        if (url != null) {
            ZooKeeper zk = connectServer(); // 连接 ZooKeeper 服务器并获取 ZooKeeper 对象
            if (zk != null) {
                createNode(zk, url); // 创建 ZNode 并将 RMI 地址放入 ZNode 上
            }
        }
    }

    /**
     * 发布 RMI 服务并返回 RMI 地址
     */
    private String publishService(Remote remote, String host, int port) {
        String url = null;
        try {
            url = String.format("rmi://%s:%d/%s", host, port, remote.getClass().getName());
            LocateRegistry.createRegistry(port);
            Naming.rebind(url, remote);
            LOGGER.debug("publish rmi service (url: {})", url);
        } catch (RemoteException | MalformedURLException e) {
            LOGGER.error("", e);
        }
        return url;
    }

    // 连接 ZooKeeper 服务器
    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(ConfigConstant.ZK_CONNECTION_STRING, ConfigConstant.ZK_SESSION_TIMEOUT, event -> {
                if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                    latch.countDown(); // 唤醒当前正在执行的线程
                }
            });
            latch.await(); // 使当前线程处于等待状态
        } catch (IOException | InterruptedException e) {
            LOGGER.error("", e);
        }
        return zk;
    }

    // 创建 ZNode
    private void createNode(ZooKeeper zk, String url) {
        try {
            byte[] data = url.getBytes();
            // 创建一个临时性且有序的 ZNode
            String path = zk.create(ConfigConstant.ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            LOGGER.debug("create zookeeper node ({} => {})", path, url);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("", e);
        }
    }
}
