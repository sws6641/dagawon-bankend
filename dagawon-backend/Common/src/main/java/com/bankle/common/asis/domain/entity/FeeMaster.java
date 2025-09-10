package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@Table(name = "TEC_FEE_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeeMaster extends BaseTimeEntityByAllDtm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FEE_M_KEY")
    private Long feeMKey;

    @Column(name = "FR_AMT")
    private Long frAmt;

    @Column(name = "TO_AMT")
    private Long toAmt;

    @Column(name = "BSC_FEE")
    private Long bscFee;

    @Column(name = "FEE_RT")
    private Double feeRt;
}
