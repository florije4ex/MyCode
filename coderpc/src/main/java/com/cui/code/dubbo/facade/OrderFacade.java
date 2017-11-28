package com.cui.code.dubbo.facade;

/**
 * 服务接口，定义成单独的模块，参见http://dubbo.io/ <br>
 * Since both service provider and service consumer rely on the same interface, it is strongly recommended to put the interface definition below in one separated module which could be shared by both provider module and consumer module.
 * <p>
 * Created by cuishixiang on 2017-11-23.
 */
public interface OrderFacade {

    /**
     * 保存订单
     *
     * @param order 模拟订单
     * @return 订单编号
     */
    String save(String order);
}
