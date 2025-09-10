package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.MembersHistory;
import com.bankle.common.asis.domain.entity.ids.MembersHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberHistoryRepository extends JpaRepository<MembersHistory, MembersHistoryId> {

    @Override
    MembersHistory save(MembersHistory member);

    List<MembersHistoryId> findByUdidOrderByMembersHistoryIdChgDtmDesc(String udid);
}
