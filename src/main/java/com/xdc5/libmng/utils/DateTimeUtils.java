package com.xdc5.libmng.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static String dateToStr(LocalDate date, String format) {
        if (date == null || format == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return date.format(formatter);
        } catch (Exception e) {
            // 捕获任何异常（例如，无效的格式字符串），然后返回null
            return null;
        }
    }

    public static LocalDate strToDate(String dateStr, String format) {
        if (dateStr == null || format == null) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            // 捕获解析异常（例如，字符串不符合格式），然后返回null
            return null;
        }
    }



}
