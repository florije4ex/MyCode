package com.cui.spring.test.config;

import com.cui.spring.dto.Person;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * profile test
 *
 * @author cuiswing
 * @date 2019-06-16
 */
@Configuration
public class DataSourceConfig {

    // 通过在运行时指定激活环境参数: -Dspring.profiles.active=dev ， 默认是default
    // 当用于class上时，只有profile满足，整个class中的bean才会生效
    @Profile({"dev", "default"})
    @Bean
    public DataSource dataSource01() {
        return new DataSource("dev环境数据源");
    }

    @Profile("test")
    @Bean
    public DataSource dataSource02() {
        return new DataSource("test环境数据源");
    }
}
