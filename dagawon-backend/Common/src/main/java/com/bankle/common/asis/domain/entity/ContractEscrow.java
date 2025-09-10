package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 거래 에스크로 기본
 */
@Entity
@Getter
@Setter
@Table(name = "TET_ESCR_M")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractEscrow extends BaseTimeEntityByAllDtm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESCR_M_KEY")
    private Long escrMKey;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "PRDT_TPC")
    private String prdtTpc;

    @Column(name = "PRDT_DSC")
    private String prdtDsc;

    @Column(name = "PRDT_DTL_DSC")
    private String prdtDtlDsc;

    @Column(name = "PST_NO")
    private String pstNo;

    @Column(name = "ALN_ADDR1")
    private String alnAddr1;

    @Column(name = "ALN_ADDR2")
    private String alnAddr2;

    @Column(name = "RD_ADDR1")
    private String rdAddr1;

    @Column(name = "RD_ADDR2")
    private String rdAddr2;

    @Column(name = "RGSTR_UNQ_NO")
    private String rgstrUnqNo;

    @Column(name = "ESCR_PGC")
    private String escrPgc;

    @Column(name = "ESCR_DTL_PGC")
    private String escrDtlPgc;

    @Column(name = "CNTRT_DRWUP_DT")
    private String cntrtDrwupDt;

    @Column(name = "FEE_PAY_YN")
    private String feePayYn;

    @Column(name = "FEE_AMT")
    private Long feeAmt;

    @Column(name = "FEE_RFND_AMT")
    private Long feeRfndAmt;

    @Column(name = "ISRN_ENTR_YN")
    private String isrnEntrYn;

    @Column(name = "ISRN_SCRT_NO")
    private String isrnScrtNo;

    @Column(name = "ISRN_PRMM")
    private Double isrnPrmm;

    @Column(name = "ISRN_ROM_SQN")
    private String isrnRomSqn;

    @Column(name = "ISRN_PRMM_ROM_YN")
    private String isrnPrmmRomYn;

    @Column(name = "SL_AMT")
    private Long slAmt;

    @Column(name = "BND_MAX_AMT")
    private Long bndMaxAmt;

    @Column(name = "GRNT_AMT")
    private Long grntAmt;

    @Column(name = "RNT_AMT")
    private Long rntAmt;

    @Column(name = "SLDLV_LS_NOP")
    private Integer sldlvLsNop;

    @Column(name = "PCHS_HRE_NOP")
    private Integer pchsHreNop;

    @Column(name = "ESCR_TRGT_AMT")
    private Long escrTrgtAmt;

    @Column(name = "ESCR_ROM_AMT")
    private Long escrRomAmt;

    @Column(name = "ESCR_PMNT_AMT")
    private Long escrPmntAmt;

    @Column(name = "ESCR_BLNC_AMT")
    private Long escrBlncAmt;

    @Column(name = "PMNT_BNK_CD")
    private String pmntBnkCd;

    @Column(name = "PMNT_ACCT_NO")
    private String pmntAcctNo;

    @Column(name = "PMNT_PLN_DT")
    private String pmntPlnDt;

    @Column(name = "PMNT_APRV_DT")
    private String pmntAprvDt;

    @Column(name = "EXE_DT")
    private String exeDt;

    @Column(name = "LS_ST_DT")      //임대시작일자 = 잔급지급일자
    private String lsStDt;

    @Column(name = "LS_END_DT")
    private String lsEndDt;

    @Column(name = "LS_FIX_DT")
    private String lsFixDt;

    @Column(name = "CNTRT_CNCL_DT")
    private String cntrtCnclDt;

    @Column(name = "CNTRT_CNCL_RSN_CD")
    private String cntrtCnclRsnCd;

    @Column(name = "CNTRT_CNCL_RSN_CNTS")
    private String cntrtCnclRsnCnts;

    @Column(name = "RGST_RD_CNTS")
    private String rgstRdCnts;

    @Column(name = "RGST_RD_DTM")
    private String rgstRdDtm;

    @Column(name = "CNFMTN_URL")
    private String cnfmtnUrl;

    @Column(name = "PRDT_MNL_URL")
    private String prdtMnlUrl;

    @Column(name = "ISRN_SCRT_URL")
    private String isrnScrtUrl;

    @Column(name = "RMK_FCT")
    private String rmkFct;

    @Column(name = "MEMB_ID")
    private String membNo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy(value = "chrgDsc asc")
    @JoinColumn(name = "ESCR_M_KEY")
    private List<ContractEscrowDetail> contractEscrowDetails;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ESCR_M_KEY")
    private List<ContractEscrowParty> contractEscrowParties;

}
