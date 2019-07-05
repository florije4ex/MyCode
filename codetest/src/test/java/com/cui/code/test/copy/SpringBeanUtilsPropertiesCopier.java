package com.cui.code.test.copy;

import org.springframework.beans.BeanUtils;

/**
 * Spring BeanUtils拷贝属性
 *
 * @author cuiswing
 * @date 2019-07-05
 */
public class SpringBeanUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}
