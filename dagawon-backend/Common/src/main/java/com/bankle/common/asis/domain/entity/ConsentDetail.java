package com.bankle.common.asis.domain.entity;

import com.bankle.common.asis.domain.entity.ids.ConsentDetailId;
import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "TEC_ENTR_ASNT_D")
@Getter
@Setter
@Builder
@ToString(exclude = "consentMaster")
@AllArgsConstructor
@NoArgsConstructor
public class ConsentDetail extends BaseTimeEntityByAllDtm implements Comparable<ConsentDetail> {

    @EmbeddedId
    private ConsentDetailId consentDetailId;

    @Transient
    private String entrAsntNm;

    @Column(name = "ENTR_ASNT_CNTS", length = 4000)
    private String entrAsntCnts;

    @Override
    public int compareTo(ConsentDetail o) {
        return this.getConsentDetailId().getEntrAsntSqn().compareTo(o.getConsentDetailId().getEntrAsntSqn());
    }
}
