package com.bankle.common.comBiz.commcode.svc;

import com.bankle.common.comBiz.commcode.vo.CommCodeCvo;
import com.bankle.common.comBiz.commcode.vo.CommCodeSvo;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.mapper.TbCommCodeMapper;
import com.bankle.common.repo.TbCommCodeRepository;
import com.bankle.common.util.CustomeModelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @Package com.bankle.common.comBiz.commcode.svc
 * @Class CommCodeSvc.class
 * @Author sh.lee
 * @date 2024-05-21
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommCodeSvc {

    private final CustomeModelMapper customeModelMapper;

    private final TbCommCodeRepository tbCommCodeRepository;

    /**
     * @Package com.bankle.common.comBiz.commcode.svc
     * @Class CommCodeSvc.class
     * @method searchCommCodeMultiList
     * @Author sh.lee
     * @date 2024-05-21
     * @version 1.0.0
     */
    public Map<String, Map<String, Object>> searchCommCodeMultiList(List<String> multiGrpCd) {
        try {
            var commCodeModel = tbCommCodeRepository.findByIdGrpCdIn(multiGrpCd);

            //공통코드가 없을 경우
            if (commCodeModel.stream().count() < 1) {
                return new HashMap<>();
            }

            // 존재할 경우
            var commCodeDtos = commCodeModel.stream()
                    .map(TbCommCodeMapper.INSTANCE::toDto).toList();

            var commCodeSvos = commCodeDtos.stream()
                    .map(dto -> customeModelMapper.mapping(dto, CommCodeSvo.SearchOutVo.class)).toList();
            var commCodeCvos = new ArrayList<>(commCodeSvos.stream()
                    .map(svo -> customeModelMapper.mapping(svo, CommCodeCvo.SearchCommCodeReqCvo.class)).toList());

            // 코드 순서 정렬
            commCodeCvos
                    .sort(Comparator.comparing(CommCodeCvo.SearchCommCodeReqCvo::getGrpCd)
                            .thenComparing(CommCodeCvo.SearchCommCodeReqCvo::getNum));

            return commCodeCvos.stream().collect(
                    Collectors.groupingBy(
                            CommCodeCvo.SearchCommCodeReqCvo::getGrpCd, Collectors.toMap(
                                    CommCodeCvo.SearchCommCodeReqCvo::getCode, CommCodeCvo.SearchCommCodeReqCvo::getCodeNm
                            )
                    )
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }

    /**
     * @Package com.bankle.common.comBiz.commcode.svc
     * @Class   CommCodeSvc.class
     * @method  searchCommCodeMap
     * @Author  sh.lee
     * @date    2024-05-21
     * @version 1.0.0
     */
    public Map<String, String> searchCommCodeMap(String grpCd) {
        try {
            var commCode = tbCommCodeRepository.findByIdGrpCd(grpCd);
            if (commCode.stream().count() < 1)
                return new HashMap<>();

            return commCode.stream().map(TbCommCodeMapper.INSTANCE::toDto).toList()
                    .stream().map(dto -> customeModelMapper.mapping(dto, CommCodeSvo.SearchOutVo.class)).toList()
                    .stream().map(svo -> customeModelMapper.mapping(svo, CommCodeCvo.SearchCommCodeReqCvo.class)).toList()
                    .stream().collect(Collectors.toMap(CommCodeCvo.SearchCommCodeReqCvo::getCode, CommCodeCvo.SearchCommCodeReqCvo::getCodeNm));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
    
    /**
     * @Package com.bankle.common.comBiz.commcode.svc
     * @Class   CommCodeSvc.class
     * @method  searchCommCodeList
     * @Author  sh.lee
     * @date    2024-05-21
     * @version 1.0.0
     */
    public List<CommCodeCvo.SearchCommCodeReqCvo> searchCommCodeList(String grpCd) {
        try {
            var commCode = tbCommCodeRepository.findByIdGrpCd(grpCd);
            if (commCode.stream().count() < 1)
                return new ArrayList<>();

            return commCode.stream().map(TbCommCodeMapper.INSTANCE::toDto).toList()
                    .stream().map(dto -> customeModelMapper.mapping(dto, CommCodeSvo.SearchOutVo.class)).toList()
                    .stream().map(svo -> customeModelMapper.mapping(svo, CommCodeCvo.SearchCommCodeReqCvo.class)).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }


    public String searchCommCodeDtlOne(String grpCd, String code) {
        try {
            var commCode = tbCommCodeRepository.findByIdGrpCd(grpCd);
            if (commCode.stream().count() < 1)
                return "";

            var codeMap = commCode.stream().map(TbCommCodeMapper.INSTANCE::toDto).toList()
                    .stream().map(dto -> customeModelMapper.mapping(dto, CommCodeSvo.SearchOutVo.class)).toList()
                    .stream().map(svo -> customeModelMapper.mapping(svo, CommCodeCvo.SearchCommCodeReqCvo.class)).toList()
                    .stream().collect(Collectors.toMap(CommCodeCvo.SearchCommCodeReqCvo::getCode, CommCodeCvo.SearchCommCodeReqCvo::getCodeNm));

            return codeMap.get(code);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }
}
