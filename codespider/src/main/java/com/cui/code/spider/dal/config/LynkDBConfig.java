package com.cui.code.spider.dal.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * 旅游年卡数据库连接配置
 *
 * @author CUI
 * @date 2019-12-27
 */
@Slf4j
public class LynkDBConfig {
    public static final SqlSessionFactory sqlSessionFactory;

    static {
        String resource = "config/lynk-mybatis-config.xml";
        try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            log.error("sql资源加载异常", e);
            throw new RuntimeException(e);
        }
    }
}
