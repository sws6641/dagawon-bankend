package com.bankle.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtil {
    /**
     * 날짜 포맷 관련 정리
     * G  | BC(기원전) / AC(기원후)
     * y  | 년도
     * M  | 월(1~12)
     * w  | 년의 몇 번째 주(1~53)
     * W  | 월의 몇 번째 주(1~5)
     * D  | 년의 몇 번째 일(1~366)
     * d  | 월의 몇 번쨰 일(1~31)
     * F  | 월의 몇 번쨰 요일(1~5)
     * E  | 요일
     * a  | 오전(AM) / 오후(PM)
     * H  | 시간(0~23)
     * k  | 시간(1~24)
     * K  | 오전/오후 시간(0~11)
     * h  | 오전/오후 시간(1~12)
     * m  | 분(0~59)
     * s  | 초(0~59)
     * S  | 천분의 일 초(1/1000) - Millisecond(0~999)
     * z  | General 타임존(GMT+9:00)
     * Z  | RFC 822 타임존(+0900)
     * `  | escape문자(특수문자 표현에 사용)
     */

    /**
     * 두 날짜 사이의 차를 구해온다.
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int daysBetween(String startDate, String endDate) {
        int year = Integer.parseInt(startDate.substring(0, 4));
        int month = Integer.parseInt(startDate.substring(4, 6));
        int day = Integer.parseInt(startDate.substring(6));

        int year2 = Integer.parseInt(endDate.substring(0, 4));
        int month2 = Integer.parseInt(endDate.substring(4, 6));
        int day2 = Integer.parseInt(endDate.substring(6));

        LocalDate fromDate = LocalDate.of(year, month, day);
        LocalDate toDate = LocalDate.of(year2, month2, day2);

        return (int) ChronoUnit.DAYS.between(fromDate, toDate);
    }

    /**
     * pattern 형식 String
     *
     * @param  (시간)
     * @param pattern (DateTimeFormatter)
     * @return 해당하는 format이 된 String 문자열
     */
    public static String formatOfPattern(LocalDateTime ldt, String pattern) {
        return ldt.format(DateTimeFormatter.ofPattern(pattern));

    }

    /**
     * 두 일자 사이의 Date 구하기
     *
     * @param :
     * @return :
     * @name : DateUtil.getDateList
     * @author : JuHeon Kim
     **/
    public static List<String> getDateList(String startDate, String lastDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        Integer days = DateUtil.daysBetween(startDate, lastDate);
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < days + 1; i++) {
            DateTimeFormatter newformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(startDate, formatter);
            LocalDate newDate = date.plusDays(i);
            String newDateStr = newDate.format(newformatter);
            dateList.add(newDateStr);
        }
        return dateList;
    }

    /**
     * 해당 하는 포맷으로 당일의 스트링 문자열을 구해온다.
     *
     * @param strFmt
     * @return
     */
    public static String getDate(String strFmt) {

        if ("".equals(strFmt)) strFmt = "yyyy-MM-dd";

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat(strFmt);

        return fmt.format(cal.getTime());
    }

    /**
     * 오늘일자(포맷지원)
     *
     * @param format
     * @return
     */
    public static String getDateNowStr(String format) {
        LocalDate today = LocalDate.now();
        return today.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * String 과 infmt, outfmt 을 받아 날짜를 출력해준다.
     * ex) getFormatDate("20080901","yyyyMMdd","yyyy-MM-dd") -> 2008-09-01
     *
     * @param date
     * @param infmt
     * @param outfmt
     * @return
     */
    public static String getFormatDate(String date, String infmt, String outfmt) {
        try {
            // date 형식이 infmt 에 맞지 않다면 date 를 infmt 에 맞추어줌.
            StringBuffer sDate = new StringBuffer(date);
            int len = date.length();

            if (infmt.equals("yyyyMMddHHmm") && len < 12) {
                for (int i = len; i < 12; i++) {
                    sDate.append("0");
                }
            } else if (infmt.equals("yyyyMMddHHmmss") && len < 14) {
                for (int i = len; i < 14; i++) {
                    sDate.append("0");
                }
            }

            SimpleDateFormat sdf = new SimpleDateFormat(infmt, Locale.US);
            Date d = sdf.parse(sDate.toString());
            sdf.applyPattern(outfmt);

            return sdf.format(d);
        } catch (ParseException e) {
            return "";
        }
    }

    /**
     * 현재일의 마지막 일자
     *
     * @return
     */
    public static String getLastDayStrNow() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * n일 전의 영업일 구하기(당일기준)
     *
     * @param n -> 숫자 n 일 이전 (당일일 경우 0)
     * @return date ->yyyyMMdd
     */
    public static String getStrDateMinusNDays(int n) {
        LocalDate now = LocalDate.now();
        LocalDate nDays = now.plusDays(n);

        int dayOfWeekNum = nDays.getDayOfWeek().getValue();

        return switch (dayOfWeekNum) {
            case 1, 2, 3, 4, 5 -> nDays.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            case 6, 7 ->
                    nDays.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            default -> "";
        };
    }

    /**
     * 오늘을 기준으로 +2 영업일 구하기
     *
     * @return date -> yyyyMMdd
     */
    public static String getStrDatePlus2Days() {
        LocalDateTime date = LocalDateTime.now();

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        int dayOfWeekNumber = dayOfWeek.getValue();
        return switch (dayOfWeekNumber) {
            // 구해온 오늘의 값이 1,2,3 인 경우는 월, 화, 수 의 경우이다.
            case 1, 2, 3 -> {
                LocalDate now = LocalDate.now();
                yield now.plusDays(2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            // 이외에 4,5,6,7 은 목, 금 토 일 이다.
            case 4, 5, 6, 7 -> {
                LocalDate now = LocalDate.now();
                yield now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            }
            default -> LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        };
    }

    /**
     * n일 뒤의 영업일 구하기
     *
     * @param n -> 숫자 n 일 이후 (당일일 경우 0)
     * @return date ->yyyyMMdd
     */
    public static String getStrDatePlusNDays(int n) {
        LocalDate nDays = LocalDate.now().plusDays(n);

        int dayOfWeekNum = nDays.getDayOfWeek().getValue();

        return switch (dayOfWeekNum) {
            case 1, 2, 3, 4, 5 -> nDays.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            case 6, 7 ->
                    nDays.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            default -> "";
        };
    }

    /**
     * 해당하는 패턴으로 당일의 스트링 문자열을 구해온다.
     *
     * @param pattern
     * @return
     */
    public static String getThisDate(String pattern) {

        if (pattern.isEmpty()) pattern = "yyyy-MM-dd";

        SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        return fmt.format(new Date());
    }

    /**
     * 요일 구하기(1 ~ 7 일 수 반환. 일요일이 1, 토요일이 7 임.)
     *
     * @param :
     * @return :
     * @name : DateUtil.getWeekNum
     * @author : JuHeon Kim
     **/
    public static int getWeekNum(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.getDayOfWeek().getValue();
    }

    public static String invalidDateToNull(String date) {
        if (date == null) return null;
        date = date.replaceAll("[^0-9]", "");
        String formatter = "";
        switch (date.length()) {
            case 14:
                formatter = "ss";
                break;
            case 12:
                formatter = "HHmm" + formatter;
                break;
            case 8:
                formatter = "yyyyMMdd" + formatter;
                break;
            default:
                return null;
        }
        SimpleDateFormat sdformat = new SimpleDateFormat(formatter);
        try {
            Date res = sdformat.parse(date);
            return sdformat.format(res);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 기준일시가 원하는 시간 이후인지 확인
     *
     * @param :
     * @return :
     * @name : DateUtil.diffMinutes
     * @name : DateUtil.isAfterAnyHours
     * @author : JuHeon Kim
     **/
    public static boolean isAfterAnyHours(LocalDateTime currDtm, int hours) {
        LocalDateTime currentTime = currDtm;
        LocalDateTime previousTime = currentTime.minusHours(hours);
        return currentTime.isAfter(previousTime);
    }

    /**
     * yyyy년 MM월 dd일 < 포맷 기준으로 들어온 일자를 뿌려주기
     *
     * @param ld
     * @return
     */
    public static String ldToStringByFormat(LocalDate ld) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
        return dateTimeFormatter.format(ld);
    }

    /**
     * yyyy-MM-dd 포맷으로만 리턴을 해준다.
     *
     * @param 문자열 date
     * @return
     */
    public static LocalDate stringToLocalDate(String date) {
        String replaceDate = date.replaceAll("[\\-|\\.|\\:|\\/]", "-");
        return LocalDate.parse(replaceDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * yyyy-MM-dd 포맷으로만 리턴을 해준다.
     *
     * @param 문자열 date
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String date) {
        String replaceDate = date.replaceAll("[\\-|\\.|\\:|\\/]", "-");
        DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(replaceDate, DATEFORMATTER);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }

    /**
     * HH시 mm분 포맷으로만 리턴을 해준다(시간)
     *
     * @param 로컬타임 tm
     * @return
     */
    public static String tmToStringByFormat(LocalTime tm) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH시 mm분");
        return dateTimeFormatter.format(tm);
    }

    /**
     * param날짜와 오늘날짜 차를 구한다.
     *
     * @param yyyyMMdd 형태의 문자열날
     * @return
     */
    public static int getDepDay2(String date){
        String now = getDateNowStr("yyyyMMdd");
        // 현재 날짜와 지급예정일 사이의 일 수 계산
        return DateUtil.daysBetween(now ,date);
    }

    /**
     * D-day 가 양수라면 "-"가 붙은 문자열로 변경
     * D-day 가 음수라면 "+"가 붙은 문자열로 변경
     * @param dDay
     * @return
     */
    public static String calculateDayString(int dDay) {
        return (dDay < 0 ? "+" : "-") + Math.abs(dDay);
    }


}