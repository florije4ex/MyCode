package com.cui.spring.test;

import com.cui.spring.test.config.BeanConfig;
import com.cui.spring.test.config.DataSource;
import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * BeanFactory 测试
 * BeanFactory的创建方式：4种，不应该说BeanFactory的创建，而是容器的创建方式。只是BeanFactory 是容器的一种体现。
 * <p>
 * Created by cuishixiang on 2017-10-31.
 */
public class BeanFactoryTest {

    // 1、直接编码方式
    @Test
    public void testCodeBeanRegister() {
        DefaultListableBeanFactory beanRegistry = new DefaultListableBeanFactory();
        BeanFactory container = bindViaCode(beanRegistry);
        FXNewsProvider newsProvider = (FXNewsProvider) container.getBean("djNewsProvider");
        System.out.println(newsProvider.getAndPersistNews());
    }

    private static BeanFactory bindViaCode(BeanDefinitionRegistry registry) {
        AbstractBeanDefinition newsProvider = new RootBeanDefinition(FXNewsProvider.class);
        AbstractBeanDefinition newsListener = new RootBeanDefinition(DowJonesNewsListener.class);
        AbstractBeanDefinition newsPersister = new RootBeanDefinition(DowJonesNewsPersister.class);
        // 将bean定义注册到容器中
        registry.registerBeanDefinition("djNewsProvider", newsProvider);
        registry.registerBeanDefinition("djListener", newsListener);
        registry.registerBeanDefinition("djPersister", newsPersister);
        // 指定依赖关系
        // 1. 可以通过构造方法注入方式
        //ConstructorArgumentValues argValues = new ConstructorArgumentValues();
        //argValues.addIndexedArgumentValue(0, newsListener);
        //argValues.addIndexedArgumentValue(1, newsPersister);
        //newsProvider.setConstructorArgumentValues(argValues);
        // 2. 或者通过setter方法注入方式
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("newsListener", newsListener));
        propertyValues.addPropertyValue(new PropertyValue("newPersistener", newsPersister));
        newsProvider.setPropertyValues(propertyValues);
        // 绑定完成
        return (BeanFactory) registry;
    }


    // 2、XML文件配置方式，将配置和关系放在了XML配置文件中而已，但是底层还是得用编码实现的
    @Test
    public void testXmlBeanFactory() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        BeanFactory beanFactory = bindViaXML(defaultListableBeanFactory);
        Date currentDate = (Date) beanFactory.getBean("haha");
        System.out.println(currentDate);
    }

    private BeanFactory bindViaXML(BeanDefinitionRegistry registry) {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(registry);
        xmlBeanDefinitionReader.loadBeanDefinitions("spring-beans-config.xml");
        return (BeanFactory) registry;
    }


    // 3、注解方式，这个得用ApplicationContext，底层也是代码实现啊
    @Test
    public void testAnnotationConfig() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-annotation-config.xml");
        // 这个是同名匹配的，写错就没法获取到bean
        FXNewsProvider newsProvider = (FXNewsProvider) context.getBean("FXNewsProvider");
        String news = newsProvider.getAndPersistNews();
        System.out.println(news);
    }

    // 第四种，完全使用注解，彻底抛弃了xml配置文件，采用了完全使用Java注解的方式。因为我们是Java程序员，应该写Java代码，所以就……这是别人的解释
    @Test
    public void testJavaAnnotationConfig() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanConfig.class);

        System.out.println("beanDefinitionNames：");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

        String osName = applicationContext.getEnvironment().getProperty("os.name");
        System.out.println("os：" + osName);

        FXNewsProvider djNewsProvider = (FXNewsProvider) applicationContext.getBean("fxNewsProvider");
        String news = djNewsProvider.getAndPersistNews();
        System.out.println(news);

        PersonFactoryBean bean = applicationContext.getBean(PersonFactoryBean.class);
        System.out.println(bean);


        Object personFactoryBean = applicationContext.getBean("personFactoryBean");
        System.out.println(personFactoryBean);

        Object factoryBean = applicationContext.getBean("&personFactoryBean");
        System.out.println(factoryBean);

        Object dataSource = applicationContext.getBean(DataSource.class);
        System.out.println(dataSource);


        applicationContext.close();
    }

}
