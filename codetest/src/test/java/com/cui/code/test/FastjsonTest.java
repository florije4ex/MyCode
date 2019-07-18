package com.cui.code.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cui.code.test.model.EvilUser;
import com.cui.code.test.model.Product;
import org.junit.Test;

/**
 * fastjson 安全漏洞测试
 * 和版本相关
 * https://kingx.me/Exploit-Java-Deserialization-with-RMI.html
 *
 * @author cuiswing
 * @date 2019-07-16
 */
public class FastjsonTest {

    // 正常的使用反序列化
    @Test
    public void testCommonDeserialization() {
        String json = "{\"code\":\"w14014-p\",\"name\":\"seamin cookie\",\"price\":\"18.99\",\"description\":\"Low calorie, it's delicious.\"}";
        Product product = JSON.parseObject(json, Product.class);

        System.out.println(product);
    }

    // 加上了type后的反序列化：
    // 通过@type可以指定反序列化类。需要autoType 的支持，低版本的fastjson可以，高版本的默认关闭了此参数，需要手动打开。
    // https://www.oracle.com/technetwork/java/seccodeguide-139067.html#8
    @Test
    public void testDeserialization() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String json = "{\"@type\":\"com.cui.code.test.Product\",\"code\":\"w14014-p\",\"name\":\"seamin cookie\",\"price\":\"18.99\",\"description\":\"Low calorie, it's delicious.\"}";
        JSONObject product = JSON.parseObject(json);

        System.out.println(product);
    }

    @Test
    public void testJsonSerializable() {
        EvilUser evilUser = new EvilUser();
        evilUser.setAge(18);
        evilUser.setName("张三");

        String jsonString = JSON.toJSONString(evilUser);
        System.out.println(jsonString);
        String jsonStringType = JSON.toJSONString(evilUser, SerializerFeature.WriteClassName);
        System.out.println(jsonStringType);
    }

    /**
     * fastjson 反序列化时会创建对象，如果构造的是恶意对象，就会造成安全问题
     */
    @Test
    public void testJsonDeserialization() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        String jsonString = "{\"@type\":\"com.cui.code.test.model.EvilUser\",\"age\":18,\"name\":\"张三\"}";
        JSONObject jsonObject = JSON.parseObject(jsonString);
        // EvilUser user = JSON.parseObject(jsonString, EvilUser.class);
        System.out.println(jsonObject);
    }
}

