package com.cui.code.test;

import com.alibaba.fastjson.JSON;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.junit.Test;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * google map test case
 *
 * @author cuishixiang
 * @date 2018-03-15
 */
public class GoogleMapTest {

    /**
     * google jar interface
     * 谷歌的查询接口：根据经纬度查询 or 根据地名查询
     */
    @Test
    public void testGeoCode() {
        GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyBGqOlaw1qbLGmddt6pMKTPzNcQk4EgNBQ").build();
        GeocodingApiRequest request;
        if (StringUtils.isEmpty("")) {
            request = GeocodingApi.newRequest(context).latlng(new LatLng(1.368570, 103.817806));
        } else {
            request = GeocodingApi.newRequest(context).address("beijing");
        }
        try {
            GeocodingResult[] results = request.await();
            System.out.println(JSON.toJSONString(results));
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * 直接通过URL转发来调用-香港地址避免翻墙
     */
    @Test
    public void testGeoCodeUrl() {
        RestTemplate template = new RestTemplate();
        Object googleResult = template.getForObject("http://maps-googleapis.mobike.io/maps/api/geocode/json?address={0}&key={1}", Object.class, "beijing", "AIzaSyBGqOlaw1qbLGmddt6pMKTPzNcQk4EgNBQ");
//        Object googleResult = template.getForObject("https://maps.googleapis.com/maps/api/geocode/json?address={0}&key={1}", Object.class, "beijing", "AIzaSyBGqOlaw1qbLGmddt6pMKTPzNcQk4EgNBQ");
        System.out.println(googleResult);

    }
}
