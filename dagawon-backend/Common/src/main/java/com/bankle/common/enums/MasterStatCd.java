package com.bankle.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public enum MasterStatCd {

    ESCR_RGST("1"), // - ESCR_RGST ==> 에스크로_등록
    INSU_JOIN("2"), // - INSU_JOIN ==> 보험형_보험_가입
    DOWN_AMT("3"), // - DOWN_AMT ==> 계약금
    INTR_AMT("4"), // - INTR_AMT ==> 중도금
    INTR_AMT_02("44"), // - INTR_AMT ==> 중도금
    BLNC_AMT("5"), // - BLNC_AMT ==> 잔금
    DEPOSIT("6"), // - DEPOSIT ==> 일반형_지급
    ESCR_CNCL("7"), // - ESCR_CNCL ==> 에스크로_취소
    ESCR_TRMN("8"), // - ESCR_TRMN ==> 에스크로_해약
    ESCR_END("9"); // - ESCR_END ==> 에스크로_종료

    private String stateCd;

    MasterStatCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public static MasterStatCd findByCode(String code) {
        List<MasterStatCd> statCdList = MasterStatCd.enumValuesInList(MasterStatCd.class);
        return statCdList.stream()
                .filter(data -> data.getStateCd().equals(code.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

    public static <T> List<T> enumValuesInList(Class<T> enumClass) {
        T[] arr = enumClass.getEnumConstants(); // Enum 아닌 경우 null
        return arr == null ? Collections.EMPTY_LIST : Arrays.asList(arr);
    }

    public String value() {
        return stateCd;
    }
}
