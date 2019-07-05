package com.cui.code.test.copy;

import com.cui.code.test.model.Customer;
import org.springframework.cglib.beans.BeanCopier;

/**
 * 静态的cglib BeanCopier拷贝属性
 *
 * @author cuiswing
 * @date 2019-07-05
 */
public class StaticCglibBeanCopierPropertiesCopier implements PropertiesCopier {
    // 全局静态 BeanCopier，避免每次都生成新的对象
    private static final BeanCopier BEAN_COPIER = BeanCopier.create(Customer.class, Customer.class, false);

    @Override
    public void copyProperties(Object source, Object target) {
        BEAN_COPIER.copy(source, target, null);
    }
}
