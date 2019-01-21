package com.cui.code.net.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 邮件配置信息
 *
 * @author cuishixiang
 * @date 2019-01-21
 */
public class MailConfig {
    private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);

    private static final String PROPERTIES_FILE = "/config/email.properties";
    public static String host;
    public static String username;
    public static String password;
    public static String emailForm;
    public static String[] toList;
    public static String[] ccList;
    public static Properties properties = new Properties();

    static {
        init();
    }

    private static void init() {
        try (InputStream inputStream = MailConfig.class.getResourceAsStream(PROPERTIES_FILE)) {
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
        }
    }
}
