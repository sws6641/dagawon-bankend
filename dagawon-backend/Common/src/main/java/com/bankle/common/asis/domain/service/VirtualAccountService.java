package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.VirtualAccountAssignDetail;
import com.bankle.common.asis.domain.repositories.VirtualAccountAssignDetailRepository;
import com.bankle.common.asis.domain.repositories.VirtualAccountMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/fee")
public class VirtualAccountService {

    private final VirtualAccountMasterRepository virtualAccountMasterRepository;
    private final VirtualAccountAssignDetailRepository virtualAccountAssignDetailRepository;

    /**
     * 가상계좌 할당
     * @param escrMKey
     * @param vrAcctDsc
     * @return
     */
//    @Transactional
//    public Long assignVirtualAccountNo(Long escrMKey, String vrAcctDsc, String chrgDsc){
//        List<VirtualAccountMaster> tmp = virtualAccountMasterRepository.findAllByVrAcctDscAndAsgnYn(vrAcctDsc, "N");
//
//        if(tmp.size() == 0){
//            log.warn("할당 할 수 있는 계좌가 없습니다.");
//            return null;
//        }else{
//            //공통 가상 계좌 기본 저장
//            VirtualAccountMaster virtualAccountMaster = tmp.stream().findFirst().get();
//            virtualAccountMaster.setAsgnYn("Y");
//            VirtualAccountMaster save = virtualAccountMasterRepository.save(virtualAccountMaster);
//
//
//            VirtualAccountAssignDetail virtualAccountAssignDetail = virtualAccountAssignDetailRepository.save(
//                    VirtualAccountAssignDetail.builder()
//                            .chrgDsc(chrgDsc)
//                            .vrAcctNo(save.getVrAcctNo())
//                            .escrMKey(escrMKey)
//                            .useYn("Y")
//                            .build()
//            );
//
//            //TODO 입금내역 SMS/PUSH 이벤트
//
//            return virtualAccountAssignDetail.getVracctAsgnDKey();
//        }
//
//    }

    /**
     * 가상계좌 할당 해제
     * @param escrMKey
     * @return
     */
//    @Transactional
//    public boolean unassignAccountNo(Long escrMKey){
//
//        Optional<VirtualAccountAssignDetail> tmp =
//                virtualAccountAssignDetailRepository.findByescrMKeyAndUseYn(escrMKey, "Y");
//
//        if(tmp.isEmpty())
//            return false;
//        else{
//            VirtualAccountAssignDetail assigned = tmp.get();
//            assigned.setUseYn("N");
//            VirtualAccountAssignDetail save = virtualAccountAssignDetailRepository.save(assigned);
//
//            Optional<VirtualAccountMaster> byId = virtualAccountMasterRepository.findById(save.getVrAcctNo());
//            if(byId.isEmpty())
//                return false;
//            else{
//                VirtualAccountMaster accountMaster = byId.get();
//                accountMaster.setAsgnYn("N");
//                virtualAccountMasterRepository.save(accountMaster);
//
//                return true;
//            }
//        }
//    }

    /**
     * 할당된 가상계좌번호
     *
     * @param escrMKey
     * @return
     */
    public String getAssignedVirtualAccount(Long escrMKey) {
        if (escrMKey == null)
            throw new RuntimeException("잘못된 요청입니다.");

        VirtualAccountAssignDetail vo = virtualAccountAssignDetailRepository
                .findByescrMKeyAndUseYn(escrMKey, "N").orElse(null);

        return (vo == null) ? "" : vo.getVrAcctNo();
    }
}
