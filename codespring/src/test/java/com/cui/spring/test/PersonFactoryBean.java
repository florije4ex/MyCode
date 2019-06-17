package com.cui.spring.test;

import com.cui.spring.dto.Person;
import org.springframework.beans.factory.FactoryBean;

/**
 * Person factory bean
 *
 * @author cuiswing
 * @date 2019-06-16
 */
public class PersonFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        return new Person("从factoryBean获取的person");
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
