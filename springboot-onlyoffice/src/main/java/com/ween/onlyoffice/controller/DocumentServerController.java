package com.ween.onlyoffice.controller;

import com.alibaba.fastjson.JSONObject;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@RestController
@RequestMapping("/document/server")
public class DocumentServerController {

    @Autowired
    private MinioClient minioClient;

    @PostMapping("/callback")
    public Map<String, Object> callback(@RequestBody Map<String, Object> request) {
        int status = (int) request.get("status");
        if (status == 2 || status == 6) {
            String downloadUri = (String) request.get("url");
            URL url = null;
            try {
                url = new URL(downloadUri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
//                MinioClient client = new MinioClient("http://192.168.2.51:9000", "minioadmin", "minioadmin");
//                minioClient.putObject("test", "myDoc1.docx", inputStream, (long) inputStream.available(), "application/msword");
                minioClient.putObject(PutObjectArgs.builder().bucket("test").object("推荐书单1.docx")
                        .stream(inputStream, inputStream.available(), -1).contentType("application/msword").build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return JSONObject.parseObject("{\"error\":0}");
    }
}
