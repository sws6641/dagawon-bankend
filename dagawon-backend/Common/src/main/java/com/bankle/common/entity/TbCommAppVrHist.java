package com.bankle.common.entity;

import com.bankle.common.entity.base.BaseTimeEntityByCrtDtmAndMembNo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_COMM_APP_VR_HIST")
public class TbCommAppVrHist extends BaseTimeEntityByCrtDtmAndMembNo {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VR_SEQ", nullable = false)
    private Long vrSeq;

    @Size(max = 100)
    @Column(name = "APP_VR", length = 100)
    private String appVr;

    @Lob
    @Column(name = "CONTENT")
    private String content;

}