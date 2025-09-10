package com.bankle.common.asis.domain.entity.ids;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class MembersHistoryId implements Serializable {

    @Column(name = "MEMB_ID", length = 25, nullable = false)
    private String membNo;

    @Column(name = "CHG_DTM")
    private LocalDateTime chgDtm;
}
