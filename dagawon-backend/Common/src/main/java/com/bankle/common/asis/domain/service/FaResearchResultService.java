//package com.bankle.common.asis.domain.service;
//
//import com.bankle.common.asis.domain.entity.FaResearchResult;
//import com.bankle.common.asis.domain.repositories.FaResearchResultRepository;
//import com.bankle.common.asis.infra.FaResearchResultDto;
//import com.bankle.common.asis.utils.CommonUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class FaResearchResultService {
//
//    private final FaResearchResultRepository faResearchResultRepository;
//
//    /**
//     *  보험형 FA 결과조회
//     * @param escrMKey
//     * @return
//     */
//    public List<FaResearchResultDto> findByEscrMKey(Long escrMKey){
//        List<FaResearchResult> byEscrMKey = faResearchResultRepository.findByEscrMKeyOrderByRegDtmDesc(escrMKey);
//        List<FaResearchResultDto> dtos = new ArrayList<>();
//
//        byEscrMKey.forEach(b ->{
//            FaResearchResultDto dto = FaResearchResultDto.of(b);
//            try {
//                dto.setRgstChgDscValue(CommonUtils.getCmnNm("RGST_CHG_DSC",dto.getRgstChgDsc()));
//                dto.setStrnEaneCdValue(CommonUtils.getCmnNm("STRN_EANE_CD", dto.getStrnEaneCd()));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            dtos.add(dto);
//        });
//
//        return dtos;
//    }
//}
