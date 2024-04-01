package com.xdc5.libmng.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static String dateToStr(LocalDate date, String format) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
    public static LocalDate strToDate(String dateStr,String format)
    {
        if(dateStr==null)
        {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(dateStr, formatter);
    }

}
