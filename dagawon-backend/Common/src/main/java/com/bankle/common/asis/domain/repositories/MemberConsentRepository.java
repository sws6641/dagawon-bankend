package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.MemberConsent;
import com.bankle.common.asis.domain.entity.ids.MemberConsentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberConsentRepository extends JpaRepository<MemberConsent, MemberConsentId> {

}
