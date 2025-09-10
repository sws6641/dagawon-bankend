package com.bankle.common.util;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

/**
 * AddressUtil.class
 *
 * @author sh.lee
 * @date 2024-04-26
 */
@Slf4j
public class AddressUtil {

    public static final String delReverseOneWord = "도,시,군,구,동,호,읍,면,동,로,길,층,외";

    public static final int levelVrf = 4;
    public static final int cntVrfOk = 6;

    public static boolean compareAddr(String addr1, String addr2) {
        List<String> list1 = getListAddress(addr1); // 의뢰서 주소
        List<String> list2 = getListAddress(addr2); // 등기부 주소

        int i = 0;
        int j = 0;
        String stdAddr = "";
        boolean rtnValue = true;
        boolean subValue = false;

        for (i = 0; i < levelVrf; i++) {
            stdAddr = list1.get(i);
            subValue = false;

            for (j = 0; j < list2.size(); j++) {
                if (stdAddr.equals(list2.get(j).toString())) {
                    subValue = true;
                    break;
                }
            }

            if (!subValue) {
                rtnValue = false;
                break;
            }
        }

        int okCnt = 0;
        if (rtnValue) {
            for (i = 0; i < list1.size(); i++) {
                stdAddr = list1.get(i);

                for (j = 0; j < list2.size(); j++) {
                    if (stdAddr.equals(list2.get(j).toString())) {
                        okCnt++;
                    }
                }
            }

            if (okCnt < cntVrfOk)
                rtnValue = false;
        }

        return rtnValue;
    }

    public static List<String> getListAddress(String addr) {

        List<String> listAddr = splitAddr(addr);
        listAddr = delWord(listAddr);
        listAddr = delReverseWord(listAddr);
        listAddr = delSign(listAddr);

        String printWord = "";
        for (String item : listAddr) {
            printWord += "[" + item + "], ";
        }
        log.debug(" getListAddress =====>> " + printWord);

        return listAddr;
    }

    public static List<String> splitAddr(String addr) {

        List<String> listResult = new ArrayList<String>();
        int i = 0;
        char ch = ' ';
        char afCh = ' ';
        String strTemp = "";

        for (i = 0; i < addr.length(); i++) {

            ch = addr.charAt(i);

            if (i < (addr.length() - 1)) {
                afCh = addr.charAt(i + 1);
            } else {
                afCh = ' ';
            }

            if (ch == ' ' || ch == '-') {
                chkSIGU(strTemp.trim(), listResult);
                strTemp = "";
            } else if ((ch < 48 || ch > 57) && (afCh >= 48 && afCh <= 57)) {
                strTemp += ch;
                chkSIGU(strTemp.trim(), listResult);
                strTemp = "";
            } else {
                strTemp += ch;
            }
        }
        listResult.add(strTemp.trim());

        return listResult;
    }

    public static List<String> delWord(List<String> listAddr) {

        List<String> listResult = new ArrayList<String>();

        for (String item : listAddr) {

            item = item.replace("특별시", "").replace("광역시", "").replace("특별자치도", "").replace("번지", "");

            listResult.add(item);
        }

        return listResult;
    }

    public static List<String> delReverseWord(List<String> listAddr) {

        List<String> listResult = new ArrayList<String>();
        String subWord = "";

        for (String item : listAddr) {

            if (!"".equals(item)) {
                item = new StringBuffer(item).reverse().toString();

                subWord = item.substring(0, 1);

                if (delReverseOneWord.indexOf(subWord) >= 0) {
                    item = item.substring(1, item.length());
                }

                item = new StringBuffer(item).reverse().toString();

                listResult.add(item);
            }
        }

        return listResult;
    }

    public static List<String> delSign(List<String> listAddr) {

        List<String> listResult = new ArrayList<String>();

        for (String item : listAddr) {

            item = item.replaceAll("-", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(",", "");

            listResult.add(item);
        }

        return listResult;
    }

    public static void chkSIGU(String addr, List<String> listResult) {

        int i = 0;
        int indexSI = -1;

        if (addr.length() < 2) {
            listResult.add(addr);
        } else {

            String reverseWord = new StringBuffer(addr).reverse().toString();

            String subWord1 = reverseWord.substring(0, 1);
            String subWord2 = reverseWord.substring(1, 2);

            if ("구".equals(subWord1) && !"시".equals(subWord2)) {
                for (i = 0; i < addr.length(); i++) {
                    if ('시' == addr.charAt(i)) {
                        indexSI = i + 1;
                    }
                }
            }

            if (indexSI >= 0) {
                listResult.add(addr.substring(0, indexSI));
                listResult.add(addr.substring(indexSI, addr.length()));
            } else {
                listResult.add(addr);
            }
        }
    }

    public static String getIrosAddress(String contents) throws Exception {

        String errYn = "";
        String unqNoAddress = "";


        JSONParser jsonParser = new JSONParser();
        JSONObject objContents = (JSONObject) jsonParser.parse(contents);
        JSONObject objOut = (JSONObject) objContents.get("out");
        JSONObject objOutC000 = (JSONObject) objOut.get("outC0000");

        errYn = objOutC000.get("errYn").toString();

        if ("N".equals(errYn)) {

            JSONArray objList = (JSONArray) objOutC000.get("list");
            JSONObject oblListRow = (JSONObject) objList.get(0);

            unqNoAddress = oblListRow.get("부동산소재지번").toString();
        } else {
            unqNoAddress = "고유번호에 해당하는 소재지번을 확인할 수 없습니다.";
        }

        return unqNoAddress;
    }

    public static String getSidoNm(String sidoNm) {
        return switch (sidoNm) {
            case "서울특별시" -> "서울";
            case "부산광역시" -> "부산";
            case "대구광역시" -> "대구";
            case "인천광역시" -> "인천";
            case "광주광역시" -> "광주";
            case "대전광역시" -> "대전";
            case "울산광역시" -> "울산";
            case "세종특별자치시" -> "세종";
            case "충청북도" -> "충북";
            case "충청남도" -> "충남";
            case "전라북도" -> "전북";
            case "전라남도" -> "전남";
            case "경상북도" -> "경북";
            case "경상남도" -> "경남";
            case "제주특별자치도" -> "제주";
            case "경기도" -> "경기";
            case "강원도" -> "강원";
            case "강원특별자치도" -> "강원";
            default -> sidoNm;
        };
    }

}
