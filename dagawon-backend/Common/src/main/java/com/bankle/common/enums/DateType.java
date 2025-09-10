package com.bankle.common.enums;

import lombok.Getter;

@Getter
public enum DateType {

    DONE("0"),
    DOWN("1"),
    INTR_01("2"),
    INTR_02("3"),
    BLNC("4"),
    DEPOSIT("5")
    ;

    private String dateType;

    DateType(String dateType) {
        this.dateType = dateType;
    }

    public String value() {
        return dateType;
    }
}
