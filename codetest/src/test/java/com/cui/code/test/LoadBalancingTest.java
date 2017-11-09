package com.cui.code.test;

import org.junit.Test;

import java.util.*;

/**
 * 负载均衡算法测试
 * Created by cuishixiang on 2017-11-08.
 */
public class LoadBalancingTest {

    //本次请求对应的服务器位置
    private static Integer position = 0;

    /**
     * 轮询Round Robin测试
     */
    @Test
    public void testRoundRobin() {
        List<String> serverIpList = new ArrayList<>();
        serverIpList.add("192.168.0.1");
        serverIpList.add("192.168.0.2");
        serverIpList.add("192.168.0.3");
        serverIpList.add("192.168.0.4");
        serverIpList.add("192.168.0.5");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String processServerIP = getProcessServerIP(serverIpList);
                System.out.println("本次请求\"" + Thread.currentThread().getName() + "\"后端对应的处理服务器是：" + processServerIP);
            }, "线程" + i).start();
        }
    }

    /**
     * 加权轮询Weight Round Robin测试
     */
    @Test
    public void testWeightRoundRobin() {
        Map<String, Integer> serverIpWithWeight = new HashMap<>();
        serverIpWithWeight.put("192.168.0.1", 5);
        serverIpWithWeight.put("192.168.0.2", 3);
        serverIpWithWeight.put("192.168.0.3", 1);
        serverIpWithWeight.put("192.168.0.4", 1);


        List<String> serverIpList = new ArrayList<>();
        serverIpWithWeight.forEach((serverIp, weight) -> {
            while (weight >= 1) {
                serverIpList.add(serverIp);
                weight--;
            }
        });

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String processServerIP = getProcessServerIP(serverIpList);
                System.out.println("本次请求\"" + Thread.currentThread().getName() + "\"后端对应的处理服务器是：" + processServerIP);
            }, "线程" + i).start();
        }
    }


    /**
     * 随机Random测试
     */
    @Test
    public void testRandom() {
        List<String> serverIpList = new ArrayList<>();
        serverIpList.add("192.168.0.1");
        serverIpList.add("192.168.0.2");
        serverIpList.add("192.168.0.3");
        serverIpList.add("192.168.0.4");
        serverIpList.add("192.168.0.5");

        //处理的服务器统计
        Map<Integer, Integer> statistics = new HashMap<>();
        statistics.put(0, 0);
        statistics.put(1, 0);
        statistics.put(2, 0);
        statistics.put(3, 0);
        statistics.put(4, 0);

        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                int randomPosition = random.nextInt(serverIpList.size());
                statistics.put(randomPosition, statistics.get(randomPosition) + 1);
                System.out.println("本次请求\"" + Thread.currentThread().getName() + "\"后端对应的处理服务器是：" + serverIpList.get(randomPosition));
            }, "线程" + i).start();
        }
        System.out.println("服务器处理请求统计" + statistics);
    }

    /**
     * 原地址哈希Source Hash测试
     */
    @Test
    public void testSourceHash() {
        List<String> serverIpList = new ArrayList<>();
        serverIpList.add("192.168.0.1");
        serverIpList.add("192.168.0.2");
        serverIpList.add("192.168.0.3");
        serverIpList.add("192.168.0.4");
        serverIpList.add("192.168.0.5");

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            //创建一个随机远程ip
            int j = 1;
            StringJoiner stringJoiner = new StringJoiner(".");
            while (j++ <= 4) {
                stringJoiner.add(String.valueOf(random.nextInt(255)));
            }

            new Thread(() -> {
                String processServerIP = getHashServerIp(stringJoiner.toString(), serverIpList);
                System.out.println("本次请求\"" + Thread.currentThread().getName() + "\"后端对应的处理服务器是：" + processServerIP);
            }, "线程" + stringJoiner.toString()).start();
        }
    }

    /**
     * 最小连接数 Least Connections
     */
    @Test
    public void testLeastConnection() throws InterruptedException {
        //K-V：机器ip和连接数
        Map<String, Integer> serverIpMapWithConnection = new HashMap<>();
        serverIpMapWithConnection.put("192.168.0.1", 0);
        serverIpMapWithConnection.put("192.168.0.2", 0);
        serverIpMapWithConnection.put("192.168.0.3", 0);
        serverIpMapWithConnection.put("192.168.0.4", 0);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String processServerIP = getMinConnectionServerIP(serverIpMapWithConnection);
                System.out.println("本次请求\"" + Thread.currentThread().getName() + "\"后端对应的处理服务器是：" + processServerIP);
                //模拟执行耗时不同的请求
                try {
                    //执行前将连接数加1
                    serverIpMapWithConnection.put(processServerIP, serverIpMapWithConnection.get(processServerIP) + 1);
                    Thread.sleep(new Random().nextInt(100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //执行完毕后将连接数减1
                    serverIpMapWithConnection.put(processServerIP, serverIpMapWithConnection.get(processServerIP) - 1);
                }
            }, "线程" + i).start();
        }
        Thread.sleep(1000);
        System.out.println(serverIpMapWithConnection);
    }

    //获取连接数最小的机器ip
    private String getMinConnectionServerIP(Map<String, Integer> serverIpMapWithConnection) {
        //K-V：连接数：机器ip
        TreeMap<Integer, String> reserveCountIpMap = new TreeMap<>();
        for (Map.Entry<String, Integer> entry : serverIpMapWithConnection.entrySet()) {
            if (entry.getValue() == 0) {
                return entry.getKey();
            }
            reserveCountIpMap.put(entry.getValue(), entry.getKey());
        }
        return reserveCountIpMap.firstEntry().getValue();
    }


    private String getHashServerIp(String remoteIp, List<String> serverIpList) {
        int index = remoteIp.hashCode() % serverIpList.size();
        return serverIpList.get(Math.abs(index));
    }


    //获取处理的服务器IP
    private String getProcessServerIP(List<String> serverIpList) {
        synchronized (position) {
            if (position >= serverIpList.size()) {
                position = 0;
            }
            String serverIp = serverIpList.get(position);
            position++;
            return serverIp;
        }
    }
}
