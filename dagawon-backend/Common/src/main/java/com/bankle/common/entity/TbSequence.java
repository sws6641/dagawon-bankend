package com.bankle.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "TB_SEQUENCE")
public class TbSequence {
    @Id
    @Column(name = "seq_date", nullable = false)
    private LocalDate id;

    @Size(max = 100)
    @NotNull
    @Column(name = "seq_name", nullable = false, length = 100)
    private String seqName;

    @NotNull
    @Column(name = "seq_number", nullable = false)
    private Integer seqNumber;
}