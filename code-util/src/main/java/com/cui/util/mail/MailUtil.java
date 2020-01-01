package com.cui.util.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件工具类：每个具体的配置邮件发送组得实例化对应的对象
 *
 * @author CUI
 * @date 2019-01-21
 */
public class MailUtil {
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private MailConfig mailConfig;

    public MailUtil(MailConfig mailConfig) {
        if (mailConfig == null) {
            mailConfig = new MailConfig();
        }
        this.mailConfig = mailConfig;
    }

    public static void sendMail(List<String> toList, List<String> ccList, String subject, String content) {

    }

    /**
     * 根据固定的配置信息发送邮件
     *
     * @param subject 邮件主题
     * @param content 邮件内容，html格式的文本
     */
    public void sendMailByConfig(String subject, String content) {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", mailConfig.getHost());
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        //session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg;
        try {
            msg = createMimeMessage(session, subject, content);

            Transport transport = session.getTransport();
            transport.connect(mailConfig.getUsername(), mailConfig.getPassword());
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            logger.error("邮件发送失败", e);
        }
    }

    private Message createMimeMessage(Session session, String subject, String content) throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mailConfig.getEmailForm()));
        Address[] toAddresses = new InternetAddress[mailConfig.getToList().length];
        for (int i = 0; i < mailConfig.getToList().length; i++) {
            toAddresses[i] = new InternetAddress(mailConfig.getToList()[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, toAddresses);

        Address[] ccAddresses = new InternetAddress[mailConfig.getCcList().length];
        for (int i = 0; i < mailConfig.getCcList().length; i++) {
            ccAddresses[i] = new InternetAddress(mailConfig.getCcList()[i]);
        }
        msg.setRecipients(Message.RecipientType.CC, ccAddresses);

        //设置邮件主题
        msg.setSubject(subject, "UTF-8");
        //设置邮件正文
        msg.setContent(content, "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }
}
