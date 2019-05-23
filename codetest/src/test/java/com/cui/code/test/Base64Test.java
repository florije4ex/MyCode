package com.cui.code.test;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 编解码测试：Base64、URL
 *
 * @author cuiswing
 * @date 2019-05-17
 */
public class Base64Test {

    @Test
    public void testDecode() {
        String secretString = "cGFzc3dvcmQ=";
        byte[] decode = Base64.getDecoder().decode(secretString);
        System.out.println(new String(decode, StandardCharsets.UTF_8));
    }

    @Test
    public void testEncode() {
        String secret = "password";
        byte[] encode = Base64.getEncoder().encode(secret.getBytes());
        System.out.println(new String(encode));
    }

    // application/x-www-form-urlencoded
    @Test
    public void testURLEncodeDecode() throws UnsupportedEncodingException {
        String encode = URLEncoder.encode("URL字符串", "utf-8");
        System.out.println("URL编码后：" + encode);
        String decode = URLDecoder.decode(encode, "utf-8");
        System.out.println("URL解码后：" + decode);
    }
}
