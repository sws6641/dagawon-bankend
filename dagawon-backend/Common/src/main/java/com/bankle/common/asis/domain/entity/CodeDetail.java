package com.bankle.common.asis.domain.entity;

import com.bankle.common.asis.domain.entity.ids.CodeDetailId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "TEC_CD_D")
@Getter @Setter
@Builder
@ToString(exclude = "codeMaster")
@AllArgsConstructor
@NoArgsConstructor
public class CodeDetail{

    @EmbeddedId
    private CodeDetailId codeDetailId;

    @Column(name = "CMN_CD_NM")
    private String cmnCdNm;

    @Column(name = "RFR_1_CNTS")
    private String rfr1Cnts;

    @Column(name = "RFR_2_CNTS")
    private String rfr2Cnts;

    @Column(name = "RFR_3_CNTS")
    private String rfr3Cnts;

    @Column(name = "SORT_NO")
    private Long sortNo;

    @Column(name = "USE_YN")
    private String useYn;
}
