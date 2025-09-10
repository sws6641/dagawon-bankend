package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@DynamicInsert @DynamicUpdate
@Entity
@Table(name = "ES1MC0901D")
@NoArgsConstructor
@ToString
@Getter
public class Code extends BaseTimeEntityByAllDtm implements Serializable {

    @Id
    private String cdDvsnCd;
    private String commCd;
    private String cdPhscNm;
    private String commCdNm;
    private String useYn;
    private String hrnkCdDvsnCd;
    private String hrnkCommCd;

    private String crtMembNo;
    private String chgMembNo;

    private int orderNum;

    @Builder
    public Code(String cdDvsnCd, String commCd, String commCdNm, String hrnkCdDvsnCd, String hrnkCommCd){
        this.cdDvsnCd = cdDvsnCd;
        this.commCd = commCd;
        this.commCdNm = commCdNm;
        this.hrnkCdDvsnCd = hrnkCdDvsnCd;
        this.hrnkCommCd = hrnkCommCd;
    }

}
