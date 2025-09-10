package com.bankle.common.asis.domain.entity;

import com.bankle.common.asis.domain.entity.ids.MemberConsentId;
import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "TEC_MEMB_ENTR_ASNT_D")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberConsent extends BaseTimeEntityByAllDtm {

    @EmbeddedId
    private MemberConsentId memberConsentId;

    @Column(name = "ENTR_ASNT_ANS_VAL")
    private String entrAsntAnsVal;
}
