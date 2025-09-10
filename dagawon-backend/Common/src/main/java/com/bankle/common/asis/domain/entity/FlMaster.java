package com.bankle.common.asis.domain.entity;

import com.bankle.common.entity.base.BaseTimeEntityByAllDtm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TEC_FL_M")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlMaster extends BaseTimeEntityByAllDtm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FL_M_KEY")
    @JsonIgnore
    private Long flMKey;

    @Column(name = "RMK")
    private String rmk;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FL_M_KEY")
    private List<FlDetail> flDetails;
}
