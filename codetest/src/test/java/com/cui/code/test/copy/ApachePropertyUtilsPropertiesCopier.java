package com.cui.code.test.copy;

/**
 * Apache PropertyUtils 拷贝属性
 *
 * @author cuiswing
 * @date 2019-07-05
 */
public class ApachePropertyUtilsPropertiesCopier implements PropertiesCopier {
    @Override
    public void copyProperties(Object source, Object target) throws Exception {
        org.apache.commons.beanutils.PropertyUtils.copyProperties(target, source);
    }
}
