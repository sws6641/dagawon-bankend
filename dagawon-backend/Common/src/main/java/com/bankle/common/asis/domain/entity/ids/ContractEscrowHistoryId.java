package com.bankle.common.asis.domain.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractEscrowHistoryId implements Serializable {
    @Column(name = "ESCR_M_KEY", nullable = false)
    private Long escrMKey;

    @Column(name = "CHG_DTM")
    private LocalDateTime chgDtm;
}
