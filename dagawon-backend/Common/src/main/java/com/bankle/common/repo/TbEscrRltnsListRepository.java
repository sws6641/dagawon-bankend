package com.bankle.common.repo;

import com.bankle.common.entity.TbEscrRltnsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface TbEscrRltnsListRepository extends JpaRepository<TbEscrRltnsList, Long> , JpaSpecificationExecutor<TbEscrRltnsList> {
     List<TbEscrRltnsList> findByEscrNo(String escrNo);

     Optional<TbEscrRltnsList> findTop1ByEscrNoAndRltnsGbCd(String escrNo, String rltnsGbCd);

     Optional<TbEscrRltnsList> findTop1ByEscrNoAndRltnsGbCdAndRltnsNm(String escrNo, String number, String rltnsNm);
     Optional<TbEscrRltnsList> findTop1ByEscrNoAndRltnsGbCdAndRltnsNmAndRltnsBirthDt(String escrNo, String number, String rltnsNm,String rltnsBirthDt);

     Optional<TbEscrRltnsList> findByEscrRltnsSeq(String escrRltnsSeq);

    Optional<TbEscrRltnsList> findByEscrRltnsSeq(Long escrRltnsSeq);

    Optional<TbEscrRltnsList> findTop1ByEscrNoAndRltnsGbCdAndTrAgreeYnOrderByTrAgreeDtDesc(String escrNo,String rltnsGbCd,String trAgreeYnw);

    Optional<TbEscrRltnsList> findByKakaoReceiptId(String receiptId);

    Optional<TbEscrRltnsList> findByEscrNoAndRltnsNmAndRltnsBirthDt(String escrNo, String rcvNm, String rcvBirthDt);

    Optional<TbEscrRltnsList> findByEscrNoAndRltnsGbCdAndRltnsAcctNoIsNotNull(String escrNo,String rltnsGbCd);
}