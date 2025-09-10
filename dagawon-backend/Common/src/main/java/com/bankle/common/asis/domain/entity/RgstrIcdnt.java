package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

/**
 * 연계 등기 사건 내역
 */
@Entity
@Table(name = "TEI_RGSTR_ICDNT_I")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RgstrIcdnt extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RGSTR_ICDNT_I_KEY")
    private Long rgstrIcdntIKey;

    @Column(name = "SRCH_DT")
    private String srchDt;

    @Column(name = "ACPT_DT")
    private String acptDt;

    @Column(name = "ACPT_NO")
    private String acptNo;

    @Column(name = "JURS_REGOF")
    private String jursRegof;

    @Column(name = "REGOF_DPRT")
    private String regofDprt;

    @Column(name = "ALN_ADDR")
    private String alnAddr;

    @Column(name = "RGSTR_OBJ")
    private String rgstrObj;

    @Column(name = "PROC_STT")
    private String procStt;

    @Column(name = "CHG_YN")
    private String chgYn;

    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;
}
