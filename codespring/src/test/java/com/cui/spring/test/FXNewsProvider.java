package com.cui.spring.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * Created by cuishixiang on 2017-10-31.
 */
@Component
public class FXNewsProvider {
    // 使用@Resource注解时，1、先按名称匹配来查找bean，2、如果名称找不到再按类型匹配来查找bean，
    // 如果这两种方式都没有找到，则报错：Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.cui.spring.test.IFXNewsPersister' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@javax.annotation.Resource(shareable=true, lookup=, name=, description=, authenticationType=CONTAINER, type=class java.lang.Object, mappedName=)}
    // 如果有多个相同class的，那么spring也不知道该注入哪个，也会报错：Caused by: org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'com.cui.spring.test.IFXNewsPersister' available: expected single matching bean but found 2: dowJonesNewsPersister,newsPersister
    @Resource
    private IFXNewsListener newsListener;
    // @Autowired 1、首先是按照类型来查找，如果没找到会报错：Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'com.cui.spring.test.IFXNewsPersister' available: expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
    // Autowired 有个required 属性设置是否是必须的，如果是false，没找到也不会报错；
    // 2、如果找到了多个则默认使用当前被注解的属性名称去查找，如果也没有找到，则会使用@Primary注入的bean首先注给当前bean,
    // 3、如果还没有，可以结合@Qualifier来指定名称

    // 还是用Resource吧，这个是jsr250的规范，而Autowired是spring自己定义的，而且最好不让让容器去乱找，最好直接指定名称
    // @Qualifier("")
    @Autowired
    private IFXNewsPersister newsPersister;
    // 还有一个jsr250规范使用@Inject注入bean,需要单独导包

    //通过setter方式注入时必须添加此构造函数，因为set是在构造出对象之后才发生的，如果没有此函数的话，Java没有默认给此class添加默认构造函数，就会挂了
    public FXNewsProvider() {
    }

    //构造注入方式必须添加此构造函数
    public FXNewsProvider(IFXNewsListener newsListener, IFXNewsPersister newsPersister) {
        this.newsListener = newsListener;
        this.newsPersister = newsPersister;
    }

    public String getAndPersistNews() {
        String[] newsIds = newsListener.getAvailableNewsIds();
//        if (ArrayUtils.isEmpty(newsIds)) {
//            return;
//        }
//
//        for (String newsId : newsIds) {
//            FXNewsBean newsBean = newsListener.getNewsByPK(newsId);
//            newPersistener.persistNews(newsBean);
//            newsListener.postProcessIfNecessary(newsId);
//        }
        return StringUtils.arrayToCommaDelimitedString(newsIds);
    }

    public IFXNewsListener getNewsListener() {
        return newsListener;
    }

    public void setNewsListener(IFXNewsListener newsListener) {
        this.newsListener = newsListener;
    }

    public IFXNewsPersister getNewsPersister() {
        return newsPersister;
    }

    public void setNewsPersister(IFXNewsPersister newsPersister) {
        this.newsPersister = newsPersister;
    }
}
