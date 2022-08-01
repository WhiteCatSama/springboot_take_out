package com.whitecatsama.delicious_take_out.controller;

import com.whitecatsama.delicious_take_out.common.Code;
import com.whitecatsama.delicious_take_out.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${delicious.picture.path}")
    private String BasePath;
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        File dir = new File(BasePath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        String fileName = UUID.randomUUID().toString();
        fileName = fileName + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        log.info(file.toString());
        try{file.transferTo(new File(BasePath+fileName));}
            catch(IOException ex){
                ex.printStackTrace();
                return new Result(Code.UPLOAD_FAILED,"上传失败");
            }
        return new Result(Code.UPLOAD_SUCCESS,fileName,"上传成功");
    }

    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response){
        try {
            FileInputStream fileInputStream = new FileInputStream(BasePath+name);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int lenth = 0;
            byte[] bytes = new byte[2048];
            while((lenth=fileInputStream.read(bytes))>0){
                servletOutputStream.write(bytes,0,lenth);
                servletOutputStream.flush();
            }
            fileInputStream.close();
            servletOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
