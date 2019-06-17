package com.cui.spring.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * aspect
 * <p>
 * AOP：动态代理
 * 在程序运行期间动态的将某段代码切入到指定方法指定位置而进行运行的编程方式
 * <p>
 * 1、导入spring aop包 spring-aspects
 * 2、编写业务逻辑类，
 * 3、编写切面类，切面方法：
 * 前置通知@Before、后置通知@After、返回通知@afterReturing、异常通知@AfterThrowing、环绕通知@Around
 * 具体的表达式参考spring的reference文档
 * 4、将切面类和业务逻辑类都加入到容器中
 * 5、启用基于注解的aop功能：@EnableAspectJAutoProxy
 *
 * @author cuiswing
 * @date 2019-06-16
 */
@Aspect
@Component
public class MyAspect {


    // 抽取公共表达式，下面的每个切点就可以通用了，方法名随意，主要是@Pointcut
    @Pointcut("execution(public String com.cui.spring.test.FXNewsProvider.getAndPersistNews())")
    public void pointCut() {

    }

    // 引用公共的切点
    @Before("com.cui.spring.test.aop.MyAspect.pointCut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("参数：" + Arrays.asList(args));
        String name = joinPoint.getSignature().getName();
        System.out.println("方法名：" + name);
        System.out.println("aop before……");
    }

    // 通用方法全部切，全部参数
    @After("execution(public String com.cui.spring.test.FXNewsProvider.*(..))")
    public void after() {
        System.out.println("aop after……");

    }

    // returning指定返回值
    @AfterReturning(value = "com.cui.spring.test.aop.MyAspect.pointCut()", returning = "result")
    public void returnValue(Object result) {
        System.out.println("aop result：" + result);
        System.out.println("aop return……");

    }

    @AfterThrowing(value = "com.cui.spring.test.aop.MyAspect.pointCut()", throwing = "exception")
    public void returnException(Exception exception) {
        System.out.println("aop return……");

    }

}
