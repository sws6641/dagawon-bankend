package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.FeeRomDetail;
import com.bankle.common.asis.domain.repositories.FeeRomDetailRepository;
import com.bankle.common.asis.utils.CommonUtils;
import com.bankle.common.util.DateUtil;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeeRomDetailService {
    private final FeeRomDetailRepository feeRomDetailRepository;
    private final ContractEscrowService contractEscrowService;

    /**
     * 수수료결제내역 저장
     *
     * @param body
     * @return
     */
    @Transactional
    public String paid(HashMap<String, String> body) {

        Long escrMKey = StringUtil.stringToLong(body.get("escrMKey"));
        //수수료 입금내역 저장
        feeRomDetailRepository.save(
                FeeRomDetail.builder()
                        .stmtCnclDsc("1")
                        .feeStmtDsc(body.get("feeStmtDsc"))
                        .crdDsc(body.get("crdDsc"))
                        .stmtAmt(StringUtil.stringToLong(body.get("stmtAmt")))
                        .stmtDt(DateUtil.getThisDate("yyyyMMdd"))
                        .escrMKey(escrMKey)
                        .build());

        ContractEscrow escrow = contractEscrowService.getContractEscrow(escrMKey);

        return escrow.getMembNo();
    }

    /**
     * 수수료입금내역정보
     *
     * @param escrMKey
     * @return
     */
    public HashMap<String, String> getFeeDetail(Long escrMKey) throws Exception {

        Optional<FeeRomDetail> byEscrMKey = feeRomDetailRepository.findByEscrMKey(escrMKey);

        if (byEscrMKey.isPresent()) {
            FeeRomDetail feeRomDetail = byEscrMKey.get();
            HashMap<String, String> map = new HashMap<>();

            map.put("crdDscTxt", CommonUtils.getCmnNm("CRD_DSC", feeRomDetail.getCrdDsc()));
            map.put("feeStmtDscTxt", CommonUtils.getCmnNm("FEE_STMT_DSC", feeRomDetail.getFeeStmtDsc()));
            map.put("feeVrAcctNo", feeRomDetail.getVrAcctNo());
            map.put("feeRomBnkCdTxt", CommonUtils.getCmnNm("BNK_CD", feeRomDetail.getRomBnkCd()));
            map.put("feeRomAcctNo", feeRomDetail.getRomAcctNo());
            map.put("feeRommnNm", feeRomDetail.getRommnNm());
            map.put("stmtAmt", String.valueOf(feeRomDetail.getStmtAmt()));
            map.put("stmtDt", feeRomDetail.getStmtDt());
            map.put("stmtCnclDsc", feeRomDetail.getStmtCnclDsc());

            return map;
        } else {
            return null;
        }
    }
}
