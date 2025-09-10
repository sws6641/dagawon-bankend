package com.bankle.common.asis.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnterStatus {

    SINED_UP("1"),
    DORMENT("2"),
    DROP_OUT("9");

    private String code;
}
