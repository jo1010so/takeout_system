package com.jojo.controller;

import com.jojo.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class AuthController {
    @GetMapping("getRole")
    public Result<?> getRole(@RequestParam("username")String username){
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("routes",new String[]{"tc", "TeacherInfo", "tcc", "MyCourse", "dpv", "DelelopmentPlanView"});
        return Result.success(objectObjectHashMap);
    }
}
