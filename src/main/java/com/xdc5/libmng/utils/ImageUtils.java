package com.xdc5.libmng.utils;

import java.io.*;

import java.io.*;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

public class ImageUtils {

    private static final HashMap<String, String> mimeTypeMap;

    static {
        mimeTypeMap = new HashMap<>();
        mimeTypeMap.put("FFD8FF", "image/jpeg");
        mimeTypeMap.put("89504E47", "image/png");
        mimeTypeMap.put("47494638", "image/gif");
        mimeTypeMap.put("49492A00", "image/tiff");
        mimeTypeMap.put("424D", "image/bmp");
        // 更多类型可以按需添加
    }
    public static String detectMimeType(byte[] imageBytes) {
        try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes))) {
            String formatName = ImageIO.getImageReaders(in).next().getFormatName();
            return "image/" + formatName.toLowerCase();
        } catch (Exception e) {
            // 在这里处理异常，返回null
            return null;
        }
    }

    public static String getBase64Prefix(String mimeType) {
        // 如果mimeType为null，意味着无法识别图片类型，可能选择不添加前缀
        if (mimeType == null) {
            return null; // 或者根据需要返回null;
        }
        return "data:" + mimeType + ";base64,";
    }

    public static String blobToBase64(byte[] blob) {
        String mimeType = detectMimeType(blob);
        // 检查是否成功检测到MIME类型
        if (mimeType == null) {
            // 无法检测到MIME类型时的处理逻辑，这里示例直接返回null
            return null;
        }
        String base64Prefix = getBase64Prefix(mimeType);
        return base64Prefix + Base64.getEncoder().encodeToString(blob);
    }
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
