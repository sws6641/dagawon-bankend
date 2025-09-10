package com.bankle.common.util.httpapi.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class LogDto {

    private String apiType;
    private String origin;
    private String destination;
    private String apiUrl;
    private String httpMethodCd;
    private String httpStusNo;
    private String classType;
    private String reqHeader;
    private String reqBody;
    private String resHeader;
    private String resBody;
    private String regId;
    private Date regDtm;

}
