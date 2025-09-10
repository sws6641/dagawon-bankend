package com.bankle.common.dto;

import com.bankle.common.entity.TbBoardMaster;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.bankle.common.entity.TbBoardMaster}
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Value
public class TbBoardMasterDto implements Serializable {
    Long seq;
    @Size(max = 2)
    String ptGbCd;
    @Size(max = 2)
    String ptTrgtGbCd;
    @Size(max = 1)
    String opnYn;
    @Size(max = 8)
    String ptStDt;
    @Size(max = 8)
    String ptEndDt;
    @Size(max = 100)
    String ptTtl;
    @Size(max = 5000)
    String ptCnts;
    int deptNo;
    TbBoardMaster parentSeq;
    @Size(max = 1)
    String delYn;
    Long sort;
    LocalDateTime crtDtm;
    @Size(max = 12)
    String crtMembNo;
    LocalDateTime chgDtm;
    @Size(max = 12)
    String chgMembNo;
    List<TbBoardMasterDto> child;
}