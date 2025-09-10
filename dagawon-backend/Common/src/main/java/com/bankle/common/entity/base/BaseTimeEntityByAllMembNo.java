package com.bankle.common.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntityByAllMembNo {
    @Column(name = "CRT_MEMB_NO",updatable = false)
    @CreatedBy
    private String crtMembNo;

    @Column(name = "CHG_MEMB_NO")
    @LastModifiedBy
    private String chgMembNo;
}
