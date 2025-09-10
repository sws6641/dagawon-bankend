package com.bankle.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MaskType 설명
 *
 * @author sh.lee
 * @date 2023-09-18
 * @exception
 **/
@Getter
@AllArgsConstructor
public enum MaskType {
    NAME, CPHN_NO, EMAIL_ID, BIRTH_DTM, ADDRESS, ACCT_NO;
}