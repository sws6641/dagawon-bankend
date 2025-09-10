package com.bankle.common.entity.base;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntityByCrtDtmAndAllMembNo {
    @Schema(description = "입력시간", nullable = false, example = "입력시간")
    @Column(updatable = false, name = "CRT_DTM")
    @CreatedDate
    private LocalDateTime crtDtm;

    @Column(name = "CRT_MEMB_NO",updatable = false)
    @CreatedBy
    private String crtMembNo;

    @Column(name = "CHG_MEMB_NO")
    @LastModifiedBy
    private String chgMembNo;
}
