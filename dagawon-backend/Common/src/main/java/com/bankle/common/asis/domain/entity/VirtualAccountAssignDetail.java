package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "TET_VRACCT_ASGN_D")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VirtualAccountAssignDetail extends BaseTimeEntityByAllDtm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VRACCT_ASGN_D_KEY")
    private Long vracctAsgnDKey;

    @Column(name = "CHRG_DSC")
    private String chrgDsc;

    @Column(name = "VR_ACCT_NO")
    private String vrAcctNo;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;

    @Column(name = "USE_YN")
    private String useYn;
}
