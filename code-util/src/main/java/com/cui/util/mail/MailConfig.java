package com.cui.util.mail;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 邮件配置信息：同一个项目中可能需要多个发送配置文件，改为非静态的。
 * 使用时，每个具体的发送配置信息都实例化一个对应的 MailConfig 对象，并实例化相对应的 MailUtil 工具类
 *
 * @author CUI
 * @date 2019-01-21
 */
@Data
public class MailConfig {
    private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);

    private static final String DEFAULT_PROPERTIES_FILE = "/config/email.properties";
    private String host;
    private String username;
    private String password;
    private String emailForm;
    private String[] toList;
    private String[] ccList;
    private Properties properties = new Properties();

    public MailConfig() {
        init(DEFAULT_PROPERTIES_FILE);
    }

    public MailConfig(String configFile) {
        if (configFile == null || configFile.isEmpty()) {
            configFile = DEFAULT_PROPERTIES_FILE;
        }
        init(configFile);
    }

    private void init(String configFile) {
        try (InputStream inputStream = MailConfig.class.getResourceAsStream(configFile)) {
            properties.load(inputStream);
            host = properties.getProperty("mail_host");
            username = properties.getProperty("mail_username");
            password = properties.getProperty("mail_password");
            emailForm = properties.getProperty("mail_from");
            String mailToList = properties.getProperty("mail_to_list");
            toList = mailToList.split(";");
            String mailCcList = properties.getProperty("mail_cc_list");
            ccList = mailCcList.split(";");
        } catch (IOException e) {
            logger.error("邮件配置信息读取失败", e);
            throw new RuntimeException("邮件配置信息读取失败", e);
        }
    }
}
