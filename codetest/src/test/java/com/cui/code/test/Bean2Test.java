package com.cui.code.test;

import biz.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by 世祥 on 2017/5/7.
 */
//创建容器
@RunWith(SpringJUnit4ClassRunner.class)
//指定spring的配置文件
@ContextConfiguration("classpath:config/applicationContext.xml")
public class Bean2Test {

//    @Resource
//    private UserVO userVO;

    /**
     * ClassPathXmlApplicationContext默认是从class目录下寻找配置文件
     */
    @Test
    public void beanTest() {
        //创建Spring的容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        //从容器中获取对象
        UserVO userVO = (UserVO) applicationContext.getBean("userVO");
        System.out.println(userVO);
    }

    /**
     * ClassPathXmlApplicationContext默认是从class目录下寻找配置文件
     * 如果配置文件不在class文件夹下则要加上目录
     */
    @Test
    public void beanTest2() {
        //创建Spring的容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config\\applicationContext.xml");
        //从容器中获取对象
        UserVO userVO = (UserVO) applicationContext.getBean("userVO");
        System.out.println(userVO);
    }

    /**
     * ClassPathXmlApplicationContext默认是从class目录下寻找配置文件
     * 如果配置文件不在class文件夹下则要加上目录
     */
    @Test
    public void beanTest3() {
        //创建Spring的容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext1.xml");
        //从容器中获取对象
        UserVO userVO = (UserVO) applicationContext.getBean("userVO3");
        System.out.println(userVO);
    }

    /**
     * ApplicationContext默认初始化所有的Singleton对象
     */
    @Test
    public void beanTest7() {
        //创建Spring的容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext1.xml");
    }

    /**
     * BeanFactory默认不会初始化所有对象
     */
    @Test
    public void beanTest8() {
        Resource resource = new ClassPathResource("applicationContext1.xml");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        new XmlBeanDefinitionReader(beanFactory).loadBeanDefinitions(resource);
    }

    /**
     * scope
     */
    @Test
    public void beanTest4() {
        //创建Spring的容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext1.xml");
        //从容器中获取对象
        UserVO userVO3 = (UserVO) applicationContext.getBean("userVO");
        UserVO userVO4 = (UserVO) applicationContext.getBean("userVO");
        UserVO userVO = (UserVO) applicationContext.getBean("userVO2");
        UserVO userVO2 = (UserVO) applicationContext.getBean("userVO2");
        System.out.println(userVO == userVO2);
        System.out.println(userVO3 == userVO4);
    }

    /**
     * init  destroy
     */
    @Test
    public void beanTest5() {
        //创建Spring的容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext1.xml");
        //关闭容器
        applicationContext.close();
    }


}
