package com.cui.code.dubbo.facade.impl;

import com.cui.code.dubbo.facade.OrderFacade;

import java.util.UUID;

/**
 * 服务接口实现
 * Created by cuishixiang on 2017-11-23.
 */
public class OrderFacadeImpl implements OrderFacade {
    /**
     * 保存订单
     *
     * @param order 模拟订单
     * @return 订单编号
     */
    @Override
    public String save(String order) {
        System.out.println(order + "已保存");
        return UUID.randomUUID().toString();
    }
}
