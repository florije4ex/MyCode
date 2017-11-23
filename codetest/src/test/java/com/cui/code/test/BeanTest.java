package com.cui.code.test;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import java.util.Date;

/**
 * Created by cuishixiang on 17/5/3.
 */
public class BeanTest {

    @Test
    public void testXmlBeanFactory() {
        ClassPathResource resource = new ClassPathResource("beans.xml");
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);

        Date currentDate = (Date) defaultListableBeanFactory.getBean("haha");
        System.out.println(currentDate.toLocaleString());
    }


//    ContextLoaderListener
}
