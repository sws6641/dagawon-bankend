package com.bankle.common.repo;

import com.bankle.common.entity.TbMesgSendHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TbMesgSendHistRepository extends JpaRepository<TbMesgSendHist, Long> , JpaSpecificationExecutor<TbMesgSendHist> {
    List<TbMesgSendHist> findByEscrNoAndAddreMembNo(String escrNo, String membNo);

    List<TbMesgSendHist> findByAddreMembNo(String membNo);

    List<TbMesgSendHist> findByEscrNo(String escrNo);
}