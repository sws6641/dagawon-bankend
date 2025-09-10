package com.bankle.common.util;

import com.bankle.common.enums.MaskType;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class MaskUtil {

    /**
     * 타입별 마스킹 처리
     *
     * @param data
     * @param maskType
     * @return
     */
    public static String getMaskedValue(String data, MaskType maskType) {
        return switch (maskType) {
            case NAME -> getMaskedName(data);
            case CPHN_NO -> getMaskedCphnNo(data);
            case EMAIL_ID -> getMaskedEmail(data);
            case BIRTH_DTM -> getMaksedBirthDe(data);
            case ACCT_NO -> getMasedAcctNo(data);
//            case ADDRESS -> getMaskedAddress(data);
            default -> "";
        };
    }

    /**
     * 이름 마스킹
     *
     * @param userNm
     * @return
     */
    public static String getMaskedName(String userNm) {
        if (userNm.length() == 1) {
            return userNm;
        } else if (userNm.length() == 2) {
            return userNm.charAt(0) + "*";
        } else if (userNm.length() > 2) {
            String first = userNm.substring(0, 1);
            String last = userNm.substring(userNm.length() - 1);
            char[] c = new char[userNm.length() - 2];
            Arrays.fill(c, '*');
            return first + String.valueOf(c) + last;
        } else {
            return "";
        }
    }

    /**
     * 휴대폰번호 마스킹
     *
     * @param cphnNo
     * @return
     */
    public static String getMaskedCphnNo(String cphnNo) {
        if (cphnNo.contains("-")) {
            if (cphnNo.length() == 13)
                return cphnNo.substring(0, 4) + "****" + cphnNo.substring(8, 13);
            if (cphnNo.length() == 12)
                return cphnNo.substring(0, 4) + "***" + cphnNo.substring(7, 12);
        } else {
            if (cphnNo.length() == 10)
                return cphnNo.substring(0, 3) + "***" + cphnNo.substring(6, 10);
            if (cphnNo.length() == 11)
                return cphnNo.substring(0, 3) + "****" + cphnNo.substring(7, 11);
        }
        return "";
    }

    /**
     * 계좌번호 마스킹
     *
     * @param acctNo
     * @return
     */
    public static String getMasedAcctNo(String acctNo) {
        Integer acctLength = acctNo.length();
        String masked = acctNo.substring(0, acctLength - 4);
        masked = masked.replaceAll("[0-9]", "*");

        return masked + acctNo.substring(acctLength - 4, acctLength);
    }

    /**
     * email 마스킹
     *
     * @param email
     * @return
     */
    public static String getMaskedEmail(String email) {
        Matcher matcher = Pattern.compile("^(..)(.*)@(.*)$").matcher(email);
        if (matcher.matches()) {
            char[] c = new char[matcher.group(2).length()];
            Arrays.fill(c, '*');
            return email.replaceAll("^(..)(.*)@(.*)$", "$1" + String.valueOf(c) + "@$3");
        }
        return email;
    }

    /**
     * 생년월일 마스킹
     *
     * @param birthDe
     * @return
     */
    public static String getMaksedBirthDe(String birthDe) {
        Matcher matcher = Pattern.compile(RegexPattern.DATE).matcher(birthDe);
        if (matcher.find()) {
            return birthDe.replaceAll("[0-9]", "*");
        }
        return birthDe;
    }

    /**
     * 주소 마스킹
     *
     * @param order
     * @param address
     * @return
     */
    public static String getMaskedAddress(String order, String address) {

        String retAddr = "";

//        String[] arrAddr = address.split("((\\d{1,5})+[^가|^길|].*)");
        String[] arrAddr = address.split("([가-힣]*-?((\\d{1,5})+[^가|^로|^길].*))");
        String beforeAddr = arrAddr[0];
        String afterAddr = address.replace(beforeAddr, "");
        afterAddr = afterAddr.replaceAll("[0-9]", "*");
        String fullAddress = beforeAddr.concat(" ").concat(afterAddr);


        switch (order) {
            case "BEF" -> {
                // BEF의 경우 마스킹이 되기 이전 주소를 보내준다.
                retAddr = beforeAddr;
            }
            case "AFT" -> {
                // AFT의 경우 마스킹이 된 주소를 보내준다.
                retAddr = afterAddr;
            }
            case "ALL" -> {
                // ALL의 경우 전체 풀 주소를 보내준다.
                retAddr = fullAddress;
            }
        }
        return retAddr.replaceAll("  ", " ");
    }
}
