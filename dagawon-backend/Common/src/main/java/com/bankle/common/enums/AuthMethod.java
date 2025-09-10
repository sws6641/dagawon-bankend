package com.bankle.common.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AuthMethod {
    PIN("PIN"),
    BIO("BIO"),

    EMPTY("");

    private String authMethod;

    AuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public static AuthMethod findByCode(String code) {
        return Arrays.stream(AuthMethod.values())
                .filter(authMethod -> authMethod.getAuthMethod().equals(code.toUpperCase()))
                .findFirst()
                .orElse(EMPTY);
    }
}