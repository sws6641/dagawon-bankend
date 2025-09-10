package com.bankle.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_TRN_TG_ERROR")
public class TbTrnTgError {
    @Id
    @Size(max = 2)
    @Column(name = "TG_DSC", nullable = false, length = 2)
    private String tgDsc;

    @Size(max = 4)
    @Column(name = "ERR_CD", length = 4)
    private String errCd;

    @Lob
    @Column(name = "ERR_MSG")
    private String errMsg;

}