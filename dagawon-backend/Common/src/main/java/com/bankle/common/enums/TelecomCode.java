package com.bankle.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TelecomCode {
    SKT("SKT", "1"),
    KT ("KT",  "2"),
    LGT("LGT", "3"),
    SKM("SKM", "5"),
    KTM("KTM", "6"),
    LGM("LGM", "7");

    private final String code;
    private final String no;

    public static TelecomCode findTelecomCode(String code) {
        for (TelecomCode tcc : TelecomCode.values()) {
            if (tcc.getCode().equals(code)) {
                return tcc;
            }
        }
        return null;
    }
}
