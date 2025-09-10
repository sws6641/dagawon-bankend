package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.CodeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodeMasterRepository extends JpaRepository<CodeMaster, String> {

    Optional<CodeMaster> findByGrpCdAndUseYn(String grpCd, String useYn);

    List<CodeMaster> findAllByUseYn(String useYn);
}
