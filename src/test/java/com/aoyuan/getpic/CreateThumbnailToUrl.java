package com.aoyuan.getpic;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateThumbnailToUrl {

    /**
     * @Description: 根据URL创建缩略图
     * @Param: Url原图路径
     * @return: 成功则返回生成的缩略图的路径,失败返回空
     * @Author: liu_yan
     * @Date: 2018/11/16
     */
    public static String createThumbnailToUrl(String fileUrl) {
        // 准备返回缩略图路径
        String thumbnailFilePath = "";
        try {
            // 连接传递进来的Url
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 获取连接返回状态码,200为成功状态
            int responseCode = connection.getResponseCode();
            if (200 == responseCode) {

                System.out.println("--------" + responseCode + "--------");

                // 获取URL的后缀
                String fileType = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);

                // 根据文件类型,生成一张0字节的文件出现先
                thumbnailFilePath = operatingPlatform("", "", fileType.toLowerCase());

                // 判断原图的大小,单位bit
                int contentLength = connection.getContentLength();
                // 起码字节在50KB以上的才进行缩略
                if (contentLength > 51022) {
                    // 如果>1M的缩放0.25,小于1M的缩放0.42
                    if (contentLength > 1045504) {
                        // scale <1是缩小.例如0.5表示将原图的分辨率,宽高像素缩小一半,1f表示不改变这些数据.
                        // outputQuality表示将图片大小缩小,0-1之前的数即为缩小,越接近0越小.
                        Thumbnails.of(url).scale(1f).outputQuality(0.25f).toFile(thumbnailFilePath);
                    } else {
                        Thumbnails.of(url).scale(1f).outputQuality(0.45f).toFile(thumbnailFilePath);
                    }
                }
            }
            return thumbnailFilePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnailFilePath;
    }

    /**
     * @Description: 根据不同情况创建文件夹或者文件(适用于不同的操作平台,注意:Linux上需要先拥有在文件夹下进行操作的权限)方法
     * @Param: path文件夹路径(不传递指定文件夹则默认为项目存储的文件夹中).
     *         fileName文件名(不传递指定文件名,则方法内会自动生成一个文件名).
     *         fileType生成的文件具体格式(如果文件名自带后缀则fileType可为空).
     * @return: 返回String. 文件夹路径/文件路径,创建的是什么则返回什么
     * @Author: liu_yan
     * @Date: 2018/11/15
     */
    public static String operatingPlatform(String folderPath, String fileName, String fileType) {
        try {
            // 获取分隔符,随操作系统不同而不同
            String separator = File.separator;

            // 当前用户程序所在目录(项目存放的地方,这样就得到一个已经存在的路径)
            String userDir = System.getProperties().getProperty("user.dir") + separator;

            // 如果传递了文件夹路径.则以传递的路径为基准创建文件夹
            if (StringUtils.isNotEmpty(folderPath)) {
                // 传递的文件夹路径肯定是自己用了分隔符的,这里需要替换成separator.
                StringBuilder folder = new StringBuilder();

                String tempPath = new File(folderPath).getCanonicalPath();// File对象转换为标准路径并进行切割，有两种windows和linux
                String[] split = tempPath.split("\\\\");// windows
                if (split.length == 1) {// linux
                    split = tempPath.split("/");
                }

                // 切割之后进行路径的重新拼接
                for (int i = 0; i < split.length; i++) {
                    String element = split[i];
                    if (StringUtils.isNotEmpty(element)) {
                        folder.append(element).append(separator);
                    }
                }
                // 最后得到重新拼接好的文件夹路径.
                userDir = folder.toString();
                // 如果文件夹不存在,则创建(注意:如果是Linux这一步可能出错,可能没有文件夹创建权限,注意)
                File file = new File(userDir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }

            System.out.println("************文件夹路径:"+userDir+"************");

            /*
             * 以下预计了几种情况:
             * 1.fileName为空,fileType不为空,则则以当前时间精确到毫秒值+当前时间的纳秒值为文件名创建文件
             * 2.fileName不为空. 则先判断是否自带后缀(如果有后缀,则不管它是否传递了fileType都直接用文件名,
             * 如果没有后缀,则用传递进来的fileType作为后缀,要是fileType都没有.那就不创建文件了.)
             */
            boolean flag = false;
            if (StringUtils.isEmpty(fileName) && StringUtils.isNotEmpty(fileType)) {
                String yyyyMMdd = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
                long nanosecond = System.nanoTime();
                fileName = yyyyMMdd + nanosecond + "." + fileType;
                userDir = userDir + fileName;
                flag = true;
            } else if (StringUtils.isNotEmpty(fileName)) {
                //检查传递进来的fileName是否自带后缀
                int lastIndexOf = fileName.lastIndexOf(".");
                if(lastIndexOf>0){//表示文件名自带后缀
                    userDir = userDir + separator + fileName;
                    flag = true;
                }else {
                    if(StringUtils.isNotEmpty(fileType)){//文件名无后缀,则必须要传递进fileType
                        userDir = userDir + separator + fileName + "." + fileType;
                        flag = true;
                    }
                }
            }

            //if true表示需要创建文件
            if(flag){
                // 如果不存在该文件,则创建
                File file1 = new File(userDir);
                if (!file1.exists()) {
                    file1.createNewFile();
                }
            }
            return userDir;
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

}
