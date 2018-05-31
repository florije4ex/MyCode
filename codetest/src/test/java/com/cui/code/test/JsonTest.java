package com.cui.code.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.junit.Test;

import java.util.Map;

/**
 * JSON解析测试
 *
 * @author cuishixiang
 * @date 2018-03-08
 */
public class JsonTest {

    @Test
    public void testJson() {
        String jsonStr = "{\"coordinates\":[[[116.458117,39.941468],[116.458975,39.938244],[116.466528,39.943377],[116.458117,39.941468]]],\"type\":\"Polygon\"}";
        Map<String, JSONArray> jsonMap = (Map<String, JSONArray>) JSONObject.parse(jsonStr);

        System.out.println(jsonMap);
        System.out.println(jsonMap.get("type"));
        //System.out.println(jsonMap.get("type").getClass());

        System.out.println(jsonMap.get("coordinates"));
        System.out.println(jsonMap.get("coordinates").getClass());
        System.out.println(jsonMap.get("coordinates").size());
        System.out.println(jsonMap.get("coordinates").getJSONArray(0));
        System.out.println(jsonMap.get("coordinates").getJSONArray(0).size());
        System.out.println(jsonMap.get("coordinates").getJSONArray(0).getJSONArray(0));
        System.out.println(jsonMap.get("coordinates").getJSONArray(0).getJSONArray(0).get(0));


        System.out.println("----------");

        JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("coordinates"));
        System.out.println(jsonObject.get("type"));

        System.out.println(jsonObject.get("coordinates").getClass());

    }


    @Test
    public void testJsonParse() {
        Map<String, String> stringStringMap = JSON.parseObject(null, new TypeReference<Map<String, String>>() {
        });

        Map<String, String> stringStringMap3 = JSON.parseObject("{}", new TypeReference<Map<String, String>>() {
        });
        String mapString = "{\"name\":\"张三\"}";
        Map<String, String> stringStringMap4 = JSON.parseObject(mapString, new TypeReference<Map<String, String>>() {
        });

        System.out.println(stringStringMap);
        System.out.println(stringStringMap3);
        System.out.println(stringStringMap4);

        Map<String, String> stringStringMap2 = JSON.parseObject("", new TypeReference<Map<String, String>>() {
        });
        System.out.println(stringStringMap2);
    }
}
