package com.cui.code.test.copy;

import org.springframework.cglib.beans.BeanCopier;

/**
 * cglib BeanCopier 拷贝属性
 *
 * @author cuiswing
 * @date 2019-07-05
 */
public class CglibBeanCopierPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) {
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }
}
