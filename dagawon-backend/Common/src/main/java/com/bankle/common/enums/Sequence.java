package com.bankle.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @package      : com.withuslaw.common.enums.Sequence
 * @name         : Sequence.java
 * @date         : 2023-09-18 오후 5:02
 * @author       : Tiger bk
 * @version      : 1.0.0
 * @desc         : 20231001 + seqLength (6자리) -> 2023100100006 14자리
 **/


@Getter
@AllArgsConstructor
public enum Sequence {
    CUST("CUST", 12),
    ADMIN_NO("ADMIN_NO", 12),
    BOARD("BOARD", 4),
    MESSAGE ("MESSAGE",  14),
    CONTRACT("CONTRACT", 14),
    IMAGE("IMAGE", 14),
    TRANS("TRANS", 14),
    ADMIN_REQUEST("ADMIN_REQUEST", 14),
    IMG_DOC("IMG_DOC", 12),
    BIZ_BACKUP("BIZ_BACKUP", 12),
    ACCT_VERF("ACCT_VERF",14),
    PUSH("PUSH",14),
    LOGIN("LOGIN",12),
    RGSTR_CASE("RGSTR_CASE",14),
    RGSTR_CASE_HIST("RGSTR_CASE",14),
    APP_VR("APP_VR",12),
    ;

    
    private final String seqNm;
    private final int seqLen;
    
    
    public static Sequence findBySeqName(String seqName) {
        for (Sequence sq : Sequence.values()) {
            if (sq.getSeqNm().equals(seqName)) {
                return sq;
            }
        }
        return null;
    }
}
