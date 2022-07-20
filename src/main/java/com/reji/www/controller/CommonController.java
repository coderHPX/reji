package com.reji.www.controller;

import com.reji.www.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${file.BasePath}")
    private String BasePath;

    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        String originalFilename = file.getOriginalFilename();

        String lastName = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString()+lastName;


        File d = new File(BasePath);
        if (!d.exists()){
            d.mkdirs();
        }

        try {
            file.transferTo(new File(BasePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(BasePath + name);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[1024];

        while (fileInputStream.read(bytes)!=-1){
            outputStream.write(bytes);
            outputStream.flush();
        }
        fileInputStream.close();
        outputStream.close();


    }


}
