package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.MessageTemplate;
import com.bankle.common.asis.domain.repositories.MessageTemplateRepository;
import com.bankle.common.asis.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageTemplateService {

    private final MessageTemplateRepository messageTemplateRepository;
    private final ContractEscrowService contractEscrowService;

    public MessageTemplate getMessageTemplate(Integer id) {
        return messageTemplateRepository.findById(id).orElse(null);
    }

    /**
     * 동의요청 메시지 생성
     *
     * @param id
     * @param membNm
     * @param params
     * @return
     * @throws Exception
     */
    public String makeMessage(int id, String membNm, HashMap<String, String> params) throws Exception {
        //메시지 템플릿 조회
        MessageTemplate messageTemplate = getMessageTemplate(id);
        String kakaoMsg = messageTemplate.getKakaoMsg();
        if (!StringUtils.hasText(kakaoMsg))
            throw new RuntimeException("메시지가 없습니다.");

        Long escrMKey = Long.valueOf(params.get("escrMKey"));
        ContractEscrow contractEscrow = contractEscrowService.getContractEscrow(escrMKey);

        //PRTY_DSC : 관계자 구분코드, PRDT_DSC: 상품구분코드
        String prtyDsc = params.get("prtyDsc");         //관계자 구분코드
        String[] args = {CommonUtils.getPrtyDscValue(contractEscrow.getPrdtDsc(), prtyDsc), membNm};

        return MessageFormat.format(kakaoMsg, args);
    }
}
