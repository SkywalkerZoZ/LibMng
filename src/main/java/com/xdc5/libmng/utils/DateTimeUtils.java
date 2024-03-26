package com.xdc5.libmng.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static String formatDate(LocalDate date, String format) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
}
