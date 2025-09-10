package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtm;
import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(name = "TB_CUST_LOGN_HIST")
@EntityListeners(AuditingEntityListener.class)
public class TbCustLoginHist extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
    @Column(name = "SEQ", nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Size(max = 12)
    @NotNull
    @Column(name = "MEMB_NO", nullable = false, length = 12)
    private String membNo;

    @Size(max = 2)
    @NotNull
    @Column(name = "MEMB_GB_CD", nullable = false, length = 2)
    private String membGbCd;

    @Size(max = 50)
    @NotNull
    @Column(name = "PIN_NO", nullable = false, length = 50)
    private String pinNo;

    @Size(max = 500)
    @NotNull
    @Column(name = "ACS_BRW", nullable = false, length = 500)
    private String acsBrw;

    @Size(max = 500)
    @NotNull
    @Column(name = "ACS_DVCE", nullable = false, length = 500)
    private String acsDvce;

    @Size(max = 100)
    @NotNull
    @Column(name = "ACS_IP_ADDR", nullable = false, length = 100)
    private String acsIpAddr;

}