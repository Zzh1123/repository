package com.aoyuan.getpic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

@RestController
public class pic {

    /* 获取水印图片*/
    @RequestMapping("/getPic")
    public void getVerificationCode(HttpServletResponse response) {

        try {

            int width = 500;
            int height = 800;

            Color color = new Color(230,230,230 , 150);
            Font font=new Font("黑体", Font.BOLD, 18);

            // 创建BufferedImage对象
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // 获取Graphics2D
            Graphics2D g2d = verifyImg.createGraphics();
            // 画图
            g2d.setBackground(new Color(0,0,0,0));

            g2d.setPaint(new Color(0,0,0));
            g2d.clearRect(0, 0, width, height);

            int x = 10;  //旋转原点的 x 坐标
            String ch = "广东奥智云科技有限公司：员工名";
            g2d.setColor(color);
            g2d.setFont(font);

            //设置字体旋转角度
            int degree = -45;  //角度小于45度

            //正向旋转
            g2d.rotate(degree * Math.PI / 180, x, 45);

            for (int i = -20; i < 20; i++) {
                for (int j = -20; j < 20; j++) {
                    g2d.drawString(ch ,0+i*ch.length()*25 ,0+j*100);
                }
            }

            //设置响应内容类型为图片
            response.setContentType("image/png");

            //获取文件输出流
            OutputStream os = response.getOutputStream();

            //输出图片流
            ImageIO.write(verifyImg, "png", os);
            ImageIO.write(verifyImg, "png", new File("D:/test/abc.png"));

            os.flush();
            os.close();//关闭流

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
