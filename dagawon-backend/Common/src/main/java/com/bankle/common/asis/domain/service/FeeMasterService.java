package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.FeeMaster;
import com.bankle.common.asis.domain.repositories.FeeMasterRepository;
import com.bankle.common.asis.domain.repositories.FeeMasterRepositorySupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeeMasterService {
    private final FeeMasterRepository feeMasterRepository;
    private final FeeMasterRepositorySupport feeMasterRepositorySupport;

    /**
     * 계산 정보 조회
     *
     * @param params
     * @return
     */
    private List<FeeMaster> getFeeCalculationInfo(HashMap<String, String> params) {
        String dsc = params.get("prdtTpc") + params.get("prdtDsc");
        return feeMasterRepositorySupport.getFeeCalculationInfo(Long.valueOf(params.get("amt")), dsc);
    }

    /**
     * 수수료계산
     *
     * @param params
     * @return
     */
    public Long calculateFee(HashMap<String, String> params) {
        List<FeeMaster> feeCalculationInfo = getFeeCalculationInfo(params);
        FeeMaster info = feeCalculationInfo.get(0);

        long exceedAmt = Long.valueOf(params.get("amt")) - info.getFrAmt();
        return info.getBscFee() + Double.valueOf(exceedAmt * info.getFeeRt() * 0.01).longValue();
    }

    /**
     * 수수료 비율 조회
     *
     * @param escrTrgtAmt
     * @return
     */
    private BigDecimal getFeeRt(Long escrTrgtAmt) {
        Optional<BigDecimal> feeRt = feeMasterRepository.findFeeRtByEscrTrgtAmt(escrTrgtAmt);
        return (feeRt.isEmpty()) ? new BigDecimal(0.1) : feeRt.get();
    }

    /**
     * 에스크로 수수료금액 조회
     *
     * @param amt 매매대금, 보증금
     * @return
     */
    public Long getFeeAmt(Long amt, String prdtTpc, String prdtDsc) {

        if (amt <= 0L)
            throw new RuntimeException("잘못된 요청입니다.");

        if ("1".equals(prdtTpc))
            return calculateBasicFeeAmt(amt);
        else
            return calculateInsureFeeAmt(amt, prdtDsc);
    }

    /**
     * 기본형 수수료계산
     *
     * @param amt 매매금액 또는 보증금
     * @return 2022-05-26 확정
     */
    private Long calculateBasicFeeAmt(Long amt) {

        Long feeAmt;
        if (amt <= toHundredMillion(1L)) {                               //1억원 이하
            feeAmt = 70000L;
        } else if (amt <= toHundredMillion(3L)) {                         //1억원 초과 3억원 이하
            feeAmt = getFeeAmt(
                    70000L,
                    getExceedAmt(amt, toHundredMillion(1L)),
                    0.014);       //0.014%
        } else if (amt <= toHundredMillion(10L)) {                        //3억원 초과 10억원 이하
            feeAmt = getFeeAmt(
                    98000L,
                    getExceedAmt(amt, toHundredMillion(3L)),
                    0.021);       //0.021%
        } else if (amt <= toHundredMillion(30L)) {                         //10억원 초과 30억원 이하
            feeAmt = getFeeAmt(
                    245000L,
                    getExceedAmt(amt, toHundredMillion(10L)),
                    0.028);      //0.028%
        } else {
            feeAmt = getFeeAmt(
                    805000L,
                    getExceedAmt(amt, toHundredMillion(30L)),
                    0.035);      //0.035%
        }
        return feeAmt;
    }


    /**
     * 보험형 수수료계산
     *
     * @param amt     매매금액 또는 보증금
     * @param prdtDsc 1: 매매, 2,3: 전세, 월세
     * @return 2022-05-26 확정
     */
    private Long calculateInsureFeeAmt(Long amt, String prdtDsc) {

        Long feeAmt;
        if ("1".equals(prdtDsc)) {      //매매
            if (amt <= toHundredMillion(1L)) {                               //1억원 이하
                feeAmt = 168000L;
            } else if (amt <= toHundredMillion(3L)) {                        //1억원 초과 3억원 이하
                feeAmt = getFeeAmt(
                        168000L,
                        getExceedAmt(amt, toHundredMillion(1L)),
                        0.0378);    //0.0378%
            } else if (amt <= toHundredMillion(10L)) {                       //3억원 초과 10억원 이하
                feeAmt = getFeeAmt(
                        243600L,
                        getExceedAmt(amt, toHundredMillion(3L)),
                        0.021);     //0.021%
            } else if (amt <= toHundredMillion(30L)) {                       //10억원 초과 30억원 이하
                feeAmt = getFeeAmt(
                        390600L,
                        getExceedAmt(amt, toHundredMillion(10L)),
                        0.028);     //0.028%
            } else {                                                              //30억원 초과
                feeAmt = getFeeAmt(
                        950600L,
                        getExceedAmt(amt, toHundredMillion(30L)),
                        0.035);     //0.035%
            }
        } else {     //전월세
            if (amt <= toHundredMillion(3L) * 0.1) {                               //3천만원 이하
                feeAmt = 119000L;
            } else if (amt <= toHundredMillion(15L) * 0.1) {                       //3천만원 초과 1.5억원 이하
                feeAmt = getFeeAmt(
                        119000L,
                        getExceedAmt(amt, (long) (toHundredMillion(3L) * 0.1)),
                        0.091);         //0.091%
            } else if (amt <= toHundredMillion(7L)) {                            //1.5억원 초과 7억원 이사
                feeAmt = getFeeAmt(
                        228200L,
                        getExceedAmt(amt, (long) (toHundredMillion(15L) * 0.1)),
                        0.021);         //0.021%
            } else if (amt <= toHundredMillion(15L)) {                            //7억원 초과 15억원 이하
                feeAmt = getFeeAmt(
                        343700L,
                        getExceedAmt(amt, toHundredMillion(7L)),
                        0.028);         //0.028%
            } else {                                                                  //15억원 초과
                feeAmt = getFeeAmt(
                        567700L,
                        getExceedAmt(amt, toHundredMillion(15L)),
                        0.035);         //0.035%
            }
        }

        return feeAmt;
    }

    /**
     * 수수료금액
     *
     * @param baseFeeAmt
     * @param exceedAmt
     * @param rate
     * @return
     */
    private Long getFeeAmt(Long baseFeeAmt, Long exceedAmt, Double rate) {
        return baseFeeAmt + getExceedFeeAmt(exceedAmt, toDoubleRate(rate));
    }

    /**
     * 초과 금액
     *
     * @param amt
     * @param baseAmt
     * @return
     */
    private Long getExceedAmt(Long amt, Long baseAmt) {
        return amt - baseAmt;
    }

    /**
     * 초과 수수료
     *
     * @param exceedAmt
     * @param rate
     * @return
     */
    private Long getExceedFeeAmt(Long exceedAmt, Double rate) {
        return Math.round(exceedAmt * rate);
    }

    /**
     * x원 -> 1억원
     *
     * @param won
     * @return
     */
    private Long toHundredMillion(Long won) {
        return won * 100000000L;
    }

    /**
     * rate% -> rate * 0.01
     *
     * @param rate
     * @return
     */
    private Double toDoubleRate(Double rate) {
        return rate * 0.01;
    }
}
