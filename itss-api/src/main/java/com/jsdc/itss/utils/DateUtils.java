package com.jsdc.itss.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * ClassName: DateUtils
 * Description: 日期函数
 * date: 2022/3/21 21:07
 *
 * @author bn
 */
public class DateUtils {

    /**
     *  日期转str
     *  yyyy-MM-dd hh:mm:ss
     * @param date
     * @return
     */
    public static String toDateStr(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        return ft.format(date);
    }

    public static String toDateStr(Date date, String patten){
        SimpleDateFormat ft = new SimpleDateFormat (patten);
        return ft.format(date);
    }

    /**
     *  str转日期
     * @param time
     * @return
     */
    public static Date toStrDate(String time) {
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
        try {
            return ft.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date toStrDate(String time, String patten) {
        SimpleDateFormat ft = new SimpleDateFormat (patten);
        try {
            return ft.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理时间字符串
     */
    public static String formatTime(String time){
        if (!StringUtils.isBlank(time)) {
            if (time.contains("T")) {
                DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                LocalDateTime ldt = LocalDateTime.parse(time, df);
                ZoneId currentZone = ZoneId.of("UTC");
                ZoneId newZone = ZoneId.of("Asia/Shanghai");
                time = ldt.atZone(currentZone).withZoneSameInstant(newZone).toLocalDateTime().toString();
            }
            if (time.length() >= 10) {
                return time.substring(0, 10);
            }
        }
        return time;
    }

}
