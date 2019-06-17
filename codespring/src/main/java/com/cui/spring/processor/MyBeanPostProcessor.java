package com.cui.spring.processor;

import com.cui.spring.dto.LifeCycleDTO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理类
 *
 * @author cuiswing
 * @date 2019-06-16
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 后置处理……
        if (bean instanceof LifeCycleDTO) {
            System.out.println("LifeCycleDTO postProcessBeforeInitialization:" + beanName);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 后置处理……
        if (bean instanceof LifeCycleDTO) {
            System.out.println("LifeCycleDTO postProcessAfterInitialization:" + beanName);
        }
        return bean;
    }
}
