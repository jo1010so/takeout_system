package com.jojo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jojo.config.IdProperties;
import com.jojo.constant.AuthConstatans;
import com.jojo.constant.StatusEnum;
import com.jojo.model.IDResponse;
import com.jojo.model.Result;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("authentication")
public class IDController {

    @Resource
    private IdProperties idProperties;

    /*
    进行实名认证
     */
    @PostMapping("realnameAuthentication")
    @SneakyThrows
    public Result<?> RealNameAuthentication(@RequestBody IDResponse idResponse){
        Boolean auth = true;
        String url = idProperties.getUrl();
        String appCode = idProperties.getAppCode();
        String name = idResponse.getName();
        String idNo = idResponse.getIdNo();
        JSONObject s = JSON.parseObject(postData(appCode, url, name, idNo));
        if (!s.get("respCode").equals("0000")){
            return Result.fail(StatusEnum.REALNAME_FAIL.getCode(), (String) s.get("respMessage"));
        }
        //TODO 送入数据库
        return Result.success("实名成功");
    }

    public static String postData(String appCode, String url, String name, String idNo) throws IOException {
        String result = "";
        okhttp3.RequestBody formBody = new FormBody.Builder().add("name", name).add("idNo", idNo).build();
        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(formBody).build();

        Call call = new OkHttpClient().newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            System.out.println("execute failed, message:" + e.getMessage());
        }
        assert response != null;
        if (!response.isSuccessful()) {      // 当返回结果发生错误时
            System.out.println("request failed----" + "返回状态码" + response.code()  + ",message:" + response.message());
        }
        result = response.body().string();    //此处不可以使用toString()方法，该方法已过期

        return result;
    }
}
