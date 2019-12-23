package com.aoyuan.getpic.controller;

import com.aoyuan.getpic.service.JiangMenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jiangmen")
public class JiangMenController {

    @Autowired
    private JiangMenService jiangMenService;

    /**
     * 获取所有门店接口
     */
    @GetMapping("/getOrganization")
    public String getAll(){
        try{

            return jiangMenService.getAll();

        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 根据门店组织码获取通道接口
     */
    @PostMapping("/getDeviceChnByOrgCode")
    public String getDeviceChnByOrgCode(String orgCode){
        try{

            return jiangMenService.getDeviceChnByOrgCode(orgCode);

        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }


    /**
     * 客流数据
     */
    @PostMapping("/passengerFlow")
    public String passengerFlow(){
        try{

            return jiangMenService.passengerFlow();

        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}
