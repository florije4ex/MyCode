package com.cui.code.test;

import biz.proxy.UserServiceCglibProxyFactory;
import biz.proxy.UserServiceProxyFactory;
import biz.sevice.UserService;
import biz.sevice.impl.UserServiceImpl;
import org.junit.Test;

/**
 * Created by 世祥 on 2017/5/7.
 */
public class ProxyTest {

    @Test
    public void testProxy() {
        UserService userService = new UserServiceImpl();
        UserServiceProxyFactory proxyFactory = new UserServiceProxyFactory(userService);
        UserService userServiceProxy = proxyFactory.getUserServiceProxy();
        userServiceProxy.save();
        System.out.println(userServiceProxy instanceof UserServiceImpl);
    }

    @Test
    public void testCglibProxy() {
        UserService userService = new UserServiceImpl();
        UserServiceCglibProxyFactory proxyFactory = new UserServiceCglibProxyFactory();
        UserService userServiceProxy = proxyFactory.getUserServiceProxy();
        userServiceProxy.update();
        System.out.println(userServiceProxy instanceof UserServiceImpl);
    }

}
