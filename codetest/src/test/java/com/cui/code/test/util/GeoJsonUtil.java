package com.cui.code.test.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * GeoJson工具类
 *
 * @author cuishixiang
 * @date 2018-10-10
 */
public class GeoJsonUtil {

    /**
     * 构造点的GeoJson
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return type为point的GeoJson
     */
    public static String createPointGeoJson(double longitude, double latitude) {
        JSONObject geoJson = new JSONObject();
        geoJson.put("type", "Point");
        JSONArray coordinates = new JSONArray();
        coordinates.add(0, longitude);
        coordinates.add(1, latitude);
        geoJson.put("coordinates", coordinates);
        return geoJson.toString();
    }

    /**
     * 构造polygon的GeoJson
     *
     * @param longitudeArray 经度坐标列表
     * @param latitudeArray  纬度坐标列表
     * @return type为polygon的GeoJson
     */
    public static JSONObject createPolygon(String[] longitudeArray, String[] latitudeArray, TransformEnum transformEnum) {
        JSONObject geoJson = new JSONObject();
        geoJson.put("type", "polygon");
        JSONArray coordinates = new JSONArray();

        JSONArray line = new JSONArray();
        int i = 0;
        for (; i < longitudeArray.length && i < latitudeArray.length; i++) {
            TransformPoint transformPoint = new TransformPoint(transformEnum, Double.parseDouble(longitudeArray[i]), Double.parseDouble(latitudeArray[i])).invoke();

            JSONArray point = new JSONArray();
            point.add(transformPoint.getLng());
            point.add(transformPoint.getLat());
            line.add(point);
        }
        if (!longitudeArray[0].equals(longitudeArray[--i])) {
            TransformPoint transformPoint = new TransformPoint(transformEnum, Double.parseDouble(longitudeArray[0]), Double.parseDouble(latitudeArray[0])).invoke();

            JSONArray point = new JSONArray();
            point.add(transformPoint.getLng());
            point.add(transformPoint.getLat());
            line.add(point);
        }

        coordinates.add(line);
        geoJson.put("coordinates", coordinates);
        return geoJson;
    }

    public static JSONObject createFeatureCollectionObject(List<JSONObject> features) {
        JSONObject featureCollectionObject = new JSONObject();
        featureCollectionObject.put("type", "FeatureCollection");
        JSONArray featuresArray = new JSONArray();
        featuresArray.addAll(features);
        featureCollectionObject.put("features", featuresArray);
        return featureCollectionObject;
    }


    public static JSONObject createFeatureObject(JSONObject geometry) {
        JSONObject featureObject = new JSONObject();
        featureObject.put("type", "Feature");
        featureObject.put("geometry", geometry);
        return featureObject;
    }

    private static class TransformPoint {
        private TransformEnum transformEnum;
        private double lng;
        private double lat;

        public TransformPoint(TransformEnum transformEnum, double lng, double lat) {
            this.transformEnum = transformEnum;
            this.lng = lng;
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }

        public TransformPoint invoke() {
            switch (transformEnum) {
                case WGS84ToGcj02:
                    double[] gcj02Coordinate = CoordinateTransformUtil.wgs84togcj02(lng, lat);
                    lng = gcj02Coordinate[0];
                    lat = gcj02Coordinate[1];
                    break;
                case NO_TRANSFORM:
                    break;
                default:
                    break;
            }
            return this;
        }
    }
}
