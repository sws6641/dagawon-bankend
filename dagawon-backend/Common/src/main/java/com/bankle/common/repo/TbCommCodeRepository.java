package com.bankle.common.repo;

import com.bankle.common.entity.TbCommCode;
import com.bankle.common.entity.TbCommCodeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbCommCodeRepository extends JpaRepository<TbCommCode, TbCommCodeId> {

    Optional<TbCommCode> findTop1ByIdGrpCdOrderByNumDesc(String grpCd);

    Optional<TbCommCode> findByIdGrpCdAndIdCode(String grpCd, String code);

    List<TbCommCode> findByIdGrpCdIn(List<String> grpCd);

    List<TbCommCode> findByIdGrpCd(String grpCd);
}