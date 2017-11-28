package com.cui.code.rmi.provider.impl;

import com.cui.code.rmi.dto.Order;
import com.cui.code.rmi.provider.OrderService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI服务实现:必须让实现类继承 java.rmi.server.UnicastRemoteObject 类
 * 并且构造器必须抛出 java.rmi.RemoteException 异常
 * Created by cuishixiang on 2017-11-28.
 */
public class OrderServiceImpl extends UnicastRemoteObject implements OrderService {


    public OrderServiceImpl() throws RemoteException {
    }

    /**
     * 保存订单
     *
     * @param order 订单信息
     * @return
     */
    @Override
    public Order save(Order order) {
        System.out.println(order);
        order.setId(22L);
        return order;
    }
}
