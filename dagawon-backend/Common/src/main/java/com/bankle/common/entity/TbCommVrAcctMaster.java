package com.bankle.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_COMM_VR_ACCT_MASTER")
public class TbCommVrAcctMaster {
    @Id
    @Column(name = "VR_ACCT_NO_SEQ", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "VR_ACCT_NO", length = 50)
    private String vrAcctNo;

    @Size(max = 2)
    @Column(name = "VR_ACCT_GB_CD", length = 2)
    private String vrAcctGbCd;

    @Size(max = 1)
    @Column(name = "ASGN_YN", length = 1)
    private String asgnYn;

}