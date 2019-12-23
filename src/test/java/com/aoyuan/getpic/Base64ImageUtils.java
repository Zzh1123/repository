package com.aoyuan.getpic;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class Base64ImageUtils {
    /**
     * 将图片转换为base64格式
     *
     * @param imageUrl：图片路径
     * @param sizeLimit：原图大小上限，当图片原图大小超过该值时先将图片大小 设置为该值以下再转换成base64格式,单位kb
     * @return
     */
    public static String convertImageToBase64(String imageUrl, Integer sizeLimit) throws IOException {
        //默认上限为500k
        if (sizeLimit == null) {
            sizeLimit = 500;
        }
        sizeLimit = sizeLimit * 1024;
        String base64Image;
        DataInputStream dataInputStream = null;
        ByteArrayOutputStream outputStream = null;
        ByteArrayInputStream inputStream = null;
        try {
            //从远程读取图片
            URL url = new URL(imageUrl);
            dataInputStream = new DataInputStream(url.openStream());
            outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            byte[] context = outputStream.toByteArray();

            //将图片数据还原为图片
            inputStream = new ByteArrayInputStream(context);
            BufferedImage image = ImageIO.read(inputStream);
            int imageSize = context.length;
            int type = image.getType();
            int height = image.getHeight();
            int width = image.getWidth();

            BufferedImage tempImage;
            //判断文件大小是否大于size,循环压缩，直到大小小于给定的值
            while (imageSize > sizeLimit) {
                //将图片长宽压缩到原来的90%
                height = new Double(height * 0.9).intValue();
                width = new Double(width * 0.9).intValue();
                tempImage = new BufferedImage(width, height, type);
                // 绘制缩小后的图
                tempImage.getGraphics().drawImage(image, 0, 0, width, height, null);
                //重新计算图片大小
                outputStream.reset();
                ImageIO.write(tempImage, "JPEG", outputStream);
                imageSize = outputStream.toByteArray().length;

                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
                ImageIO.write(tempImage, "png", imOut);
                InputStream inputS = new ByteArrayInputStream(bs.toByteArray());

                OutputStream outStream = new FileOutputStream("D:\\project\\getpic\\20191120112233009181812631644700.png");
                IOUtils.copy(inputS, outStream);
            }


            //将图片转化为base64并返回
            byte[] data = outputStream.toByteArray();
            //此处一定要使用org.apache.tomcat.util.codec.binary.Base64，防止再linux上出现换行等特殊符号
            base64Image = Base64.encodeBase64String(data);
        } catch (Exception e) {
            //抛出异常
            throw e;
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(base64Image);
        return base64Image;
    }

}
