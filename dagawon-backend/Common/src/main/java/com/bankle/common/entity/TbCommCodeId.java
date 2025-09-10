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
public class TbCommCodeId implements Serializable {
    private static final long serialVersionUID = 3026011321441836869L;
    @Size(max = 20)
    @NotNull
    @Column(name = "GRP_CD", nullable = false, length = 20)
    private String grpCd;

    @Size(max = 10)
    @NotNull
    @Column(name = "CODE", nullable = false, length = 10)
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TbCommCodeId entity = (TbCommCodeId) o;
        return Objects.equals(this.code, entity.code) &&
                Objects.equals(this.grpCd, entity.grpCd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, grpCd);
    }

}