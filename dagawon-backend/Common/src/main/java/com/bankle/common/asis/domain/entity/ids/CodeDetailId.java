package com.bankle.common.asis.domain.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CodeDetailId implements Serializable {

    @Column(name = "GRP_CD")
    private String grpCd;

    @Column(name = "CMN_CD")
    private String cmnCd;
}
