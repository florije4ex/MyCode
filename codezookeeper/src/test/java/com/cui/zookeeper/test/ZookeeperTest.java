package com.cui.zookeeper.test;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;

import java.util.List;

/**
 * Zookeeper测试
 * <p>
 * Created by cuishixiang on 2017-11-24.
 */
public class ZookeeperTest {


    /**
     * zookeeper的基本操作测试
     *
     * @throws Exception
     */
    @Test
    public void testZookeeper() throws Exception {

        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 3000, event -> {
            // 监控所有被触发的事件
            System.out.println("已经触发了" + event.getType() + "事件！");
        });
        // 创建一个目录节点
        zooKeeper.create("/zk", "cui".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zooKeeper.create("/zk/child", "testChildDataOne".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        byte[] zkdata = zooKeeper.getData("/zk", false, null);
        byte[] childdata = zooKeeper.getData("/zk/child", false, null);

        System.out.println(new String(zkdata));
        System.out.println(new String(childdata));

        // 取出子目录节点列表
        List<String> children = zooKeeper.getChildren("/zk", false);
        System.out.println(childdata);

        // 修改子目录节点数据
        zooKeeper.setData("/zk/child", "modifyChildDataOne".getBytes(), -1);
        System.out.println("目录节点状态：[" + zooKeeper.exists("/zk", true) + "]");

        // 创建另外一个子目录节点并添加watch
        zooKeeper.create("/zk/child2", "testChildDataTwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zooKeeper.getData("/zk/child2", true, null)));
        // 删除子目录节点
        zooKeeper.delete("/zk/child", -1);
        zooKeeper.delete("/zk/child2", -1);
        // 删除父目录节点
        zooKeeper.delete("/zk", -1);
        // 关闭连接
        zooKeeper.close();
    }
}
