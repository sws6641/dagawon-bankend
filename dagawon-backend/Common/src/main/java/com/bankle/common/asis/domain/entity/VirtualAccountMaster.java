package com.bankle.common.asis.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "TEC_VR_ACCT_M")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VirtualAccountMaster {

    @Id
    @Column(name = "VR_ACCT_NO")
    private String vrAcctNo;

    @Column(name = "VR_ACCT_DSC")
    private String vrAcctDsc;

    @Column(name = "ASGN_YN")
    private String asgnYn;
}
