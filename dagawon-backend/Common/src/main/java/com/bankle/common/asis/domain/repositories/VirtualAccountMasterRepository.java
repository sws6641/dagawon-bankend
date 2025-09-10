package com.bankle.common.asis.domain.repositories;

import com.bankle.common.asis.domain.entity.VirtualAccountMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VirtualAccountMasterRepository extends JpaRepository<VirtualAccountMaster, String> {

    List<VirtualAccountMaster> findAllByVrAcctDscAndAsgnYn(String vrAcctDsc, String asgnYn);

}
