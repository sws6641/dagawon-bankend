package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.ContractEscrowHIstory;
import com.bankle.common.asis.domain.entity.ContractEscrowParty;
import com.bankle.common.asis.domain.entity.Members;
import com.bankle.common.asis.domain.mapper.ContractEscrowMapper;
import com.bankle.common.asis.domain.mapper.EscrCancelMapper;
import com.bankle.common.asis.domain.mapper.IfTgInfoMapper;
import com.bankle.common.asis.domain.repositories.ContractEscrowHistoryRepository;
import com.bankle.common.asis.domain.repositories.ContractEscrowRepository;
import com.bankle.common.asis.domain.service.extnLk.LguFirmBankingService;
import com.bankle.common.asis.infra.ContractEscrowDetailDto;
import com.bankle.common.asis.infra.ContractEscrowInfoDto;
import com.bankle.common.asis.infra.ContractEscrowReportDto;
import com.bankle.common.asis.infra.ContractStatusDto;
import com.bankle.common.asis.utils.CommonUtils;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import com.bankle.common.util.DateUtil;
import com.bankle.common.util.NumberUtil;
import com.bankle.common.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowService {

    private final ContractEscrowRepository contractEscrowRepository;
    private final ContractEscrowHistoryRepository contractEscrowHistoryRepository;
    private final ContractEscrowServiceSupport contractEscrowServiceSupport;
    private final MemberService memberService;
    private final FeeMasterService feeMaterService;
    private final VirtualAccountService virtualAccountService;
    private final ContractEscrowMapper contractEscrowMapper;
    private final ApplicationEventPublisher publisher;

    private final EscrCancelMapper escrCancelMapper;
    private final IfTgInfoMapper ifTgInfoMapper;
    private final LguFirmBankingService LFBService;
    private final FeeService feeService;
    private final EscrSendMsgUtils escrSendMsg;
    private final ContractEscrowMapper escrMapper;

    /**
     * 계약상태 및 카드정보 표시
     *
     * @param membId
     * @return
     */
    @Transactional
    public List<ContractStatusDto> getCards(String membId) throws Exception {

        if (!StringUtils.hasText(membId))
            throw new RuntimeException("membId는 필수값 입니다.");

        //계약리스트 조회
        //List<ContractEscrow> allByMembId = contractEscrowRepository.findAllByMembId(membId);
        List<ContractEscrow> allByMembId = contractEscrowRepository.findAllByMembNoOrderByEscrMKeyDesc(membId);

        if (allByMembId.size() == 0)
            return null;

        //회원정보 조회
        Members memberByMembId = memberService.getMemberByMembId(membId);

        //리턴 DTO 생성
        List<ContractStatusDto> dtos = new ArrayList<>();

        //계약리스트가 없는 경우,
        if (allByMembId.size() == 0) {
            ContractStatusDto contractStatusDto = new ContractStatusDto();
            contractStatusDto.setMembNo(membId);
            contractStatusDto.setMembNm(memberByMembId.getMembNm());
            dtos.add(contractStatusDto);
        } else {
            allByMembId.forEach(contractEscrow -> {

                try {
                    // 수수료 납입전 취소 계약건은 조회하지 않는다.
                    if (!"81".equals(contractEscrow.getEscrDtlPgc())) {
                        ContractStatusDto of = ContractStatusDto.of(contractEscrow);
                        of.setMembNm(memberByMembId.getMembNm());
                        of.setMembPrtyDsc(
                                String.valueOf(contractEscrowServiceSupport
                                        .getMembPrtyDsc(contractEscrow.getEscrMKey(), memberByMembId.getHpNo()))
                        );
                        of.setPrdtDscValue(CommonUtils.getCmnNm("PRDT_DSC", of.getPrdtDsc()));
                        of.setPrdtTpcValue(CommonUtils.getCmnNm("PRDT_TPC", of.getPrdtTpc()));
                        of.setEscrPgcValue(CommonUtils.getCmnNm("ESCR_PGC_" + contractEscrow.getPrdtTpc(), of.getEscrPgc()));
                        of.setEscrDtlPgcValue(CommonUtils.getCmnNm("ESCR_DTL_PGC_" + contractEscrow.getPrdtTpc(), of.getEscrDtlPgc()));
                        //contractEscrow.getesc

                        //현재 진행단계의 대금상세내역 저장
                        contractEscrowServiceSupport.setEscrDetail(of, contractEscrow);

                        of.setRomPlnDt(contractEscrowMapper.getRomPlnDt(contractEscrow.getEscrMKey() + ""));
                        dtos.add(of);
                    }

                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            });
        }

        return dtos;
    }

    /**
     * 에스크로 조회 by escrMKey
     *
     * @param escrMKey
     * @return
     */
    @Transactional
    public ContractEscrow getContractEscrow(Long escrMKey) {
        if (escrMKey == null)
            throw new RuntimeException("escrMKey cannot be null!!");
        return contractEscrowRepository.findById(escrMKey).orElse(null);
    }

    @Transactional
    public ContractEscrow getContractEscrow(String escrMKey) {
        if (!StringUtils.hasText(escrMKey))
            throw new RuntimeException("escrMKey cannot be null!!");
        return contractEscrowRepository.findById(Long.valueOf(escrMKey)).orElse(null);
    }

    /**
     * 계약정보상세 조회
     *
     * @param escrMKey
     * @return
     */
    @Transactional
    public ContractEscrowInfoDto getDetails(Long escrMKey) throws Exception {
        //에스크로 기본 조회
        ContractEscrow contractEscrow = getContractEscrow(escrMKey);
        if (contractEscrow == null)
            throw new RuntimeException("잘못된 요청입니다.");

        //ContractEscrow -> ContractEscrowDetailDto 변경
        ContractEscrowInfoDto dto = ContractEscrowInfoDto.of(contractEscrow);
        dto.setPrdtDscValue(CommonUtils.getCmnNm("PRDT_DSC", dto.getPrdtDsc()));
        dto.setPrdtTpcValue(CommonUtils.getCmnNm("PRDT_TPC", dto.getPrdtTpc()));

        //회원명 설정
        Members memberByMembId = memberService.getMemberByMembId(contractEscrow.getMembNo());
        dto.setMembNm(memberByMembId.getMembNm());
        dto.setMembBirth(memberByMembId.getBirthDt());

        //가상계좌번호
        dto.setVrActNo(virtualAccountService.getAssignedVirtualAccount(contractEscrow.getEscrMKey()));
        dto.setPmntBnkNoTxt(CommonUtils.getCmnNm("BNK_CD", dto.getPmntBnkNo()));

        //JPA LAZY LOADING 해결
        List<ContractEscrowParty> contractEscrowParties = contractEscrow.getContractEscrowParties();
        int size = contractEscrowParties.size();
        dto.setContractEscrowParties(contractEscrowParties);

        List<ContractEscrowDetailDto> detailDtos = ContractEscrowDetailDto.of(contractEscrow);
        dto.setDetailDto(detailDtos);

        return dto;
    }

    /**
     * 계약등록
     *
     * @param body
     * @throws Exception
     */
    @Transactional
    public ContractEscrow newContract(HashMap<String, Object> body) throws Exception {

        String udid = (String) body.get("udid");
        if (!StringUtils.hasText(udid))
            throw new RuntimeException("잘못된 로그인 정보 입니다.");

        Members member = memberService.getMemberByUdid(udid);
        if (member == null)
            throw new RuntimeException("잘못된 로그인 정보 입니다.");
        try {
            if (contractEscrowServiceSupport.validatated(body)) {
                //상품유형코드(에스크로 기본형(1) / 에스크로 보험형(2))
                String prdtTpc = (String) body.get("prdtTpc");
                //상품구분코드(매매(1)/전세(2)/월세(3))
                String prdtDsc = (String) body.get("prdtDsc");

                ContractEscrow escrow = ContractEscrow.builder()
                        .regDt(DateUtil.getThisDate("yyyyMMdd"))
                        .prdtTpc(prdtTpc)
                        .prdtDsc(prdtDsc)
                        //prdtDtlDsc 00:미입력, 11: 월세, 12: 전세
                        .prdtDtlDsc(StringUtil.equals(prdtDsc, "2") ? "12" : StringUtil.equals(prdtDsc, "3") ? "11" : "00")
                        .pstNo((String) body.get("pstNo"))
                        .alnAddr1((String) body.get("alnAddr1"))
                        .alnAddr2((String) body.get("alnAddr2"))
                        .rdAddr1((String) body.get("rdAddr1"))
                        .rdAddr2((String) body.get("rdAddr2"))
                        .escrPgc("0")       //에스크로 등록
                        .escrDtlPgc("01")   //계약당사자 동의 전
                        .feePayYn("N")
                        .isrnEntrYn("N")
                        .escrTrgtAmt(StringUtil.stringToLong(body.get("escrTrgtAmt")))    //에스크로대상금액
                        .membNo(member.getMembNo())
                        .build();

                //임대
                if (StringUtil.equalsAny(prdtDsc, "2", "3")) {
                    //에스크로 수수료금액 조회
                    Long feeAmt = feeMaterService.getFeeAmt(StringUtil.stringToLong(body.get("grntAmt")), prdtTpc, prdtDsc);
                    escrow.setFeeAmt(feeAmt);

                    escrow.setGrntAmt(StringUtil.stringToLong(body.get("grntAmt")));  //보증금액

                } else {
                    //에스크로 수수료금액 조회
                    Long feeAmt = feeMaterService.getFeeAmt(StringUtil.stringToLong(body.get("slAmt")), prdtTpc, prdtDsc);
                    escrow.setFeeAmt(feeAmt);

                    escrow.setSlAmt(StringUtil.stringToLong(body.get("slAmt")));      //매매금액
                }

                if (StringUtil.equals(prdtTpc, "2")) {   //보험형

                    escrow.setBndMaxAmt(StringUtil.stringToLong((body.get("bndMaxAmt") == "") ? "0" : body.get("bndMaxAmt")));
                    escrow.setCntrtDrwupDt(((String) body.get("cntrtDrwupDt")).replaceAll("-", ""));
                    escrow.setExeDt(((String) body.get("lsStDt")).replaceAll("-", ""));        //실행일자 = 계약일
                    escrow.setLsStDt(((String) body.get("lsStDt")).replaceAll("-", ""));     //계약일

                    //임대
                    if (StringUtil.equalsAny(prdtDsc, "2", "3")) {

                        if (StringUtil.equals(prdtDsc, "3")) {
                            escrow.setRntAmt(StringUtil.stringToLong(body.get("rntAmt")));    //차임금액
                        }
                        escrow.setLsEndDt(((String) body.get("lsEndDt")).replaceAll("-", ""));   //퇴거일
                    }
                } else {
                    escrow.setPmntPlnDt(((String) body.get("pmntPlnDt")).replaceAll("-", ""));   //지급예정일자
                }

                //거래 에스크로 상세
                escrow.setContractEscrowDetails(contractEscrowServiceSupport.getEscrowDetails(body));
                //거래 에스크로 관계자 상세
                escrow.setContractEscrowParties(contractEscrowServiceSupport.getEscrowParties(member));

                ContractEscrow save = save(escrow);

                // 알림톡 / PUSH 전송
                escrSendMsg.sendMsg(escrow.getEscrMKey() + "", 30001, null, null);

                return save;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 계약정보 수정
     *
     * @param body
     */
    @Transactional
    public ContractEscrow update(Long escrMKey, HashMap<String, Object> body) throws Exception {

        log.debug("update ===========================================================================");
        StringUtil.printMapInfo(body);
        log.debug("update ===========================================================================");

        String udid = (String) body.get("udid");
        if (!StringUtils.hasText(udid))
            throw new RuntimeException("잘못된 로그인 정보 입니다.");

        Members member = memberService.getMemberByUdid(udid);
        body.put("escrMKey", escrMKey);
        ContractEscrow contractEscrow = getContractEscrow(escrMKey);
        if (contractEscrow == null)
            throw new RuntimeException("잘못된 요청 입니다.");

        if (StringUtil.equalsAny(contractEscrow.getEscrPgc(), "8", "9"))
            throw new RuntimeException("수정할 수 없는 단계입니다.");

        try {
            if (contractEscrowServiceSupport.validatated(body)) {
                contractEscrow.setPstNo((String) body.get("pstNo"));
                contractEscrow.setAlnAddr1((String) body.get("alnAddr1"));
                contractEscrow.setAlnAddr2((String) body.get("alnAddr2"));
                contractEscrow.setRdAddr1((String) body.get("rdAddr1"));
                contractEscrow.setRdAddr2((String) body.get("rdAddr2"));

                contractEscrow.setEscrTrgtAmt(StringUtil.stringToLong(String.valueOf(body.get("escrTrgtAmt"))));

                String prdtTpc = contractEscrow.getPrdtTpc();
                String prdtDsc = contractEscrow.getPrdtDsc();
                if (StringUtil.equals(prdtTpc, "1")) {       //기본형
                    contractEscrow.setPmntPlnDt(String.valueOf(body.get("pmntPlnDt")).replaceAll("-", ""));   //지급예정일자
                } else {
                    //보험형
                    String bndMaxAmt = !StringUtils.hasText((String) body.get("bndMaxAmt")) ? "0" : String.valueOf(body.get("bndMaxAmt"));     //채권최고금액
                    contractEscrow.setBndMaxAmt(StringUtil.stringToLong(bndMaxAmt));
                    contractEscrow.setCntrtDrwupDt(String.valueOf(body.get("cntrtDrwupDt")).replaceAll("-", ""));    //계약일
                    contractEscrow.setLsStDt(String.valueOf(body.get("lsStDt")).replaceAll("-", ""));     //입주일

                    if (StringUtil.equals(prdtDsc, "1")) {      //매매
                        Long feeAmt = feeMaterService.getFeeAmt(StringUtil.stringToLong(String.valueOf(body.get("slAmt"))), prdtTpc, prdtDsc);
                        contractEscrow.setFeeAmt(feeAmt);
                        contractEscrow.setSlAmt(StringUtil.stringToLong(String.valueOf(body.get("slAmt"))));      //매매금액
                    } else {  //전월세
                        Long feeAmt = feeMaterService.getFeeAmt(StringUtil.stringToLong(String.valueOf(body.get("grntAmt"))), prdtTpc, prdtDsc);
                        contractEscrow.setFeeAmt(feeAmt);
                        contractEscrow.setGrntAmt(StringUtil.stringToLong(String.valueOf(body.get("grntAmt"))));  //보증금액
                        contractEscrow.setLsEndDt(String.valueOf(body.get("lsEndDt")).replaceAll("-", ""));
                        contractEscrow.setExeDt(String.valueOf(body.get("lsStDt")).replaceAll("-", ""));        //실행일자 = 입주일

                        //월세
                        if (StringUtil.equals(prdtDsc, "3")) {
                            contractEscrow.setRntAmt(StringUtil.stringToLong(String.valueOf(body.get("rntAmt"))));    //차임금액
                        }
                    }
                }

                //거래 에스크로 상세
                contractEscrow.setContractEscrowDetails(contractEscrowServiceSupport.updateDetail(escrMKey, body));

                ContractEscrow save = save(contractEscrow);
                return save;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * 거래 에스크로/ 거래 에스크로 히스토리 저장
     *
     * @param contractEscrow
     */
    @Transactional
    public ContractEscrow save(ContractEscrow contractEscrow) {
        ContractEscrow save = contractEscrowRepository.save(contractEscrow);
        //거래 에스크로 이력
        contractEscrowHistoryRepository.save(ContractEscrowHIstory.of(save));
        return save;
    }

    /**
     * 지급계좌정보 저장
     *
     * @param body
     */
    public boolean setPmntAcctNo(HashMap<String, String> body) {
        ContractEscrow escrow = getContractEscrow(body.get("escrMKey"));
        if (escrow == null)
            return false;

        if (StringUtil.equalsAny(escrow.getEscrPgc(), "8", "9"))
            throw new RuntimeException("수정할 수 없는 단계입니다.");

        escrow.setPmntBnkCd(body.get("pmntBnkCd"));
        escrow.setPmntAcctNo(body.get("pmntAcctNo"));

        save(escrow);

        return true;
    }

    /**
     * 거래상세진행코드 업데이트
     *
     * @param escrMKey
     * @param escrDtlPgc
     * @return
     */
    @Transactional
    public boolean updateEscrDtlPgc(Long escrMKey, String escrDtlPgc) {
        ContractEscrow escrow = getContractEscrow(escrMKey);
        if (escrow == null)
            return false;

        escrow.setEscrDtlPgc(escrDtlPgc);
        save(escrow);

        return true;
    }

    /**
     * 거래진행코드 업데이트
     *
     * @param escrMKey
     * @param escrPgc
     * @return
     */
    @Transactional
    public boolean updateEscrPgc(Long escrMKey, String escrPgc) {
        ContractEscrow escrow = getContractEscrow(escrMKey);
        if (escrow == null)
            return false;

        escrow.setEscrPgc(escrPgc);
        save(escrow);

        return true;
    }

    /**
     * 에스크로 입금/지급내역 상세
     *
     * @param escrMKey
     */
    public List<ContractEscrowReportDto> getContractEscrowReport(Long escrMKey) {
        HashMap<String, Long> inputMap = new HashMap<>();
        inputMap.put("escrMKey", escrMKey);

        List<ContractEscrowReportDto> contractEscrowReportDtos = contractEscrowMapper.selectContractEscrowReport(inputMap);
        contractEscrowReportDtos.forEach(report -> {
            try {
                report.setPrdtTpcValue(CommonUtils.getCmnNm("PRDT_TPC", report.getPrdtTpc()));
                report.setPrdtDscValue(CommonUtils.getCmnNm("PRDT_DSC", report.getPrdtDsc()));
                report.setEscrPgcValue(CommonUtils.getCmnNm("ESCR_PGC_" + report.getPrdtTpc(), report.getEscrPgc()));

                String chrgDscNm = "";
                if ("1".equals(report.getPrdtDsc()) && "4".equals(report.getChrgDsc())) {
                    chrgDscNm = "에스크로금액";
                } else {
                    chrgDscNm = CommonUtils.getCmnNm("CHRG_DSC", report.getChrgDsc());
                }
                report.setChrgDscValue(chrgDscNm);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return contractEscrowReportDtos;
    }

    /**
     * 매수인/임차인 최종지급승인
     *
     * @param body
     */
    public ContractEscrow approval(HashMap<String, String> body) throws Exception {

        ContractEscrow contractEscrow = getContractEscrow(body.get("escrMKey"));
        if (contractEscrow == null)
            throw new RuntimeException("잘못된 요청입니다.");

        String prdtTpc = contractEscrow.getPrdtTpc();
        String escrPgc = String.valueOf(Integer.valueOf(prdtTpc) + 2);      //잔금단계
        String escrDtlPgc = escrPgc + "2";                                  //잔금입금완료

        //ESCR_DTL_PGC 잔금입금완료 인지 확인
        if (!StringUtil.equals(contractEscrow.getEscrDtlPgc(), escrDtlPgc)) {
            log.warn("정상적인 진행상태가 아닙니다 {}", contractEscrow.getEscrDtlPgc());
            throw new RuntimeException("정상적인 진행상태가 아닙니다");
        }

        if (StringUtil.equals(prdtTpc, "1")) {       //에스크로 기본형
            contractEscrow.setEscrPgc("4");
            contractEscrow.setEscrDtlPgc("41");
        } else {                                          //에스크로 보험형
            contractEscrow.setEscrDtlPgc("44");
        }

        //지급승인일자
        contractEscrow.setPmntAprvDt(DateUtil.getThisDate("yyyyMMdd"));
        save(contractEscrow);

        return contractEscrow;

    }

    /**
     * 확정일자 저장
     *
     * @param body
     * @return
     */
    public boolean fixed(HashMap<String, Object> body) throws Exception {

        String escr_m_key = StringUtil.mapToString(body, "escrMKey");

        ContractEscrow escrow = getContractEscrow(escr_m_key);
        if (escrow == null)
            throw new RuntimeException("잘못된 요청 입니다.");

        //임대인 경우만 해당
        if (StringUtil.equals(escrow.getPrdtDsc(), "1"))    //매매
            throw new RuntimeException("잘못된 요청 입니다.");

        //에스크로 종료인 경우만 해당
        if (!StringUtil.equals(escrow.getEscrPgc(), "9"))
            throw new RuntimeException("잘못된 요청 입니다.");

        //확정일자
        escrow.setEscrDtlPgc("92");
        escrow.setLsFixDt((String) body.get("lsFixDt"));

        save(escrow);

        /*==============================================================*/
        // 알림톡/PUSH 전송
        escrSendMsg.sendMsg(escr_m_key, 37001, null, null);
        /*==============================================================*/

        return true;
    }

    /**
     * 해지정보 조회(반환금액)
     *
     * @param
     * @return
     */
    public HashMap<String, Object> getCancelInfo(HashMap<String, Object> paramMap) {

        paramMap.put("ESCR_M_KEY", paramMap.get("escrMKey"));

        return escrCancelMapper.checkEscrCancel(paramMap);
    }

    /**
     * 에스크로 해지
     *
     * @param map
     * @return
     */
    @Transactional
    public HashMap<String, Object> cancel(HashMap<String, Object> paramMap) throws Exception {

        HashMap<String, Object> resMap = new HashMap<String, Object>();
        HashMap<String, Object> selectMap = escrCancelMapper.checkEscrCancel(paramMap);

        String escr_m_key = StringUtil.mapToString(selectMap, "ESCR_M_KEY");
        String chrg_dsc = StringUtil.mapToString(selectMap, "CHRG_DSC");
        String memb_nm = StringUtil.mapToString(selectMap, "MEMB_NM");
        String res_cd = (String) selectMap.get("RES_CD");
        String res_msg = (String) selectMap.get("RES_MSG");
        long rfnd_amt = StringUtil.mapToStringL(selectMap, "RFND_AMT");
        int dbCnt = 0;

        String pmnt_bnk_cd = StringUtil.mapToString(paramMap, "PMNT_BNK_CD");
        String pmnt_bnk_nm = CommonUtils.getCmnNm("BNK_CD", pmnt_bnk_cd);
        String pmnt_acct_no = StringUtil.mapToString(paramMap, "PMNT_ACCT_NO");

        if ("0000".equals((String) selectMap.get("RES_CD"))) {

            dbCnt = escrCancelMapper.updateVrAcctM(selectMap);
            dbCnt = escrCancelMapper.updateVracctAsgnD(selectMap);
            dbCnt = escrCancelMapper.insertEscrPmntD(selectMap);
            dbCnt = escrCancelMapper.updateEscrM(selectMap);
            dbCnt = ifTgInfoMapper.insertBackupEscrM(paramMap);

            if (rfnd_amt == 0) {

                /*==============================================================*/
                // 알림톡/PUSH 전송
                escrSendMsg.sendMsg(escr_m_key, 37002, null, null);
                /*==============================================================*/

                resMap.put("RES_CD", "0000");
                resMap.put("RES_MSG", "SUCCESS");
            } else {

                if ("7".equals(chrg_dsc)) {
                    /*============================================================================*/
                    // 수수료환불
                    /*============================================================================*/
                    log.debug("============>>> feeService cancelCard Call !!!!");
                    resMap = feeService.cancelCard(selectMap);
                    resMap.put("RES_CD", StringUtil.mapToString(resMap, "rslt_cd"));
                    resMap.put("RES_MSG", StringUtil.mapToString(resMap, "rslt_msg"));

                } else {
                    /*============================================================================*/
                    // 에스크로금액 반환
                    /*============================================================================*/
                    resMap = LFBService.sendEscrTrn(Long.parseLong(escr_m_key), chrg_dsc);
                }

                /*==============================================================*/
                // 알림톡/PUSH 전송

                Object[] msgPatten = {pmnt_bnk_nm, pmnt_acct_no, memb_nm, NumberUtil.toDecimalFormat(rfnd_amt)};
                escrSendMsg.sendMsg(escr_m_key, 37003, msgPatten, null);
                /*==============================================================*/


                log.debug("cancel OK  RES_CD  [" + res_cd + "]");
                log.debug("cancel OK  RES_MSG [" + res_msg + "]");
            }

            return resMap;
        } else {

            log.debug("cancel Check Error  RES_CD  [" + (String) selectMap.get("RES_CD") + "]");
            log.debug("cancel Check Error  RES_MSG [" + (String) selectMap.get("RES_MSG") + "]");
            return selectMap;
        }
    }

    /**
     * 매도인(임대인)/매수인(임차인) 인원수 저장
     *
     * @param body
     */
    @Transactional
    public ContractEscrow setPartyNums(HashMap<String, String> body) throws Exception {

        ContractEscrow escrMKey = getContractEscrow(body.get("escrMKey"));
        if (StringUtil.equalsAny(escrMKey.getEscrPgc(), "8", "9"))
            throw new RuntimeException("수정할 수 없는 단계 입니다.");

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ESCR_M_KEY", body.get("escrMKey"));
        paramMap.put("SLDLV_LS_NOP", body.get("sldlvLsNop"));
        paramMap.put("PCHS_HRE_NOP", body.get("pchsHreNop"));
        contractEscrowMapper.updateNop(paramMap);

        int chkValue = escrMapper.checkTrAsntFn(paramMap);

        if (chkValue == 1) {
            escrSendMsg.sendMsg(body.get("escrMKey"), 31005, null, null);
        }

        return save(escrMKey);
    }

    /**
     * 지급예정 데이터 조회 (기본형)
     *
     * @param prdtTpc
     * @return
     */
    public List<ContractEscrow> getPmntPlnData(String prdtTpc) {
        return contractEscrowRepository.findAllByPmntPlnDtIsNotNullAndPrdtTpc(prdtTpc);
    }

    /**
     * 보험형 전체 데이터
     *
     * @param prdtTpc
     * @return
     */
    public List<ContractEscrow> getInsureData(String prdtTpc) {
        return contractEscrowRepository.findAllByPrdtTpc(prdtTpc);
    }

    /**
     * 수수료입금전 해약
     *
     * @param escrMKey
     * @return
     */
    @Transactional
    public ContractEscrow delete(Long escrMKey) {
        if (escrMKey == null)
            throw new RuntimeException("잘못된 요청입니다.");

        Optional<ContractEscrow> byId = contractEscrowRepository.findById(escrMKey);
        if (byId.isEmpty())
            throw new RuntimeException("잘못된 요청입니다.");

        ContractEscrow escrow = byId.get();
        if (StringUtil.equals(escrow.getEscrDtlPgc(), "03"))
            throw new RuntimeException("이미 수수료납입이 되었습니다.");

        escrow.setEscrPgc("8");
        escrow.setEscrDtlPgc("81");

        escrow.getContractEscrowDetails().size();
        escrow.getContractEscrowParties().size();

        return contractEscrowRepository.save(escrow);
    }

    /**
     * 회원ID로 계약정보 조회
     *
     * @param membId
     * @return
     */
    public boolean getContractsByMembId(String membId) {
        List<ContractEscrow> byMembId = contractEscrowRepository.findByMembNo(membId);
        return byMembId.size() > 0;
    }
}
