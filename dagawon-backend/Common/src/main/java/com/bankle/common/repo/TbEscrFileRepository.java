package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TbEscrFileRepository extends JpaRepository<TbEscrFile, Long> {

    Optional<TbEscrFile> findTopByWkCdAndFileCdOrderByGrpNoDesc(String wkCd , String fileCd);
}