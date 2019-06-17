package com.cui.spring.dto;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * inti test
 *
 * @author cuiswing
 * @date 2019-06-16
 */
public class LifeCycleDTO implements InitializingBean, DisposableBean {

    public LifeCycleDTO() {
        System.out.println("LifeCycleDTO 构造器……" + this);
    }

    public void init() {
        System.out.println("LifeCycleDTO init……" + this);
    }

    public void destroyBean() {
        System.out.println("LifeCycleDTO destroyBean……" + this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("LifeCycleDTO InitializingBean afterPropertiesSet ……" + this);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("LifeCycleDTO DisposableBean destroy……" + this);
    }

    @PostConstruct
    public void postCont() {
        System.out.println("LifeCycleDTO PostConstruct ……" + this);
    }

    @PreDestroy
    public void preDest() {
        System.out.println("LifeCycleDTO PreDestroy ……" + this);
    }
}
