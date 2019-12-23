package com.aoyuan.getpic.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoyuan.getpic.service.JiangMenService;
import com.aoyuan.getpic.util.HttpClientUtil;
import com.aoyuan.getpic.util.HttpUtil;
import com.aoyuan.getpic.util.Md5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class JiangMenServiceImpl implements JiangMenService {

    public final String KEY = "DAHUAH880";

    @Override
    public String getAll() {
        try {

            HttpClientUtil httpClientUtil = new HttpClientUtil(false);
            String s = httpClientUtil.sendGet("http://59.37.162.194:8181/admin/rest/getDeviceInfoOrOrgCode/getOrganization", null);
            return s;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String passengerFlow() {
        try {

            //HttpClientUtil httpClientUtil = new HttpClientUtil(false);
            Map<String , String> param = new HashMap<>();

//            Long second = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()/1000;
            //String countDate = LocalDateTime.now().minusDays(5).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")) + ":00:00:000";
            String countDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH")) + ":00:00:000";
            Long second = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(countDate).getTime()/1000;
            param.put("startDate" , String.valueOf(second - 3600));
            param.put("endDate" , String.valueOf(second));
            //param.put("channelName" , "一层A区昌大昌正门1");
//            param.put("deviceCode" , "1000008");
//            param.put("deviceName" , "一层A区金至福大门");
//            param.put("orgCode" , "001001");
//            param.put("orgName" , "商铺旧相机");
            /*param.put("startDate" , "1576181263");
            param.put("endDate" , "1576191263");*/
            //String s = httpClientUtil.sendPost("http://59.37.162.194:8181/admin/rest/personFlow/admin.person.getPersonFlow", param);

            String data = JSON.toJSONString(param);
            JSONObject jsonObject = JSON.parseObject(data);
            String s = HttpUtil.sendPost(jsonObject, "http://59.37.162.194:8181/admin/rest/personFlow/admin.person.getPersonFlow");
            return s;

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public String getDeviceChnByOrgCode(String orgCode) {

        try{

            String sign = Md5Utils.hash(KEY + orgCode);
            Map<String , String> param = new HashMap<>();

            param.put("orgCode" , orgCode);
            param.put("sign" , sign);

            String data = JSON.toJSONString(param);
            JSONObject jsonObject = JSON.parseObject(data);

            String s = HttpUtil.sendPost(jsonObject, "http://59.37.162.194:8181/admin/rest/getDeviceInfoOrOrgCode/getDeviceChnByOrgCode");

            return s;

        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
