package com.bankle.common.repo;

import com.bankle.common.entity.TbRgstrCaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TbRgstrCaseInfoRepository extends JpaRepository<TbRgstrCaseInfo, Long> {

    List<TbRgstrCaseInfo> findByEscrNo(String escrNo);

    Optional<TbRgstrCaseInfo> findTop1ByEscrNoOrderByRgstrCaseNoDesc(String escrNo);

    void deleteByEscrNo(String escrNo);

    boolean existsByEscrNo(String escrNo);
}