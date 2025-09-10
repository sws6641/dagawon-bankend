package com.bankle.common.asis.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateCustUtils extends DateUtils {

    public static String getThisDate(String pattern) {

        if(pattern.isEmpty())
            pattern = "yyyy-MM-dd";

        SimpleDateFormat fmt  = new SimpleDateFormat(pattern);
        return fmt.format(new Date());
    }

    /**
     * 특정 날짜를 특정 포맷 String 으로 변경
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateWithPattern(String date, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));

        return localDateTime.format(formatter);
    }

    public static String getSystemTime(String pattern) {

        if(pattern.isEmpty())
            pattern = "HH:mm:ss";

        SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        return fmt.format(System.currentTimeMillis());
    }

    public static Date StringToDate(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(date);
    }

    public static String getDayOfWeek(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar instance = Calendar.getInstance();
        instance.setTime(format.parse(date));

        return String.valueOf(instance.get(Calendar.DAY_OF_WEEK));
    }

    public static String getDateToString(LocalDateTime date, String patter){
        return date.format(DateTimeFormatter.ofPattern(patter));
    }

    public static String howLongDelayed(String romPlnDt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate theDay = LocalDate.parse(romPlnDt, formatter);
        String dayOfWeek = String.valueOf(theDay.getDayOfWeek().getValue());

        if(is3DaysAfter(theDay, dayOfWeek)){
            return "3";
        }

        if(is1DayAfter(theDay, dayOfWeek)){
            return "1";
        }

        return "";
    }

    public static boolean is1DayAfter(LocalDate theDay, String dayOfWeek) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //월,화,수,목요일인 경우,
        if(StringCustUtils.equalsAny(dayOfWeek,"2","3","4","5")){
            String afterDay = theDay.plusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return StringCustUtils.equals(today, afterDay);
            //금요일인 경우,
        }else if(StringCustUtils.equalsAny(dayOfWeek,"6")){
            String afterDay = theDay.plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return StringCustUtils.equals(today, afterDay);
        }
        return false;
    }

    public static boolean is3DaysAfter(LocalDate theDay, String dayOfWeek){
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //월,화요일인 경우,
        if(StringCustUtils.equalsAny(dayOfWeek,"2","3")){
            String afterDay = theDay.plusDays(3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return StringCustUtils.equals(today, afterDay);
            //수,목,금
        }else if(StringCustUtils.equalsAny(dayOfWeek,"4","5","6")){
            String afterDay = theDay.plusDays(5).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return StringCustUtils.equals(today, afterDay);
        }
        return false;
    }
}
