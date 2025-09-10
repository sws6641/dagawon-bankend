package com.bankle.common.asis.domain.repositories;


import com.bankle.common.asis.domain.entity.ConsentDetail;
import com.bankle.common.asis.domain.entity.ids.ConsentDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsentDetailRepository extends JpaRepository<ConsentDetail, ConsentDetailId> {

    List<ConsentDetail> findByConsentDetailIdEntrAsntCdAndConsentDetailIdEntrAsntDtlCd(String entrAsntCd, String entrAsntDtlCd);
}
