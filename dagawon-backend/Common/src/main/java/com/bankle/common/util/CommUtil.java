package com.bankle.common.util;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CommUtil.class
 *
 * @author sh.lee
 * @date 2024-04-26
 */
public class CommUtil {


    /**
     * 강제 에러 발생
     *
     * @return
     */
    public static int calcDivide(int i, int j) {
        return i / j;
    }

    public static int err() {
        int i = 10;
        int j = 0;
        int k = calcDivide(i, j);
        return k;
    }

    /**
     * 금액 콤마 작성
     * ex) 1,000,000
     *
     * @param value
     * @return
     */
    public static String getAmountComma(String value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(value));
    }

    /**
     * 코드 생성(원하는 자릿수 만큼 생성)
     *
     * @param digits
     * @return
     * @author sh.lee
     */
    public static String getRandomCode(int digits) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(digits)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * 대문자로 코드 생성(원하는 자릿수 만큼 생성)
     *
     * @param digits
     * @return
     * @author sh.lee
     */
    public static String getRandomCodeUpperCase(int digits) {
        int leftLimit = 48;
        int rightLimit = 90;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && i <= 90)
                .limit(digits)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * 난수 생성
     *
     * @param digits
     * @return
     * @author sh.lee
     */
    public static int getRandomNumber(int digits) {
        Random random = new Random(System.currentTimeMillis());
        int range = (int) Math.pow(10, digits);
        int trim = (int) Math.pow(10, digits - 1);
        int rslt = random.nextInt(range) + trim;
        if (rslt > range) {
            rslt = rslt - trim;
        }
        return rslt;
    }

    /**
     * isEmpty > null check 하지 않음
     *
     * @param value
     * @return
     */
    public static boolean isEmpty(String value) {
        return "".equals(value);
    }

    /**
     * isNotEmpty > null check 하지 않음
     *
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    /**
     * 타겟과 소스가 같은지 비교
     * 같을 경우 source 리턴
     * 다를 경우 target 리턴
     *
     * @param target
     * @param source
     * @return
     */
    public static String isPresent(String target, String source) {
        return target.equals(source) ? source : target;
    }

    /**
     * 우측을 기준으로 값을 채운다.
     *
     * @param value
     * @param len
     * @param pad
     * @return
     */
    public static String rpad(String value, int len, String pad) {
        if (CommUtil.isEmpty(value)) {
            return value;
        }
        int lenStr = value.length();
        int lenPad = pad.length();
        if (lenStr < len && lenPad > 0) {
            StringBuffer sb = new StringBuffer(value);
            int i = lenStr + lenPad - 1;
            for (; i < len; i += lenPad) {
                sb.append(pad);
            }
            return sb.toString();

        } else {
            return value;
        }
    }

    public static <T> Map<Integer, List<T>> streamPaging(List<T> list, int pageSize) {
        return (Map<Integer, List<T>>) IntStream.iterate(0, i -> i + pageSize)
                .limit((list.size() + pageSize - 1) / pageSize)
                .boxed()
                .collect(Collectors.toMap(i -> i / pageSize, i -> list.subList(i, Math.min(i + pageSize, list.size()))));
    }

}