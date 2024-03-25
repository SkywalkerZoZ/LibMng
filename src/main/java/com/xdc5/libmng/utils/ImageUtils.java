package com.xdc5.libmng.utils;

import java.io.*;

import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Base64;
import javax.imageio.ImageIO;

public class ImageUtils {

    // 将二进制数据保存为图片
    public static void saveImage(byte[] imageData, String filePath, String formatName) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage image = ImageIO.read(bais);
        File output = new File(filePath);
        ImageIO.write(image, formatName, output);
    }

    // 从文件读取图片并返回二进制数据
    public static byte[] readImageAsBytes(String filePath) throws IOException {
        File inputFile = new File(filePath);
        BufferedImage image = ImageIO.read(inputFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String formatName = filePath.substring(filePath.lastIndexOf('.') + 1);
        ImageIO.write(image, formatName, baos);
        return baos.toByteArray();
    }

    // 示例用法
    public static void main(String[] args) {
        try {
            // 读取图片并返回二进制数据
            String inputFilePath = "C:/Users/SkywalkerzzZ/Pictures/Saved Pictures/edward.jpg";
            byte[] imageBytes = readImageAsBytes(inputFilePath);

            // 将二进制数据转换为Base64编码的字符串
            String base64String = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println(base64String);


            // 将二进制数据保存为图片
            String filePath = "C:/Users/SkywalkerzzZ/Pictures/Saved Pictures/test.jpg";
            String formatName = "jpg";
            saveImage(imageBytes, filePath, formatName);


            // 使用 imageBytes 做进一步处理
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
