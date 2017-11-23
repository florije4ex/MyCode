package biz.proxy;


import biz.sevice.UserService;
import biz.sevice.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理实现
 * Created by 世祥 on 2017/5/7.
 */
public class UserServiceProxyFactory implements InvocationHandler {

    private UserService userService;

    public UserServiceProxyFactory(UserService userService) {
        this.userService = userService;
    }

    //动态代理
    public UserService getUserServiceProxy() {
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(
                UserServiceProxyFactory.class.getClassLoader(),
                UserServiceImpl.class.getInterfaces(),
                this);
        return userServiceProxy;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("打开事务");
        Object invoke = method.invoke(userService, args);
        System.out.println("提交事务");
        return invoke;
    }
}
