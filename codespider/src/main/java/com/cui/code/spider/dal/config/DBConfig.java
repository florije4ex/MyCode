package com.cui.code.spider.dal.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author cuishixiang
 * @date 2018-12-24
 */
public class DBConfig {
    private static final Logger log = LoggerFactory.getLogger(DBConfig.class);


    private String host = "localhost";
    private Integer port = 3306;
    private String database = "douban";
    private String username = "root";
    private String password = "root";


    public static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "config/mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            log.error("sql资源加载异常", e);
            throw new RuntimeException(e);
        }
    }


}
