package com.cui.code.spider.dal.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author cuishixiang
 * @date 2019-06-28
 */
@Slf4j
public class HospitalDBConfig {

    private String host = "localhost";
    private Integer port = 3306;
    private String database = "hospital";
    private String username = "root";
    private String password = "root";


    public static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "config/hospital-mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            log.error("sql资源加载异常", e);
            throw new RuntimeException(e);
        }
    }


}
