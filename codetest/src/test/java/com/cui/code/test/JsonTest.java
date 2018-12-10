package com.cui.code.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cui.code.test.util.GeoJsonUtil;
import com.cui.code.test.util.TransformEnum;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON解析测试
 *
 * @author cuishixiang
 * @date 2018-03-08
 */
public class JsonTest {
    private static final Logger logger = LoggerFactory.getLogger(JsonTest.class);

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
        Map<String, String> stringStringMap = JSON.parseObject(null, Map.class);
        Map<String, String> stringStringMap2 = JSONObject.parseObject(null, HashMap.class);

        Map<String, String> stringStringMap3 = JSON.parseObject("{}", Map.class);
        String mapString = "{\"name\":\"张三\"}";
        Map<String, String> stringStringMap4 = JSON.parseObject(mapString, Map.class);
        Map<String, String> map3 = JSONObject.parseObject(mapString, Map.class);

        System.out.println(stringStringMap);
        System.out.println(stringStringMap2);
        System.out.println(stringStringMap3);
        System.out.println(stringStringMap4);
        System.out.println(map3);

        Map map4 = JSONObject.parseObject("", Map.class);

        Map<String, String> stringStringMap5 = JSON.parseObject("", new TypeReference<Map<String, String>>() {
        });
        System.out.println(map4);
        System.out.println(stringStringMap5);
    }


    @Test
    public void testString() {
        String jsonString = "{\"xxx\":1}";
        Map<String, String> map = (Map<String, String>) JSON.parse(jsonString);
        System.out.println(map.get("xxx"));
    }

    @Test
    public void testJSONArray() {
        JSONArray coordinates = new JSONArray();
        coordinates.add(0, 116.123);
        coordinates.add(1, 39.098);
        System.out.println(coordinates);
    }

    /**
     * 停放区数据解析成GeoJson
     */
    @Test
    public void testParseParking() {
        String userDir = System.getProperty("user.dir");
        try (RandomAccessFile parkFile = new RandomAccessFile(userDir + "/src/test/resources/停放区.txt", "r");
             RandomAccessFile fenceGeoJsonFile = new RandomAccessFile(userDir + "/src/test/resources/parking.json", "rw")) {

            text2Polygon(parkFile, fenceGeoJsonFile, "PARKING_LNG", "PARKING_LAT", TransformEnum.WGS84ToGcj02);
            //text2FeatureCollection(parkFile, fenceGeoJsonFile, "PARKING_LNG", "PARKING_LAT", TransformEnum.WGS84ToGcj02);
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
    }

    /**
     * 禁停区数据解析成GeoJson
     */
    @Test
    public void testParseNoParking() {
        String userDir = System.getProperty("user.dir");
        try (RandomAccessFile parkFile = new RandomAccessFile(userDir + "/src/test/resources/禁停区.txt", "r");
             RandomAccessFile fenceGeoJsonFile = new RandomAccessFile(userDir + "/src/test/resources/noParking.json", "rw");) {

            text2Polygon(parkFile, fenceGeoJsonFile, "NOPARKING_LNG", "NOPARKING_LAT", TransformEnum.WGS84ToGcj02);
            //text2FeatureCollection(parkFile, fenceGeoJsonFile, "NOPARKING_LNG", "NOPARKING_LAT", TransformEnum.WGS84ToGcj02);
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
    }

    private void text2Polygon(RandomAccessFile readFile, RandomAccessFile writeToFile, String lngField, String latField, TransformEnum transformEnum) throws IOException {
        String line;
        while ((line = readFile.readLine()) != null) {
            JSONObject jsonObject = JSON.parseObject(line);
            String parking_lng = jsonObject.getString(lngField);
            String parking_lat = jsonObject.getString(latField);
            String[] lngs = parking_lng.split("\\|");
            String[] lats = parking_lat.split("\\|");
            JSONObject polygon = GeoJsonUtil.createPolygon(lngs, lats, transformEnum);

            writeToFile.write(polygon.toJSONString().getBytes(StandardCharsets.UTF_8));
            writeToFile.write("\n".getBytes(StandardCharsets.UTF_8));
        }
    }

    private void text2FeatureCollection(RandomAccessFile readFile, RandomAccessFile writeToFile, String lngField, String latField, TransformEnum transformEnum) throws IOException {
        String line;
        List<JSONObject> features = new ArrayList<>();
        while ((line = readFile.readLine()) != null) {
            JSONObject jsonObject = JSON.parseObject(line);
            String parking_lng = jsonObject.getString(lngField);
            String parking_lat = jsonObject.getString(latField);
            String[] lngs = parking_lng.split("\\|");
            String[] lats = parking_lat.split("\\|");
            JSONObject polygon = GeoJsonUtil.createPolygon(lngs, lats, transformEnum);
            JSONObject featureObject = GeoJsonUtil.createFeatureObject(polygon);
            features.add(featureObject);
        }
        System.out.println("features：" + features.size());
        JSONObject featureCollectionObject = GeoJsonUtil.createFeatureCollectionObject(features);
        writeToFile.write(featureCollectionObject.toJSONString().getBytes(StandardCharsets.UTF_8));
    }
}
