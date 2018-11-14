package com.cui.code.bio.chat;

import java.nio.charset.Charset;

/**
 * 协议常量定义
 *
 * @author cuishixiang
 * @date 2018-11-08
 */
public class ChatProtocolConstant {

    /**
     * 聊天室服务监听端口
     */
    public static final int PORT = 20000;
    /**
     * 协议长度
     */
    public static final int PROTOCOL_LENGTH = 2;

    // 协议分隔符，服务端和客户端通讯的消息都应该在前后加上这些特殊字符串

    /**
     * 用户登陆
     */
    public static final String USER_LOGIN = "åß";
    /**
     * 私聊协议标记符，传输使用
     */
    public static final String PRIVATE_ROUND = "∂ƒ";
    /**
     * 群聊协议标记符，传输使用
     */
    public static final String PUBLIC_ROUND = "Ω≈";
    /**
     * 私聊用户和内容分隔符，传输使用
     */
    public static final String PRIVATE_SPLIT = "∆˚";
    /**
     * 私聊开始标记符：输入使用
     */
    public static final String PRIVATE_TO_START = "@";
    /**
     * 私聊用户与内容分隔符：输入使用
     */
    public static final String PRIVATE_USER_SPLIT = "::";
    /**
     * 登陆成功
     */
    public static final String LOGIN_SUCCESS = "01";
    /**
     * 用户名重复
     */
    public static final String USER_NAME_REPEAT = "-1";

    /**
     * 默认编解码器
     */
    public static final Charset charset = Charset.forName("UTF-8");

    /**
     * 将获取的消息去除前后协议字符串获取真实消息
     *
     * @param line
     * @return 真实消息
     */
    public static String getRealMsg(String line) {
        return line.substring(PROTOCOL_LENGTH, line.length() - PROTOCOL_LENGTH);
    }
}
