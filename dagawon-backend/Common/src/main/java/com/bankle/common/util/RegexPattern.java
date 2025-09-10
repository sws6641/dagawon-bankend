package com.bankle.common.util;

/**
 * RegexPattern
 *
 * @author sh.lee
 * @date 2023-09-18
**/
public class RegexPattern {

    /** 숫자 */
    public final static String NUMBER = "^[0-9]*$";

    /** 휴대폰 번호 */
    public final static String CELLPHONE_NUMBER = "^01[0-9][0-9]{3,4}[0-9]{4}$";

    /** 휴대폰 번호(하이픈 사용) */
    public final static String CELLPHONE_NUMBER_WITH_HYPHEN = "^01[0-9][-/.]?[0-9]{3,4}[-/.]?[0-9]{4}$";

    /** YN 여부 */
    public final static String YN = "^[YN]{1}$";

    /** 버전 */
    public final static String VERSION = "^[0-9].[0-9]$";

    /** 일자(DE에 해당) */
    public final static String DATE = "^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";

    /** 시간(TM에 해당) */
    public final static String TIME = "^(?<hour>([0-1]?[0-9]|2[0-3]))(:(?<minute>[0-5]?[0-9]))?(:(?<second>[0-5]?[0-9]))?$";

    /** 일시(DT에 해당) */
    public final static String DATE_TIME = "^[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) (0[1-9]|1[0-9]|2[0-4]):(0[0-9]|[1-5][0-9]):(0[0-9]|[1-5][0-9])$";

    /** 연속된 숫자 세자리 이상 */
    public final static String CONSECUTIVE_NUMBER = "012|123|234|345|456|567|678|789|890|098|987|876|765|654|543|432|321|210";

    /** 세번이상 반복되는 값을 포함 */
    public final static String REPEAT_VALUE = "(.)\\1\\1";
}
