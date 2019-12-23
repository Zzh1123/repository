package com.aoyuan.getpic.controller;

import com.aoyuan.getpic.util.EncryptUtils;
import com.aoyuan.getpic.util.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class abc {

    private static final String URL = "https://125.70.226.244:8080/login";

    @GetMapping("/login")
    public String login(){

        try{

            String password = "aoyuntest";
            String secretArr = EncryptUtils.encode(password);
            System.out.println(secretArr);
            String param = "loginName=aoyuntest&password=" + secretArr;

            String s = HttpUtils.sendSSLPost(URL, param);

            return s;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
