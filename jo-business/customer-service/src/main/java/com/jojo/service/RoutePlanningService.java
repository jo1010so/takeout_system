package com.jojo.service;

import com.jojo.model.Location;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

@Service
public interface RoutePlanningService {

    //根据地质获取经纬度
    Location getAMapGeocode(String address) throws Exception;

    //骑行的路径规划
    void routePlanByGeocode(String srcLongitude, String srcLatitude, String destLongitude, String destLatitude);

    //根据经纬度获取地址
    Location getAddressFromCoordinates(double longitude, double latitude) throws Exception;

}
