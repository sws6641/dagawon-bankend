package com.bankle.common.comBiz.mesg.vo;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmPayload {
    /* Fcm 랜덤 Key */
    private String seq;

    /* Fcm 클릭시 이동할 페이지 예) "/cntr/list"  */
    private String clickUri;

    /* 대출 번호 */
    private String loanNo;

    /* 기타 내용 */
    private String desc;
}