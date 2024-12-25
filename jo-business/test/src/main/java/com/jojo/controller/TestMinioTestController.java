package com.jojo.controller;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class TestMinioTestController {

    @Resource
    private MinioClient minioClient;

    @SneakyThrows
    @GetMapping("neirong")
    public String neirong(){
        return String.valueOf(minioClient.listBuckets());
    }

}
