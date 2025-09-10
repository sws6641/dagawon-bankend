package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TB_RGSTR_ISN_HIST")
@ToString
public class TbRgstrIsnHist extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RGSTR_ISN_SEQ", nullable = false)
    private Long rgstrIsnSeq;

    @Size(max = 20)
    @NotNull
    @Column(name = "RGSTR_UNQ_NO", nullable = false, length = 20)
    private String rgstrUnqNo;

    @NotNull
    @Column(name = "READ_DTM", nullable = false)
    private LocalDateTime readDtm;

    @NotNull
    @Column(name = "ISSU_ID_NUM", nullable = false)
    private Integer issuIdNum;

    @Lob
    @Column(name = "CONTENT")
    private String content;

}