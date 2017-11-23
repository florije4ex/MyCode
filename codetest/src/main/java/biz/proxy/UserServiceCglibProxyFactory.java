package biz.proxy;

import biz.sevice.UserService;
import biz.sevice.impl.UserServiceImpl;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib代理实现
 * Created by 世祥 on 2017/5/7.
 */
public class UserServiceCglibProxyFactory implements MethodInterceptor {

    public UserService getUserServiceProxy() {
        //生成代理对象
        Enhancer enhancer = new Enhancer();
        //设置要对谁进行代理
        enhancer.setSuperclass(UserServiceImpl.class);
        //代理要做的事
        enhancer.setCallback(this);
        //创建代理
        UserService userService = (UserService) enhancer.create();
        return userService;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("打开事务");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("提交事务");
        return result;
    }
}
