package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "TB_ESCR_FILE")
@ToString
public class TbEscrFile extends BaseTimeEntity {
    @Id
    @Column(name = "SEQ", nullable = false)
    private Long seq;

    @Size(max = 1000)
    @Column(name = "GRP_NO", length = 1000)
    private String grpNo;

    @Size(max = 11)
    @Column(name = "WK_CD", length = 11)
    private String wkCd;

    @Size(max = 2)
    @Column(name = "FILE_CD", length = 2)
    private String fileCd;

    @Size(max = 100)
    @Column(name = "FILE_NM", length = 100)
    private String fileNm;

    @Size(max = 100)
    @Column(name = "FILE_ORGN_NM", length = 100)
    private String fileOrgnNm;

    @Size(max = 5)
    @Column(name = "FILE_EXT", length = 5)
    private String fileExt;

    @Size(max = 1000)
    @Column(name = "FILE_PTH", length = 1000)
    private String filePth;

    @Column(name = "FILE_SIZE", precision = 25)
    private BigDecimal fileSize;

    @Size(max = 12)
    @Column(name = "MEMB_NO", length = 12)
    private String membNo;

    @Size(max = 20)
    @Column(name = "ESCR_NO", length = 20)
    private String escrNo;

    @Size(max = 4000)
    @Column(name = "RMK", length = 4000)
    private String rmk;

    @Size(max = 1)
    @NotNull
    @Column(name = "DEL_YN", nullable = false, length = 1)
    private String delYn;

}