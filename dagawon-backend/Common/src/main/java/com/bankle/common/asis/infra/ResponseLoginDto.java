package com.bankle.common.asis.infra;

import com.bankle.common.asis.domain.entity.Members;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDto {
    private String membNo;
    //    private String pwd;
    private String membNm;
    private String accessToken;
    private String refreshToken;
    private String role;

    public static ResponseLoginDto of(Members member) {
        return ResponseLoginDto.builder()
                .membNo(member.getMembNo())
//                .pwd(member.getPwd())
                .membNm(member.getMembNm())
                .build();
    }
}
