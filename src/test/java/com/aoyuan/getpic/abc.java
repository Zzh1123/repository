package com.aoyuan.getpic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class abc {

    @Test
    public void tset1() throws IOException {

        /*LocalDate date = LocalDate.now();
        LocalDate lastMonthDate = date.minusWeeks(1).minusDays(1);
        LocalDate lastYearDate = date.minusYears(1);
        System.out.println(date);
        System.out.println(lastMonthDate);
        System.out.println(lastYearDate);*/

        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format1 = LocalDate.now().format(formatter);
        String format = LocalDate.now().minusDays(1).format(formatter);
        System.out.println(format1);
        System.out.println(format);*/

        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
        LocalDate today = LocalDate.now();

        String startTime = today.minusDays(1).format(formatter);
        String endTime = today.format(formatter);
        System.out.println(startTime);
        System.out.println(endTime);*/

        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date1 = LocalDate.parse("2019-09-18", formatter);*/

        //时间入参：日期
                //昨天日期
        /*String format = date1.format(formatter);
        //上个星期昨天日期
        String format1 = date1.minusWeeks(1).format(formatter);
        //去年昨天日期
        String format2 = date1.minusYears(1).format(formatter);
        System.out.println(format);
        System.out.println(format1);
        System.out.println(format2);*/

        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate today = LocalDate.now();
        List<String> list = new ArrayList();
        for (int i = 1; i <= 30; i++) {
            LocalDate date = today.minusDays(i);
            list.add(date.format(formatter));
        }
        System.out.println(list);

        for (String s : list) {
            if (s.contains("9月17日")){
                System.out.println(s);
            }
        }*/

        /*String str = "<p>aaa</p>";
        String content = str.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");
        System.out.println(content);
        int length = content.length();
        System.out.println(length);
        char[] chars = content.toCharArray();
        for (char aChar : chars) {
            System.out.println(aChar);
        }*/

        /*String s = Base64ImageUtils.convertImageToBase64("http://qiniu.ggotv.com/99834201911201109025436.png", 100);
        System.out.println(s);*/

        String date = "2019-12-15 00:00:00.0";
        String day = date.substring(0,date.indexOf(" "));
        System.out.println(day);

    }
}
