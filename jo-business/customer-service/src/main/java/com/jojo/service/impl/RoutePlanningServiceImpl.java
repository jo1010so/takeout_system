package com.jojo.service.impl;

import com.jojo.config.AmapProperties;
import com.jojo.model.Location;
import com.jojo.service.RoutePlanningService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.annotation.Resource;

public class RoutePlanningServiceImpl implements RoutePlanningService {

    @Resource
    private AmapProperties amapProperties;

    public Location getAMapGeocode(String address) throws Exception {
        OkHttpClient client = new OkHttpClient();
        // 构建请求URL
        String url = "http://restapi.amap.com/v3/geocode/geo" + "?address=" + address + "&key=" + amapProperties.getKey();
        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        // 发起请求并获取响应
        Response response = client.newCall(request).execute();
        // 返回响应内容（JSON字符串）
        String jsonResponse = response.body().string();
        org.json.JSONObject responseObject = new org.json.JSONObject(jsonResponse);
        // 判断是否成功
        if ("1".equals(responseObject.getString("status"))) {
            // 获取地理编码的结果数组
            org.json.JSONArray geocodes = responseObject.getJSONArray("geocodes");
            // 获取第一个地理编码结果（假设第一个是最准确的）
            org.json.JSONObject geocode = geocodes.getJSONObject(0);
            // 获取格式化地址
            String formattedAddress = geocode.getString("formatted_address");
            // 获取经纬度（返回的是字符串 "经度,纬度"）
            String location = geocode.getString("location");
            // 分割经纬度字符串
            String[] locationParts = location.split(",");
            String lng = locationParts[0];  // 经度
            String lat = locationParts[1];  // 纬度
            return new Location(formattedAddress, lng, lat);
        } else {
            // 请求失败时的处理
            System.out.println("地址解析失败，错误信息：" + responseObject.getString("info"));
            return null;
        }
    }

    public void routePlanByGeocode(String srcLongitude, String srcLatitude, String destLongitude, String destLatitude){
        String origin = srcLongitude+","+srcLatitude;
        String destination = destLongitude+","+destLatitude;
        String key = amapProperties.getKey();
        String url = "https://restapi.amap.com/v5/direction/bicycling?origin=" + origin
                + "&destination=" + destination
                + "&key=" + key+"&show_fields=duration";

        OkHttpClient client = new OkHttpClient();
        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            // 发起请求并获取响应
            Response response = client.newCall(request).execute();
            // 打印响应内容
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response: " + responseBody);
                org.json.JSONObject jsonResponse = new org.json.JSONObject(responseBody);
                if (jsonResponse.getString("status").equals("1")) {
                    org.json.JSONArray paths = jsonResponse.getJSONObject("route").getJSONArray("paths");
                    if (paths.length()>0){
                        int duration = paths.getJSONObject(0).getInt("duration");
                        int distance = paths.getJSONObject(0).getInt("distance");
                        System.out.println("骑行时间: " + duration + "秒");
                        System.out.println("总路程: "+distance+"米");
                    }
                }
            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location getAddressFromCoordinates(double longitude, double latitude) throws Exception {
        OkHttpClient client = new OkHttpClient();
        // 构建请求URL
        String url = "http://restapi.amap.com/v3/geocode/regeo" + "?location=" + longitude + "," + latitude + "&key=" +  amapProperties.getKey();
        // 创建请求
        Request request = new Request.Builder()
                .url(url)
                .build();
        // 发起请求并获取响应
        Response response = client.newCall(request).execute();
        // 返回响应内容（JSON字符串）
        String jsonResponse = response.body().string();
        org.json.JSONObject responseObject = new org.json.JSONObject(jsonResponse);
        // 判断是否成功
        if ("1".equals(responseObject.getString("status"))) {
            // 获取返回的逆地理编码结果
            org.json.JSONObject regeocode = responseObject.getJSONObject("regeocode");
            // 获取详细地址
            String formattedAddress = regeocode.getString("formatted_address");
            return new Location(formattedAddress,longitude+"",latitude+"");
        } else {
            // 请求失败时的处理
            System.out.println("地址解析失败，错误信息：" + responseObject.getString("info"));
        }
        return null;
    }


}
