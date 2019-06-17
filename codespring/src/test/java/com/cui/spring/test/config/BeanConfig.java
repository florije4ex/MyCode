package com.cui.spring.test.config;

import com.cui.spring.dto.LifeCycleDTO;
import com.cui.spring.dto.Order;
import com.cui.spring.dto.Person;
import com.cui.spring.service.TestService;
import com.cui.spring.test.*;
import com.cui.spring.test.condition.LinuxCondition;
import com.cui.spring.test.condition.WindowsCondition;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * bean的注入配置信息
 * 采用@Configuration注解代替了xml配置文件 相当于<beans></beans>
 * 使用 @ComponentScan 代替了 <context:component-scan></context:component-scan> 的包扫描，
 * 使用@ComponentScan也可以像xml一样指定扫描规则：只扫描哪些，排除哪些，这个是可重复的注解。
 * 扫描规则：
 * value : 指定要扫描的包
 * includeFilters：指定只扫描哪些类，扫描规则有按注解、正则、自定义等来判断的
 * 当判断规则为 FilterType.ANNOTATION 需要注意使用这个 useDefaultFilters 属性，否则Component等都会被扫描的
 * FilterType.ASSIGNABLE_TYPE : 这个只扫描指定的class及其子类
 *
 * @author cuiswing
 * @date 2019-06-16
 */
@Configuration
@ComponentScan(value = "com.cui.spring", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Component.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = TestService.class)
}, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)

}, useDefaultFilters = false)
@ComponentScan(value = "com.cui.spring.dao", includeFilters = {
        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TestTypeFilter.class)
}, useDefaultFilters = false)
@Import(Order.class)
@EnableAspectJAutoProxy
public class BeanConfig {

    // 使用@Bean代替了<bean>，注册的bean的id默认是方法名，改名可以指定@bean中的value值，或者修改方法名。
    @Bean
    // 作用范围默认是singleton：单例，可以设置为 prototype 多实例
    @Scope(value = "prototype")
    public FXNewsProvider fxNewsProvider() {
        return new FXNewsProvider();
    }

    @Bean
    // 默认创建时加实例化了bean，可以使用Lazy 指定为懒加载，只有在获取bean时才创建，只对singleton有效
    @Lazy
    public IFXNewsListener newsListener() {
        return new DowJonesNewsListener();
    }

    @Bean
    // 条件注册，只有在满足条件时才会注册bean
    @Conditional(LinuxCondition.class)
    public Person linusPerson() {
        return new Person("linus");
    }

    @Bean
    // 条件注册，只有在满足条件:windows系统时才会注册bean
    @Conditional(WindowsCondition.class)
    public Person billPerson() {
        return new Person("bill gates");
    }

    @Bean
    public PersonFactoryBean personFactoryBean() {
        return new PersonFactoryBean();
    }


    // 也可以指定初始化方法 initMeth  od 和销毁方法  destroyMethod
    @Bean(initMethod = "init", destroyMethod = "destroyBean")
    public LifeCycleDTO lifeCycleDTO() {
        return new LifeCycleDTO();
    }



}
