package com.bankle.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public enum MasterDtlStatCd {

    FEE_PAY("1"), //  - FEE_PAY ==> 수수료_결제
    FEE_PAY_SUCC("2"), //  - FEE_PAY_SUCC ==> 수수료_결제_완료
    INSU_ING("3"), //  - INSU_ING ==> 보험형_보험청약_진행중
    INSU_SUCC("4"), //  - INSU_SUCC ==> 보험형_보험청약_완료
    INSU_FAIL("5"), //  - INSU_FAIL ==> 보험형_보험청약_거절
    DEP_REQ("6"), //  - DEP_REQ ==> 입금_요청
    DEP_SUCC("7"), //  - DEP_SUCC ==> 입금_완료
    DEP_DELAY("8"), //  - DEP_DELAY ==> 입금_지연
    PAY_REQ("9"), //  - PAY_REQ ==> 지급_요청
    PAY_SUCC("10"), //  - PAY_SUCC ==> 지급_완료
    PAY_DELAY("11"), //  - PAY_DELAY ==> 지급_지연
    ESCR_CNCL("12"), //  - ESCR_CNCL ==> 에스크로_취소
    ESCR_TRMN("13"), //  - ESCR_TRMN ==> 에스크로_해약
    ESCR_END("14"), //  - ESCR_END ==> 에스크로_종료
    FIX_DT("15") //  - FIX_DT ==> 보험형_확정일자_등록
    ;


    private String stateCd;

    MasterDtlStatCd(String stateCd) {
        this.stateCd = stateCd;
    }

    public static MasterDtlStatCd findByCode(String code) {
        List<MasterDtlStatCd> statCdList = MasterDtlStatCd.enumValuesInList(MasterDtlStatCd.class);
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
