package com.jojo;

import com.jojo.config.MinioProperties;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@SpringBootTest
@ContextConfiguration(classes = {MinioProperties.class})
public class test {

    @Resource
    private MinioProperties minioProperties;

    @Test
    public void getProperties(){
        System.out.println(minioProperties.getEndpoint());
        System.out.println(minioProperties.getAccessKey());
        System.out.println(minioProperties.getBucket());
        System.out.println(minioProperties.getSecretKey());
    }

}
