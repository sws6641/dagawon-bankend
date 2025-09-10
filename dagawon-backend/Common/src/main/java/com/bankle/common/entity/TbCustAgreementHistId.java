package com.bankle.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TbCustAgreementHistId implements Serializable {
    private static final long serialVersionUID = 1919576750017130230L;
    @Size(max = 12)
    @NotNull
    @Column(name = "MEMB_NO", nullable = false, length = 12)
    private String membNo;

    @Size(max = 8)
    @NotNull
    @Column(name = "REFORM_DT", nullable = false, length = 8)
    private String reformDt;

    @Size(max = 2)
    @NotNull
    @Column(name = "AGREE_CD", nullable = false, length = 2)
    private String agreeCd;

    @Size(max = 2)
    @NotNull
    @Column(name = "AGREE_DTL_CD", nullable = false, length = 2)
    private String agreeDtlCd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbCustAgreementHistId entity = (TbCustAgreementHistId) o;
        return Objects.equals(this.agreeDtlCd, entity.agreeDtlCd) &&
                Objects.equals(this.agreeCd, entity.agreeCd) &&
                Objects.equals(this.membNo, entity.membNo) &&
                Objects.equals(this.reformDt, entity.reformDt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agreeDtlCd, agreeCd, membNo, reformDt);
    }

}