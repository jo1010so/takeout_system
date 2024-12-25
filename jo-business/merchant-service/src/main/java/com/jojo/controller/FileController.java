package com.jojo.controller;

import com.jojo.config.MinioProperties;
import com.jojo.model.Result;
import io.minio.*;
import io.minio.messages.Bucket;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Objects;

@RestController
@RequestMapping("file")
public class FileController {
    @Resource
    private MinioClient minioClient;
    @Resource
    private MinioProperties minioProperties;

    /*
    上传营业执照
     */
    @ApiOperation("上传营业执照")
    @PostMapping("/upload/contract")
    @SneakyThrows(Exception.class)
    public Result<?> contract(MultipartFile file, @RequestParam("id") Integer merchant_id){
        String subfix = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().indexOf("."));
        String object = merchant_id+"-营业执照"+ subfix;
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucket())
                .object(object)
                .stream(file.getInputStream(),file.getSize(),-1)
                .build());
        System.out.println(objectWriteResponse);
        //存到数据库中
        return Result.success();
    }

    /*
    下载营业执照
     */
    @ApiOperation("下载营业执照")
    @GetMapping("/download/contract/{id}")
    @SneakyThrows
    public void download(@PathVariable(value = "id") Integer merchant_id, HttpServletResponse response){
        String bucket = minioProperties.getBucket();
        String object = merchant_id + ""+"营业执照"+".jpg";
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(object, "UTF-8"));
        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());
        IOUtils.copy(getObjectResponse, response.getOutputStream());
    }


}
