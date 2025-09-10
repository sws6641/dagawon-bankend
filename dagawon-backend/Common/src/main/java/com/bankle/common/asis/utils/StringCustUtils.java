package com.bankle.common.asis.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringCustUtils extends StringUtils {

    @Autowired
    private static MessageSource messageSource;

    /**
     * query strng to Map
     * @param qs
     * @return
     */
    public static Map<String, List<String>> stringToMap(String qs) {

        Map<String, List<String>> pairsMap = new LinkedHashMap<>();
        String[] pairs = qs.split("&");
        for (String pair : pairs) {
            if(isNotEmpty(pair)){
                int idx = pair.indexOf("=");
                String key = idx > 0 ? substringBefore(pair, "=") : pair;
                if (!pairsMap.containsKey(key)) {
                    pairsMap.put(key, new LinkedList());
                    pairsMap.get(key).add(( idx > 0 ) ? substringAfter(pair,"=") : null);
                }
            }
        }

        return pairsMap;
    }

    public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getOnlyNum(String value) {
        String trimmedValue = trim(value);
        if(isEmpty(trimmedValue)) {
            return value;
        }
        return value.replaceAll("[^0-9]", "");
    }

    public static String getAmountWithComma(String value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(value));
    }

    /**
     * key, value 필수값 체크
     * @param keysMap
     * @param str
     * @return
     */
    public static Map<String, String> validate(HashMap keysMap, String str){

       Map<String, String> errors = new HashMap<>();

       //Declare and check all mandatory fields
       final String[] keys = str.trim().split(",");
       for(String key: keys){
           final String value = ((String) keysMap.get("key")).trim();
           if(StringCustUtils.isEmpty(value)){
               errors.put(key, key);
           }
       }

       return errors;
    }

    /**
     * 패스워드 유효검사
     * @param password
     * @return errorMsg
     */
    public static String isValidPassword(String password){
        String errorMsg = "";

        if(password.length() < 8){
            return getErrorMsg("error.password.char");
        }

        if(!password.matches(".*[0-9].*")){
            return getErrorMsg("error.password.number");
        }

        if(!password.matches(".*[A-Z].*")){
            return getErrorMsg("error.password.capital");
        }

        if(!password.matches(".*[-!@#$%^&*()_+|~=`{}:'<>?,.\"\\[\\];\\\\/].*")){
            return getErrorMsg("error.password.specialChar");
        }

        return errorMsg;
    }

    public static String getErrorMsg(String errorCode){
        return messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
    }

    public static Long StringToLong(Object amt){
        String val = String.valueOf(amt);
        if(isEmpty(val))
            return 0L;

        val = val.replaceAll(",","");
        return Long.valueOf(val);
    }

    public static String lpad(String value, int len, String pad) {
        if(StringCustUtils.isEmpty(value)) {
            return value;
        }
        int lenStr = value.length();
        int lenPad = pad.length();
        if (lenStr < len && lenPad > 0) {
            StringBuilder sb = new StringBuilder(len);
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
    
    public static String rpad(String value, int len, String pad) {
        if(StringCustUtils.isEmpty(value)) {
            return value;
        }
        int lenStr = value.length();
        int lenPad = pad.length();
        if (lenStr < len && lenPad > 0) {
            StringBuilder sb = new StringBuilder(value);
            int i = lenStr + lenPad -1;
            for (; i < len; i += lenPad) {
                sb.append(pad);
            }
            return sb.toString();

        } else {
            return value;
        }
    }    
    
    public static String getDate(String strFmt) {
        
        if ("".equals(strFmt)) strFmt = "yyyy-MM-dd";
        
        Calendar         cal  = Calendar.getInstance();
        SimpleDateFormat fmt  = new SimpleDateFormat(strFmt);
        String           date = fmt.format(cal.getTime());

        return date;
    }    
    
    /*=============================================================================*/
    /* Function Name : NVL ==> Null String 특정 문자열 대체 함수                   */
    /*=============================================================================*/
    public static String nvl(String str) {
        
        return nvl(str, "", "N");
    }
    
    public static String nvl(String str, String repStr) {
        
        return nvl(str, repStr, "N");
    }
    
    public static String nvlTrim(String str) {
        
        return nvl(str, "", "Y");
    }

    public static String nvlTrim(String str, String repStr) {
        
        return nvl(str, repStr, "Y");
    }
    
    public static String nvl(String str, String repStr, String trimYN) {
        
        String strRslt = (str == null) ? repStr : str;
        
        if ( "Y".equals(trimYN)) { strRslt = strRslt.trim(); }

        return strRslt;
    }
    /*=============================================================================*/
    
    public static String validParam(String chkCd, String varStr, String varNm) {
        String errMsg    = "";
        String checkRole = "";
        
        if      ("1".equals(chkCd)) { checkRole = "^[0-9]*";          }  // 숫자만  
        else if ("2".equals(chkCd)) { checkRole = "^[0-9a-zA-Z]+$";   }  // 숫자 + 영문
        else if ("3".equals(chkCd)) { checkRole = "^[가-힝a-zA-Z ]*"; }  // 한글 + 영문
        else                        { return errMsg;                  }
        
        if (!varStr.matches(checkRole)) {
            errMsg = varNm + "에 유효하지 않은 문자열이 있습니다. \n";
        }

        return errMsg;
    }
    
    public static String getAmountComma(String value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(value));
    }
    
    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }
    
    public static boolean isEmpty(Object o) {
        if(o == null)
            return true;

        String str = String.valueOf(o);
        return StringUtils.isEmpty(str);
    }
    
    public static String toDecimalFormat(Long amt){
        DecimalFormat format = new DecimalFormat("###,###");
        return format.format(amt);
    }

    public static long mapToStringL(HashMap<String, Object> map, String keyName) {
        
        String rtnValue = mapToString(map, keyName);
        
        if ("".equals(rtnValue)) rtnValue = "0";
        
        return Long.parseLong(rtnValue);
    }
    
    public static String mapToString(HashMap<String, Object> map, String keyName) {
        
        String rtnValue = "";
        
        try {            
            rtnValue = nvl(map.get(keyName).toString());
        } catch (Exception Ex) {
            rtnValue = "";
        }
        
        return rtnValue;
    }
    
    public static void printMapInfo(HashMap<String, Object> map) {
        
        int i = 0;
        for(String key: map.keySet()) {
            log.debug("=====>> map [" + i + "]   key [" + key + "]   value ["  + map.get(key) + "]");
            i++;
        }
        
    }
    
    public static String getNumberFormat(long   value) { return getNumberFormat(value + ""); }
    public static String getNumberFormat(double value) { return getNumberFormat(value + ""); }
    public static String getNumberFormat(String value) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(Double.parseDouble(value));
    }
}
