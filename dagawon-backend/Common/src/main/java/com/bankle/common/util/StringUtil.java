package com.bankle.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 스트링 유틸
 *
 * @author LSH
 * @since 2023.02.20
 */
@Slf4j
public class StringUtil {
    private static final Object _syncObjWorkerKey = new Object();



    /**
     * NULL 을 제외하고, 나머지 모든 문자열들을 붙여준다.<br/>
     *
     * <pre>
     * StringUtil.concatExceptNull(null, null) = null
     * StringUtil.concatExceptNull(null, "abc", null, "def", null) = "abcdef"
     * </pre>
     */
    public static String concatExceptNull(String... strings) {
        String ret = null;
        for (String str : strings) {
            if (null != str) {
                if (null == ret) {
                    ret = str;
                } else {
                    ret += str;
                }
            }
        }
        return ret;
    }

    /**
     * src 안에, searches 의 요소들이 모두 포함되었는지 확인한다.
     */
    public static boolean containsAll(String src, String[] searches) {
        for (String search : searches) {
            if (!src.contains(search)) {
                return false;
            }
        }
        return true;
    }

    public static String convertHangul(String money) {
        money = money.replaceAll(",", "");
        DecimalFormat result = new DecimalFormat("#,###");
        return result.format(Double.parseDouble(money));
    }

    public static String dateFormatOfPattern(String date) {
        if (8 == date.length()) {
            return date.replaceAll("(\\d{4})(\\d{2})(\\d{2})", "$1-$2-$3");
        }
        return date;
    }

    public static String deCamelCase(String target) {
        StringBuffer buffer = new StringBuffer();
        char[] chrs = target.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            char c = chrs[i];
            if (c >= 'A' && c <= 'Z') {
                buffer.append('_');
            }
            buffer.append(c);
        }
        return buffer.toString();
    }

    /**
     * 문자열의 왼쪽에 지정한 수만큼의 글자만 남기고, 나머지를 지정한 문자로 대체한다.
     *
     * <pre>
     * StringUtil.doMaskRemainLeft(null, '*', 10) = null
     * StringUtil.doMaskRemainLeft("", '*', 10) = ""
     * StringUtil.doMaskRemainLeft("abcdef", '*', 10) = "abcdef"
     * StringUtil.doMaskRemainLeft("abcdef", '*', 3) = "abc***"
     * StringUtil.doMaskRemainLeft("abcdef", '*', -1) = Exception
     * </pre>
     */
    public static String doMaskRemainLeft(String src, char maskingChar, int remainCounts) {
        if ((null != src) && (remainCounts < src.length())) {
            return src.substring(0, remainCounts) + StringUtils.repeat(maskingChar, src.length() - remainCounts);
        } else {
            return src;
        }
    }

    /**
     * 문자열의 왼쪽과 오른쪽에 지정한 수만큼의 글자만 남기고, 나머지를 지정한 문자로 대체한다.
     *
     * <pre>
     * StringUtil.doMaskRemainLeftAndRight(null, '*', 3, 3) = null
     * StringUtil.doMaskRemainLeftAndRight("", '*', 3, 3) = ""
     * StringUtil.doMaskRemainLeftAndRight("abcdef", '*', 3, 3) = "abcdef"
     * StringUtil.doMaskRemainLeftAndRight("abcdef", '*', 1, 2) = "a***ef"
     * StringUtil.doMaskRemainLeftAndRight("abcdef", '*', -1, 1) = Exception
     * StringUtil.doMaskRemainLeftAndRight("abcdef", '*', 1, -1) = Exception
     * </pre>
     */
    public static String doMaskRemainLeftAndRight(String src, char maskingChar, int leftRemainCounts, int rightRemainCounts) {
        if ((null != src) && ((leftRemainCounts + rightRemainCounts) < src.length())) {
            return src.substring(0, leftRemainCounts) + StringUtils.repeat(maskingChar, src.length() - (leftRemainCounts + rightRemainCounts)) + src.substring(src.length() - rightRemainCounts);
        } else {
            return src;
        }
    }

    /**
     * 문자열의 오른쪽에 지정한 수만큼의 글자만 남기고, 나머지를 지정한 문자로 대체한다.
     *
     * <pre>
     * StringUtil.doMaskRemainRight(null, '*', 10) = null
     * StringUtil.doMaskRemainRight("", '*', 10) = ""
     * StringUtil.doMaskRemainRight("abcdef", '*', 10) = "abcdef"
     * StringUtil.doMaskRemainRight("abcdef", '*', 3) = "***def"
     * StringUtil.doMaskRemainRight("abcdef", '*', -1) = Exception
     * </pre>
     */
    public static String doMaskRemainRight(String src, char maskingChar, int remainCounts) {
        if ((null != src) && (remainCounts < src.length())) {
            int startIndex = src.length() - remainCounts;
            return StringUtils.repeat(maskingChar, startIndex) + src.substring(startIndex);
        } else {
            return src;
        }
    }

    /**
     * 문자열의 오른쪽에 지정한 수만큼의 글자를, 지정한 문자로 대체한다.
     *
     * <pre>
     * StringUtil.doMaskRight(null, '*', 10) = null
     * StringUtil.doMaskRight("abcdef", '*', 10) = "******"
     * StringUtil.doMaskRight("abcdef", '*', 3) = "abc***"
     * StringUtil.doMaskRight("abcdef", '*', -1) = Exception
     * </pre>
     */
    public static String doMaskRight(String src, char maskingChar, int maskCounts) {
        if (null == src) return null;
        if (maskCounts < src.length()) {
            return src.substring(0, src.length() - maskCounts) + StringUtils.repeat(maskingChar, maskCounts);
        } else {
            return StringUtils.repeat(maskingChar, src.length());
        }
    }

    public static Boolean empty(Object obj) {
        if (obj instanceof String) return obj == null || "".equals(obj.toString().trim());
        else if (obj instanceof List) return obj == null || ((List<?>) obj).isEmpty();
        else if (obj instanceof Map) return obj == null || ((Map<?, ?>) obj).isEmpty();
        else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
        else if (obj instanceof Integer) return obj == null || (Integer) obj == 0;
        else if (obj instanceof Long) return obj == null || (Long) obj == 0;
        else return obj == null;
    }

    public static boolean equals(String target, String source) {
        return source.equals(target);
    }


    public static boolean equalsAny(String target, String source1, String source2) {
        return source1.equals(target) || source2.equals(target);
    }

    public static String getAmountComma(String value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(value));
    }

    /**
     * null을 빈문자열로 치환
     *
     * @param data
     * @return
     */
    public static String getNullToStr(String data) {
        String rs = "";
        if (!StringUtils.isBlank(data)) {
            if (data == null) {
                return rs;
            }
            return data;
        }
        return rs;
    }

    public static String getRandomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 스트링 빈값('')을 NULL로 치환
     *
     * @param data
     * @return
     */
    public static String getStrToNull(String data) {
        String rs = null;
        if (!StringUtils.isBlank(data)) {
            if ("null".equals(data)) {
                return rs;
            }
            return data;
        }
        return rs;
    }

    public static String getTime(String pattern) {
        SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        return fmt.format(new Date(System.currentTimeMillis()));
    }

    public static boolean isEmpty(String value) {
        return (value == null || "".equals(value));
    }

    public static boolean isIn(String source, String... compares) {
        return isIn(source, true, compares);
    }

    public static boolean isIn(String source, boolean isCaseSensitive, String... compares) {
        if (!isCaseSensitive && source != null) {
            source = source.toLowerCase();
        }
        for (String compare : compares) {
            if (compare == null && source == null) return true;
            else if (compare == null || source == null) continue;
            if (source.equals(isCaseSensitive ? compare : compare.toLowerCase())) return true;
        }
        return false;
    }

    public static boolean isInObj(Object source, Object... compares) {
        for (Object compare : compares) {
            if (compare == null && source == null) return true;
            else if (compare == null || source == null) continue;
            if (source.equals(compare)) return true;
        }
        return false;
    }

    public static boolean isMultiIndexOf(String source, String... compares) {
        if (source == null) return false;
        for (String compare : compares) {
            if (source.indexOf(compare) > -1) return true;
        }
        return false;
    }

    public static <T extends String> String isNull(Object source, Object target) {
        boolean tfSource = org.springframework.util.StringUtils.hasText(String.valueOf(source));
        if (tfSource) {
            return String.valueOf(source);
        }
        return String.valueOf(target);
    }

    /**
     * 문자열을 지정한 크기의 Byte로 자르거나 늘리는 함수<br/>
     * 네트워크로 전송하기 전에, 고정길이 문자열로 변환하는 용도.<br/>
     * 문자열의 길이가 지정한 길이보다 짧다면, 왼쪽에 공백을 붙인다.
     */
    public static String leftPadBytes(final String str, final char padChar, final int size, Charset charSet) {
        if (null == str) {
            return StringUtils.repeat(padChar, size);
        } else {
            byte[] buffer = str.getBytes(charSet);
            if (size < buffer.length) {
                byte[] tmpBuffer = new byte[size];
                System.arraycopy(buffer, 0, tmpBuffer, 0, size);
                return new String(tmpBuffer, charSet);
            }
            return StringUtils.repeat(padChar, size - buffer.length) + str;
        }
    }

    public static String lpad(String value, int len, String pad) {
        if (StringUtil.isEmpty(value)) {
            return value;
        }
        int lenStr = value.length();
        int lenPad = pad.length();
        if (lenStr < len && lenPad > 0) {
            StringBuffer sb = new StringBuffer(len);
            int i = lenStr + lenPad;
            for (; i < len; i += lenPad) {
                sb.append(pad);
            }
            sb.append(pad, 0, (len - i + lenPad));
            sb.append(value);
            return sb.toString();
        } else {
            return value;
        }
    }

    public static String lpad(String src, int size, char c) {
        StringBuilder sb = new StringBuilder();
        int startPos = src != null ? src.length() : 0;
        for (int i = startPos; i < size; i++) {
            sb.insert(0, c);
        }
        if (src != null) sb.append(src);
        return sb.toString();
    }


    public static String mapToString(Map<String, Object> map, String keyName) {
        String rtnValue = "";
        try {
            rtnValue = nvl(map.get(keyName).toString());
        } catch (Exception Ex) {
            rtnValue = "";
        }

        rtnValue = ("false".equals(rtnValue)) ? "" : rtnValue;

        return rtnValue;
    }

    public static long mapToStringL(HashMap<String, Object> map, String keyName) {

        String rtnValue = mapToString(map, keyName);

        if ("".equals(rtnValue)) rtnValue = "0";

        return Long.parseLong(rtnValue);
    }

    public static Boolean notEmpty(Object obj) {
        return !empty(obj);
    }

    public static String nvl(String value, String defaultValue) {
        if (isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    public static String nvl(String value) {
        return nvl(value, "");
    }

    /**
     * 이함수는 휴대폰번호를 "-"을 포함하여 데이터 포멧하여 반환시켜주는 함수이다.
     */
    public static String phoneFormat(String number) {
        if (number.length() == 10) {
            // 10자리 전화번호 형식: 011-229-5845
            String regEx = "(\\d{3})(\\d{3})(\\d{4})";
            return number.replaceAll(regEx, "$1-$2-$3");
        } else if (number.length() == 11) {
            // 11자리 전화번호 형식: 010-4797-4115
            String regEx = "(\\d{3})(\\d{4})(\\d{4})";
            return number.replaceAll(regEx, "$1-$2-$3");
        } else {
            // 그 외의 경우는 그대로 반환
            return number;
        }
    }

    public static void printMapInfo(HashMap<String, Object> map) {
        int i = 0;
        for (String key : map.keySet()) {
            log.debug("=====>> map [" + i + "]   key [" + key + "]   value [" + map.get(key) + "]");
            i++;
        }
    }

    public static String replaceEnter(String str) {
        str = str.replaceAll("\\\\n", " ");
        return str;
    }

    /**
     * 문자열을 지정한 크기의 Byte로 자르거나 늘리는 함수<br/>
     * 네트워크로 전송하기 전에, 고정길이 문자열로 변환하는 용도.<br/>
     * 문자열의 길이가 지정한 길이보다 짧다면, 오른쪽에 공백을 붙인다.
     */
    public static String rightPadBytes(final String str, final int size, Charset charSet) {
        if (null == str) {
            return StringUtils.repeat(' ', size);
        } else {
            byte[] buffer = str.getBytes(charSet);
            if (size < buffer.length) {
                byte[] tmpBuffer = new byte[size];
                System.arraycopy(buffer, 0, tmpBuffer, 0, size);
                return new String(tmpBuffer, charSet);
            }
            return str + StringUtils.repeat(' ', size - buffer.length);
        }
    }

    public static String rpad(String value, int len, String pad) {
        if (StringUtil.isEmpty(value)) {
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

    /**
     * 배열에 있는 모든 문자열의 맨 앞과 맨 뒤를 char 를 잘라내주는 함수.<br/>
     * 리턴되는 배열원 원본 배열과 동일한 배열이나, 배열의 요소들은 다른 객체일 수 있음.
     *
     * <pre>
     * StringUtil.slice_first_end_string( null ) = null
     * StringUtil.slice_first_end_string( { } ) = { }
     * StringUtil.slice_first_end_string( { "abcdef", null } ) = { "bcde", null }
     * StringUtil.slice_first_end_string( { "abc", "ab", "a", "" } ) = { "b", "", "", "" }
     * </pre>
     */
    public static String[] sliceFirstEndString(String[] arrayString) {
        if (null == arrayString) {
            return null;
        }
        for (int ii = 0; ii < arrayString.length; ++ii) {
            String _text = arrayString[ii];
            if (null != _text) {
                if (2 < _text.length()) {
                    arrayString[ii] = _text.substring(1, _text.length() - 1);
                } else {
                    arrayString[ii] = "";
                }
            }
        }
        return arrayString;
    }

    public static Long stringToLong(Object amt) {
        String val = String.valueOf(amt);
        if (isEmpty(val)) return 0L;

        val = val.replaceAll(",", "");
        return Long.valueOf(val);
    }


    public static String subStrByte(String str, int cutlen) throws Exception {
        if (!str.isEmpty()) {
            str = str.trim();
            if (str.getBytes("euc-kr").length <= cutlen) {
                return str;
            } else {
                StringBuffer sbStr = new StringBuffer(cutlen);
                int nCnt = 0;
                for (char ch : str.toCharArray()) {
                    nCnt += String.valueOf(ch).getBytes("euc-kr").length;
                    if (nCnt > cutlen) break;
                    sbStr.append(ch);
                }
                return sbStr.toString();
            }
        } else {
            return "";
        }
    }


    public static <K> K toCamelCase(Object obj, Class<K> clazz) {
        return clazz.cast(toCamelCase(String.valueOf(obj)));
    }

    public static String toCamelCase(String target) {
        StringBuffer buffer = new StringBuffer();
        char[] chrs = target.toCharArray();
        for (int i = 0; i < chrs.length; i++) {
            boolean isUpper = chrs[i] == '_';
            char c = isUpper ? chrs[++i] : chrs[i];

            if (c >= 'A' && c <= 'Z') {
                c = (char) (c + 0x20);
            }
            if (isUpper && (c >= 'a' && c <= 'z')) {
                c = (char) (c - 0x20);
            }
            buffer.append(c);
        }
        return buffer.toString();
    }

    /**
     * toString
     */
    public static String toString(Object o) {
        return o.toString() != null ? o.toString() : null;
    }

    /**
     * null 이 아닐 경우에만, trim 을 수행한다.
     */
    public static String trim(String param) {
        return param.trim() != null ? param.trim() : null;
    }

}
