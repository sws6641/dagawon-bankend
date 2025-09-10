package com.bankle.common.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Random;

/**
 * NumberUtil
 *
 * @author sh.lee
 * @date 2023-09-18
 **/
public class NumberUtil {

    private static HashMap<String, DecimalFormat> cache;

    static {
        cache = new HashMap<String, DecimalFormat>();
        DecimalFormat defaultFormat = new DecimalFormat();
        defaultFormat.setParseBigDecimal(true);
        cache.put(null, defaultFormat);
    }

    private NumberUtil() {
    }

    public static String getNumberFormat(long value) {
        return getNumberFormat(value + "");
    }

    public static String getNumberFormat(double value) {
        return getNumberFormat(value + "");
    }

    public static String getNumberFormat(String value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(value));
    }

    public static String toDecimalFormat(Long amt) {
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(amt);
    }

    public static int toInt(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        } else {
            String s = value.toString();
            s = s.replaceAll("\\D+", "");
            if (s.length() == 0) {
                return 0;
            }
            return Integer.parseInt(s);
        }
    }

    public static Number nvl(Number value) {
        return nvl(value, BigDecimal.ZERO);
    }

    public static Number nvl(Number value, Number defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public static double toDouble(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else {
            String s = value.toString();
            if (!StringUtils.hasText(s)) {
                return 0.0;
            }
            return Double.parseDouble(s);
        }
    }

    public static BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else {
            String s = value.toString();
            if (!StringUtils.hasText(s)) {
                return null;
            }
            return new BigDecimal(s);
        }
    }

    public static Number parse(String source) {
        if (source == null) {
            return null;
        }
        try {
            return parse(source, null);
        } catch (ParseException ee) {
            throw new NumberFormatException(source);
        }
    }

    public static Number parse(String source, String pattern) throws ParseException {
        if (source == null) {
            return null;
        }
        return getInnerNumberFormat(pattern).parse(source);
    }

    public static String format(Number number, String pattern) {
        if (number == null) {
            return null;
        }
        return getInnerNumberFormat(pattern).format(number);
    }

    private static DecimalFormat getInnerNumberFormat(String pattern) {
        DecimalFormat f = cache.get(pattern);
        if (f == null) {
            f = createNumberformat(pattern);
        }
        return f;
    }

    private synchronized static DecimalFormat createNumberformat(String pattern) {
        DecimalFormat f = cache.get(pattern);
        if (f == null) {
            f = new DecimalFormat(pattern);
            f.setParseBigDecimal(true);
            cache.put(pattern, f);
        }
        return f;
    }

    public static boolean existDecimal(BigDecimal source) throws Exception {
        try {
            if (source.intValue() >= 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 자릿수를 받아와서 난수 생성을 진행한다.
     *
     * @param bound
     * @return
     */
    public static String ranNumber(int bound) {
        Random random = new Random();
        int createNum = 0;
        String ranNum = "";
        String resultNum = ""; // 생성된 난수를 담아준다.

        for (int i = 0; i < bound; i++) {
            createNum = random.nextInt(9);
            ranNum = Integer.toString(createNum);

            resultNum += ranNum;
        }
        return resultNum; // 생성된 난수를 리턴한다.
    }

    /**
     * 금액 필드 3자리수 comma
     * ex ) 1,000,000
     *
     * @param amt
     * @return
     */
    public static String amtCommaFormat(BigDecimal amt) {
        if (amt == null) {
            return "";
        }
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(amt);
    }
}
