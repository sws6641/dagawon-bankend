package com.bankle.common.asis.domain.service;

import java.util.HashMap;
import java.util.List;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.ContractEscrowParty;
import com.bankle.common.asis.domain.mapper.ContractEscrowMapper;
import com.bankle.common.asis.domain.repositories.ContractEscrowPartyRepository;
import com.bankle.common.asis.domain.repositories.ContractEscrowRepository;
import com.bankle.common.asis.infra.ContractPartiesDto;
import com.bankle.common.asis.utils.CommonUtils;
import com.bankle.common.asis.utils.EscrSendMsgUtils;
import com.bankle.common.util.DateUtil;
import com.bankle.common.util.StringUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractEscrowPartyService {

    private final ContractEscrowRepository contractEscrowRepository;
    private final ContractEscrowPartyRepository contractEscrowPartyRepository;
    private final MemberService memberService;

    private final ApplicationEventPublisher publisher;
    private final EscrSendMsgUtils escrSendMsg;
    private final ContractEscrowMapper escrMapper;
    /**
     * 이해관계자 조회
     * @param escrMKey 에스크로 M 키
     * @return
     */
    @Transactional
    public ContractPartiesDto getParty(Long escrMKey) throws Exception {

        if(escrMKey == null)
            throw new RuntimeException("escrMKey cannot be null");

        //계약서 상세 조회
        ContractEscrow escrow = contractEscrowRepository.findContractEscrowByEscrMKey(escrMKey).orElse(null);
        if(escrow == null)
            return null;

        //리턴 DTO 값 세팅
        ContractPartiesDto of = ContractPartiesDto.of(escrow);
        of.setPrdtTpcValue(CommonUtils.getCmnNm("PRDT_TPC", of.getPrdtTpc()));      //상품 유형코드
        of.setPrdtDscValue(CommonUtils.getCmnNm("PRDT_DSC", of.getPrdtDsc()));      //상품 구분코드
        of.setMembNm(memberService.getMembNm(escrow.getMembNo()));                         //회원명
        setPrtyDscValue(of);

        return of;
    }

    /**
     * 이해관계자 등록
     * @param vo
     * @return
     */
    @Transactional
    public ContractEscrowParty save(ContractEscrowParty vo){
        return contractEscrowPartyRepository.save(vo);
    }

    /**
     * 관계자 구분코드명 조회
     * @param dto
     */
    private void setPrtyDscValue(ContractPartiesDto dto){

        List<ContractEscrowParty> contractEscrowParties = dto.getContractEscrowParties();
        contractEscrowParties.forEach(party -> {
            try {
                party.setPrtyDscValue(CommonUtils.getPrtyDscValue(dto.getPrdtDsc(),party.getPrtyDsc())); //관계자 구분 코드
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 매수인 본인외 이해관계자 동의 저장
     * @param body
     * @return
     */
    public ContractEscrowParty saveOthers(Long escrMKey, HashMap<String, String> body) {

        //계약정보 조회
        ContractEscrow contract = contractEscrowRepository.findContractEscrowByEscrMKey(escrMKey).orElse(null);

        if(contract == null)
            throw new RuntimeException("잘못된 요청입니다.");

        //에스크로 상세 진행구분코드 != 계약당사자 동의 전
        if(!StringUtil.equals(contract.getEscrDtlPgc(), "01"))
            throw new RuntimeException("이미 동의완료 되었습니다.");

        //이해관계자 저장
        String prtyHpNo = body.get("prtyHpNo").replaceAll("-", "");
        ContractEscrowParty party = ContractEscrowParty.builder()
                .prtyDsc(body.get("prtyDsc"))
                .prtyNm(body.get("prtyNm"))
                .prtyHpNo(prtyHpNo)
                .prtyBirthDt(body.get("prtyBirthDt"))
                .prtySex(body.get("prtySex"))
                .trAsntYn(body.get("trAsntYn"))
                .trAsntDt(DateUtil.getThisDate("yyyyMMdd"))
                .escrMKey(escrMKey)
                .build();

        /*=======================================================================================*/
        // 알림톡 / PUSH  전송 (관계자, 에스크로등록자, 에스크로등록자(전원승인시)
        /*=======================================================================================*/
        // 이해관계자 푸시 전송 이벤트 발생 (prty)
        escrSendMsg.sendMsg(contract.getEscrMKey()+"", 31003, null, prtyHpNo);

        // 이해관계자 동의 결과 에스크로등록자 통지 (member)
        String prtyDsc = body.get("prtyDsc");
        String prdtDsc = contract.getPrdtDsc();
        String word1   = "";

        if ("1".equals(prtyDsc)) { word1 = ("1".equals(prdtDsc)) ? "매수" : "임차"; }
        else                     { word1 = ("1".equals(prdtDsc)) ? "매도" : "임대"; }

        Object[] msgPatten = {word1, body.get("prtyNm")};
        escrSendMsg.sendMsg(contract.getEscrMKey()+"", 31004, msgPatten, null);

        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("ESCR_M_KEY", escrMKey);
        int chkValue = escrMapper.checkTrAsntFn(paramMap);

        if (chkValue == 1) {
            escrSendMsg.sendMsg(contract.getEscrMKey()+"", 31005, null, null);
        }

        return contractEscrowPartyRepository.save(party);
    }

    /**
     * 이해관계자 삭제
     * @param escrPrtyDKey
     */
    public void delete(Long escrPrtyDKey) {
        contractEscrowPartyRepository.deleteById(escrPrtyDKey);
    }

    public List<ContractEscrowParty> getParties(Long escrMKey){
        return contractEscrowPartyRepository.findAllByEscrMKey(escrMKey);
    }
}
