package com.bankle.common.util;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FormatterUtil
 *
 * @author sh.lee
 * @date 2023-09-18
 **/
public class FormatterUtil {

    private static Pattern pTime = Pattern.compile(
            "^\\s*(?<hour>([0-1]?[0-9]|2[0-3]))\\s*([:]\\s*(?<minute>[0-5]?[0-9]))?\\s*([:]\\s*(?<second>[0-5]?[0-9]))?$");

    private static Pattern pCPH = Pattern
            .compile("^((?<head>01[0-9])[-/.]?)?(?<body>[0-9]{3,4})[-/.]?(?<tail>[0-9]{4})$");

    public static String CPH_NO(String strCPH) {
        return CPH_NO(strCPH, "-");
    }

    public static String CPH_NO(String strCPH, String delim) {
        Matcher mCPH = pCPH.matcher(strCPH);
        StringBuilder res = new StringBuilder();
        if (mCPH.find()) {
            String head = mCPH.group("head");
            String body = mCPH.group("body");
            String tail = mCPH.group("tail");
            res.append(head == null ? "010" : head);
            if (delim != null)
                res.append(delim);
            res.append(body);
            if (delim != null)
                res.append(delim);
            res.append(tail);
        } else {
            res.append(strCPH);
        }
        return res.toString();
    }

    public static String Time(String strTime) {
        return Time(strTime, ":");
    }

    public static String Time(String strTime, String delim) {
        Matcher mTime = pTime.matcher(strTime);
        StringBuilder res = new StringBuilder();
        if (mTime.find()) {
            String hour = mTime.group("hour");
            String minute = mTime.group("minute");
            String second = mTime.group("second");
            res.append(StringUtil.lpad(hour, 2, '0'));
            if (delim != null)
                res.append(delim);
            res.append(StringUtil.lpad(minute, 2, '0'));
            if (delim != null)
                res.append(delim);
            res.append(StringUtil.lpad(second, 2, '0'));
        } else {
            res.append(strTime);
        }
        return res.toString();
    }

    public static String PatternTelNo(String target) {
        if (!StringUtils.hasText(target)) {
            return "";
        }
        if (target.length() == 8) {
            return target.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        } else if (target.length() == 12) {
            return target.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        return target.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }

    public static String PatternDateyMd(String date) {
        return date.replace("([0-9]{4})-([0-9]{2})-([0-9]{2})", "$1년 $2월 $3일");
    }
}
