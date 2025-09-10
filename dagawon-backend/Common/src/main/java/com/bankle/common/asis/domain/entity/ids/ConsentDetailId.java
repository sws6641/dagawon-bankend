package com.bankle.common.asis.domain.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ConsentDetailId implements Serializable {
    @Column(name = "ENTR_ASNT_CD", length = 2, nullable = false)
    private String entrAsntCd;

    @Column(name = "ENTR_ASNT_DTL_CD", nullable = false)
    private String entrAsntDtlCd;

    @Column(name = "ENTR_ASNT_SQN", nullable = false)
    private Long entrAsntSqn;
}
