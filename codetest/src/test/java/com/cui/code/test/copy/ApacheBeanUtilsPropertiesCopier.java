package com.cui.code.test.copy;


/**
 * Apache BeanUtils 拷贝属性
 *
 * @author cuiswing
 * @date 2019-07-05
 */
public class ApacheBeanUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }
}
