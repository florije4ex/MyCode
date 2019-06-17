package com.cui.spring.test.config;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 过滤规则：bean扫描需要的TypeFilter实现类
 *
 * @author cuiswing
 * @date 2019-06-16
 */
public class TestTypeFilter implements TypeFilter {
    /**
     * @param metadataReader        当前类的信息读取器
     * @param metadataReaderFactory 可以获取到其他class信息的读取器
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        // 获取当前类的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        // 获取当前类的class信息：
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类的资源信息
        Resource resource = metadataReader.getResource();

        System.out.println("需要判断的ClassName:" + classMetadata.getClassName());

        if (classMetadata.getClassName().contains("dao")) {
            return true;
        }
        return false;
    }
}
