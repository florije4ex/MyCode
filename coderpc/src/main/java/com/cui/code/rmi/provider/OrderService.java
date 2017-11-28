package com.cui.code.rmi.provider;

import com.cui.code.rmi.dto.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI服务接口：RMI接口实际上还是一个普通的 Java 接口，只是 RMI 接口必须继承 java.rmi.Remote，
 * 此外，每个 RMI 接口的方法必须声明抛出一个 java.rmi.RemoteException 异常
 * Created by cuishixiang on 2017-11-28.
 */
public interface OrderService extends Remote {

    /**
     * 保存订单
     *
     * @param order 订单信息
     * @return
     */
    Order save(Order order) throws RemoteException;
}
