package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.*;
import com.bankle.common.asis.domain.repositories.ContractEscrowPartyRepository;
import com.bankle.common.asis.domain.repositories.ContractEscrowRomRepository;
import com.bankle.common.asis.domain.repositories.ContractEscrowRomRepositorySupport;
import com.bankle.common.asis.infra.ContractStatusDto;
import com.bankle.common.asis.utils.CommonUtils;
import com.bankle.common.util.DateUtil;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowServiceSupport {

    private final ContractEscrowRomRepository contractEscrowRomRepository;
    private final ContractEscrowPartyRepository contractEscrowPartyRepository;
    private final ContractEscrowRomRepositorySupport contractEscrowRomRepositorySupport;

    private final Long RESEARCH_FEE = 11000L;      //조사비용

    /**
     * 입금상세 세팅
     *
     * @param of
     * @param escrow
     */
    public void setEscrDetail(ContractStatusDto of, ContractEscrow escrow) {

        //입금예정금액 조회
        List<ContractEscrowDetail> contractEscrowDetails = escrow.getContractEscrowDetails();

        contractEscrowDetails
                .forEach(detail -> {
                    try {
                        if (Integer.parseInt(escrow.getEscrPgc()) < Integer.parseInt(escrow.getPrdtTpc())) {
                            //계약등록중
                        } else {
                            if ((of.getEscrPgc() + "1").equals(of.getEscrDtlPgc())) {     //입금요청
                                of.setEscrAmt(detail.getEscrAmt());
                                of.setRomPlnDt(detail.getRomPlnDt());
                            } else {
                                of.setChrgDsc(detail.getChrgDsc());
                                of.setChrgDscValue(CommonUtils.getCmnNm("CHRG_DSC", of.getChrgDsc()));
                                setEscrowRomDetails(of, escrow.getEscrMKey(), detail.getChrgDsc());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    /**
     * 최신 입금내역 설정
     *
     * @param dto
     * @param escrMKey
     * @param chrgDsc
     * @return
     */
    public ContractStatusDto setEscrowRomDetails(ContractStatusDto dto, Long escrMKey, String chrgDsc) {

        if (dto == null) return null;

        //입금내역 조회
        List<ContractEscrowRom> allRom = contractEscrowRomRepository.findAllByescrMKeyOrderByEscrRomDKeyDesc(escrMKey);
        Optional<ContractEscrowRom> first = allRom.stream()
                .filter(rom -> chrgDsc.equals(rom.getChrgDsc()))
                .findFirst();

        if (first.isEmpty()) return null;

        ContractEscrowRom rom = first.get();
        //dto.setRomBnkCd(rom.getRomBnkCd());
        //dto.setRomBnkCdValue(CommonUtils.getCmnNm("BNK_CD", rom.getRomBnkCd()));
        //dto.setRomAcctNo(rom.getRomAcctNo());
        dto.setRomDt(rom.getRomDt());
        dto.setRomAmt(rom.getRomAmt());

        return dto;
    }

    /**
     * 해당 대금구분코드에 해당하는 전체 총입금금액, 입금총예정금액 체크
     *
     * @param vo
     * @param chrgDsc
     * @return
     */
    @Transactional
    public boolean isMatchedAmt(ContractEscrow vo, String chrgDsc) {
        //해당 대금구분코드에 해당하는 전체 총입금금액
        Long sumRomAmt = contractEscrowRomRepositorySupport.getSumRomAmt(vo.getEscrMKey(), chrgDsc);
        sumRomAmt = (sumRomAmt == null) ? 0 : sumRomAmt;

        List<Long> amts = new ArrayList<>();
        List<ContractEscrowDetail> contractEscrowDetails = vo.getContractEscrowDetails();
        contractEscrowDetails.forEach(detail -> {
            if (chrgDsc.equals(detail.getChrgDsc())) {
                amts.add(detail.getEscrAmt());
            }
        });

        long sumEscrAmt = amts.stream().mapToLong(i -> i).sum();
        return sumRomAmt == sumEscrAmt;
    }

    /**
     * 계약 등록 Validation
     *
     * @param body
     * @return
     */
    public Boolean validatated(HashMap<String, Object> body) throws Exception {
        //1. 값이 모두 있는지 체크 - 화면에서
        if (!StringUtils.hasText((String) body.get("prdtTpc")))
            throw new RuntimeException("상품 구분값은 필수값 입니다.");

        if (!StringUtils.hasText((String) body.get("prdtDsc")))
            throw new RuntimeException("거래 구분값은 필수값 입니다.");

        if (!StringUtils.hasText((String) body.get("pstNo")))
            throw new RuntimeException("우편번호는 필수 입력 값 입니다.");

        if (!StringUtils.hasText((String) body.get("escrTrgtAmt")))
            throw new RuntimeException("에스크로 대상 금액이 없습니다. 확인해 주세요.");

        Long escrTrgtAmt = StringUtil.stringToLong((String) body.get("escrTrgtAmt"));

        String prdtTpc = String.valueOf((String) body.get("prdtTpc"));
        String prdtDsc = String.valueOf((String) body.get("prdtDsc"));

        //2. 날짜 체크 계약금, 중도금 1, 중도금 2, 잔금 날짜 체크
        List<HashMap<String, Object>> maps = new ArrayList<>();
        List<HashMap<String, String>> details = (List<HashMap<String, String>>) body.get("detailDto");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //2-1. chrgDsc로 오름차순 변경
        for (HashMap<String, String> detail : details) {
            if (!StringUtils.hasText(detail.get("romPlnDt")))
                throw new RuntimeException("입금예정일은 필수값 입니다.");

            if (!StringUtils.hasText(detail.get("chrgDsc")))
                throw new RuntimeException("필수값을 확인해 주세요.");

            String dateStr = (detail.get("romPlnDt").replaceAll("-", ""));

            HashMap<String, Object> map = new HashMap<>();
            map.put("chrgDsc", detail.get("chrgDsc"));
            map.put("romPlnDt", LocalDate.parse(dateStr, formatter));
            maps.add(map);
        }

        Collections.sort(maps, Comparator.comparing(o -> (String.valueOf(o.get("chrgDsc")))));

        //2-2. 계약금입금예정일자 <= 중도금입금예정일자 <= 잔금입금예정일자 체크
        LocalDate lastRomPlnDt = null;
        LocalDate tmp = null;
        if (maps.size() > 1) {
            for (int i = 0; i < maps.size(); i++) {

                if (i == 0) {
                    tmp = (LocalDate) maps.get(0).get("romPlnDt");
                    i++;
                }
                if (tmp.compareTo((LocalDate) maps.get(i).get("romPlnDt")) > 0)
                    throw new RuntimeException("날짜를 확인하세요.");

                tmp = (LocalDate) maps.get(i).get("romPlnDt");

                if (i == maps.size() - 1) {
                    lastRomPlnDt = (LocalDate) maps.get(i).get("romPlnDt");
                }
            }
        } else {
            lastRomPlnDt = (LocalDate) maps.get(0).get("romPlnDt");
        }

        //3. 기본형인 경우, 마지막 금액입금예정일자 <= 지급예정일자
        if ("1".equals(prdtTpc)) {
            if (StringUtils.hasText((String) body.get("pmntPlnDt")))
                throw new RuntimeException("지급예정일을 확인해 주세요.");

            String pmntPlnDt = String.valueOf((String) body.get("pmntPlnDt")).replaceAll("-", "");
            if (lastRomPlnDt.compareTo(LocalDate.parse(pmntPlnDt, formatter)) > 0)
                throw new RuntimeException("지급예정일을 확인해 주세요.");

            if ("1".equals(prdtDsc)) {     //매매
                if (StringUtils.hasText((String) body.get("slAmt")))
                    throw new RuntimeException("매매대금은 필수 입력값 입니다.");

                Long slAmt = StringUtil.stringToLong((String) body.get("slAmt"));
                if (escrTrgtAmt > slAmt)
                    throw new RuntimeException("매매대금보다 에스크로대상 금액이 더 큽니다. 금액을 확인하세요.");


            } else {           //전월세
                if (!StringUtils.hasText((String) body.get("grntAmt")))
                    throw new RuntimeException("보증금은 필수 입력값 입니다.");

                Long grntAmt = StringUtil.stringToLong((String) body.get("grntAmt"));
                if (escrTrgtAmt > grntAmt)
                    throw new RuntimeException("보증금보다 에스크로대상 금액이 더 큽니다. 금액을 확인하세요.");
            }

        } else {
            //보험형인 경우,
            if (!StringUtils.hasText((String) body.get("cntrtDrwupDt")))
                throw new RuntimeException("계약일은 필수 입력값 입니다.");

            if (!StringUtils.hasText((String) body.get("lsStDt")))
                throw new RuntimeException("입주일은 필수 입력값 입니다.");

            if ("1".equals(prdtDsc)) {     //매매
                if (!StringUtils.hasText((String) body.get("slAmt")))
                    throw new RuntimeException("매매대금은 필수 입력값 입니다.");

                Long slAmt = StringUtil.stringToLong((String) body.get("slAmt"));
                if (escrTrgtAmt > slAmt)
                    throw new RuntimeException("매매대금보다 에스크로대상 금액이 더 큽니다. 금액을 확인하세요.");


            } else {           //전월세
                if (!StringUtils.hasText((String) body.get("grntAmt")))
                    throw new RuntimeException("보증금은 필수 입력값 입니다.");

                Long grntAmt = StringUtil.stringToLong((String) body.get("grntAmt"));
                if (escrTrgtAmt > grntAmt)
                    throw new RuntimeException("보증금보다 에스크로대상 금액이 더 큽니다. 금액을 확인하세요.");

                if (!StringUtils.hasText((String) body.get("lsEndDt")))
                    throw new RuntimeException("퇴거일은 필수 입력값 입니다.");

                if ("3".equals(prdtDsc)) {  //월세
                    if (!StringUtils.hasText((String) body.get("rntAmt")))
                        throw new RuntimeException("차임금액은 필수 입력값 입니다.");
                }
            }
        }

        return true;
    }

    /**
     * 회원의 이해관계자 관계자 M키 조회
     *
     * @param escrMKey
     * @param hpNo
     * @return
     */
    public Long getMembPrtyDsc(Long escrMKey, String hpNo) {
        ContractEscrowParty byEscrMKeyAndPrtyHpNo = contractEscrowPartyRepository
                .findByEscrMKeyAndPrtyHpNo(escrMKey, hpNo);
        return byEscrMKeyAndPrtyHpNo.getEscrPrtyDKey();
    }

    /**
     * 입금금액 조회
     *
     * @param escrMKey
     * @param chrgDsc
     * @return
     */
    public Long getSumRomAmt(Long escrMKey, String chrgDsc) {
        Long sumRomAmt = contractEscrowRomRepositorySupport.getSumRomAmt(escrMKey, chrgDsc);
        log.info("sumRomAmt >> " + sumRomAmt);
        return (sumRomAmt == null) ? 0 : sumRomAmt;
    }

    /**
     * 환불 수수료 조회
     *
     * @param feeAmt
     * @param prdtTpc
     * @param escrPgc
     * @return
     */
    public Long getRefundFeeAmt(Long feeAmt, String prdtTpc, String escrPgc) {
        //기본형인 경우
        if ("1".equals(prdtTpc )) {
            feeAmt = ("0".equals(escrPgc))
                    ? feeAmt
                    //에스크로계좌 이용 후 환불 불가
                    : Long.valueOf(0);
        } else {

            feeAmt = ("0".equals(escrPgc))
                    ? feeAmt
                    //권리조사시작 : 권리조사비용 제외
                    : (("1".equals(escrPgc))
                    ? feeAmt - RESEARCH_FEE
                    //에스크로 계좌 이용 후 환불 불가
                    : 0L);
        }

        return feeAmt;
    }

    /**
     * 지급정보데이트 조회
     *
     * @param refundAmt
     * @param escrow
     * @param map
     * @return
     */
    public HashMap<String, String> getPmntData(Long refundAmt, Long refundFeeAmt, ContractEscrow escrow, HashMap<String, String> map) {
        HashMap<String, String> returnMap = new HashMap<>();
        returnMap.put("escrMKey", String.valueOf(escrow.getEscrMKey()));
        returnMap.put("refundAmt", String.valueOf(refundAmt));
        returnMap.put("refundFeeAmt", String.valueOf(refundFeeAmt));
        returnMap.put("pmntDt", DateUtil.getThisDate("yyyyMMdd"));
        returnMap.put("pmntBnkCd", map.get("pmntBnkCd"));
        returnMap.put("pmntAcctNo", map.get("pmntAcctNo"));

        return returnMap;
    }

    /**
     * 동의한 인원보다 작게 설정하면 오류
     *
     * @param body
     * @param contractEscrowParties
     * @return
     */
    public boolean isValid(HashMap<String, String> body, List<ContractEscrowParty> contractEscrowParties) {

        int tobeSldlvLsNop = Integer.parseInt((String) body.get("sldlvLsNop"));  // 매도인
        int tobePchsHreNop = Integer.parseInt((String) body.get("pchsHreNop"));  // 매수인

        AtomicInteger sldlvLsNop = new AtomicInteger();
        AtomicInteger pchsHreNop = new AtomicInteger();


        contractEscrowParties.forEach(p -> {
            if ("1".equals(p.getPrtyDsc())) { //매수인
                pchsHreNop.getAndIncrement();
            } else {
                sldlvLsNop.getAndIncrement();
            }
        });

        return tobeSldlvLsNop >= sldlvLsNop.get() && tobePchsHreNop >= pchsHreNop.get();

    }

    /**
     * 거래 에스크로 관계자 상세
     *
     * @param member
     * @return
     * @throws Exception
     */
    public List<ContractEscrowParty> getEscrowParties(Members member) throws Exception {
        List<ContractEscrowParty> contractEscrowPartyList = new ArrayList<>();
        //이해관계자 저장
        contractEscrowPartyList.add(ContractEscrowParty.builder()
                .prtyDsc("1")
                .prtyNm(member.getMembNm())
                .prtyHpNo(member.getHpNo())
                .prtyBirthDt(member.getBirthDt())
                .prtySex(member.getSex())
                .prtyEmail(member.getEmail())
                .trAsntDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .trAsntYn(member.getMktNotiYn())
                .build());

        return contractEscrowPartyList;

    }

    /**
     * 계약상세
     *
     * @param body
     * @return
     */
    public List<ContractEscrowDetail> getEscrowDetails(HashMap<String, Object> body) {
        List<HashMap<String, Object>> details = (List<HashMap<String, Object>>) body.get("detailDto");

        //거래 에스크로 상세 VO 리스트
        List<ContractEscrowDetail> contractEscrowDetailList = new ArrayList<>();
        details.forEach(detail -> {
            ContractEscrowDetail build = ContractEscrowDetail.builder()
                    .chrgDsc((String) detail.get("chrgDsc"))
                    .escrAmt(StringUtil.stringToLong(String.valueOf(detail.get("escrAmt"))))
                    .romPlnDt(((String) detail.get("romPlnDt")).replaceAll("-", ""))
                    .build();
            //계약상세 저장
            contractEscrowDetailList.add(build);
        });


        return contractEscrowDetailList;
    }

    /**
     * 계약상세
     *
     * @param body
     * @return
     */
    public List<ContractEscrowDetail> updateDetail(Long escrMKey, HashMap<String, Object> body) {
        List<HashMap<String, Object>> details = (List<HashMap<String, Object>>) body.get("detailDto");

        //거래 에스크로 상세 VO 리스트
        List<ContractEscrowDetail> contractEscrowDetailList = new ArrayList<>();

        details.forEach(detail -> {

            Long escrDKey = StringUtil.mapToStringL(detail, "escrDKey");

            if (escrDKey == 0) {
                log.debug("====>> detail Insert  escrDKey [" + escrDKey + "]   escrMKey [" + escrMKey + "]");

                log.debug("====>> detail Insert  chrgDsc [" + (String) detail.get("chrgDsc") + "]");
                log.debug("====>> detail Insert  escrAmt [" + StringUtil.stringToLong(String.valueOf(detail.get("escrAmt"))) + "]");
                log.debug("====>> detail Insert  romPlnDt [" + ((String) detail.get("romPlnDt")).replaceAll("-", "") + "]");

                ContractEscrowDetail build = ContractEscrowDetail.builder()
                        .chrgDsc((String) detail.get("chrgDsc"))
                        .escrAmt(StringUtil.stringToLong(String.valueOf(detail.get("escrAmt"))))
                        .romPlnDt(((String) detail.get("romPlnDt")).replaceAll("-", ""))
                        .build();
                //계약상세 저장
                contractEscrowDetailList.add(build);
            } else {
                log.debug("====>> detail Update  escrDKey [" + escrDKey + "]   escrMKey [" + escrMKey + "]");

                ContractEscrowDetail build = ContractEscrowDetail.builder()
                        .escrDKey(escrDKey)
                        .escrMKey(escrMKey)
                        .chrgDsc((String) detail.get("chrgDsc"))
                        .escrAmt(StringUtil.stringToLong(String.valueOf(detail.get("escrAmt"))))
                        .romPlnDt(((String) detail.get("romPlnDt")).replaceAll("-", ""))
                        .build();

                //계약상세 저장
                contractEscrowDetailList.add(build);
            }
        });


        return contractEscrowDetailList;
    }

}
