package com.bankle.common.asis.domain.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberConsentId implements Serializable {

    @Column(name = "MEMB_ID")
    private String membNo;

    @Column(name = "ENTR_ASNT_CD")
    private String entrAsntCd;

    @Column(name = "ENTR_ASNT_SQN")
    private Long entrAsntSqn;
}
