package com.bankle.common.comBiz.appVersion.svc;

import com.bankle.common.comBiz.appVersion.vo.AppVrCvo;
import com.bankle.common.comBiz.appVersion.vo.AppVrSvo;
import com.bankle.common.comBiz.infoTech.vo.InfoTechCvo;
import com.bankle.common.dto.TbCommAppVrHistDto;
import com.bankle.common.entity.TbCommAppVrHist;
import com.bankle.common.enums.Sequence;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.mapper.TbCommAppVrHistMapper;
import com.bankle.common.mapper.TbRgstrCaseInfoMapper;
import com.bankle.common.repo.TbCommAppVrHistRepository;
import com.bankle.common.util.BizUtil;
import com.bankle.common.util.CustomeModelMapper;
import com.bankle.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * @version 1.0.0
 * @Package com.bankle.common.comBiz.appVersion.svc
 * @Class AppVrSvc.class
 * @Author rojoon
 * @date 2024-06-19
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppVrSvc {

    private final CustomeModelMapper customeModelMapper;
    private final TbCommAppVrHistRepository tbCommAppVrHistRepository;
    private final BizUtil bizUtil;


    /**
     * 앱 버전정보 저장
     *
     * @param : AppVrSvo.SaveAppVrInSvo
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public void crtAppVr(AppVrSvo.SaveAppVrInSvo req) throws Exception{
        try {
            TbCommAppVrHistDto dto = customeModelMapper.mapping(req , TbCommAppVrHistDto.class);
            dto.setVrSeq(Long.parseLong(bizUtil.getSeq(Sequence.APP_VR)));
            tbCommAppVrHistRepository.save(TbCommAppVrHistMapper.INSTANCE.toEntity(dto));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }


    /**
     * 최신앱 버전 조회
     *
     * @param : AppVrSvo.getLatestAppVr
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public AppVrCvo.SearchLatestAppVrResCvo getLatestAppVr() throws Exception{
        try {
            Optional<TbCommAppVrHist> appVrHist = tbCommAppVrHistRepository.findTop1ByOrderByCrtDtmDesc();
            if (appVrHist.isPresent()) {
                TbCommAppVrHistDto dto =  TbCommAppVrHistMapper.INSTANCE.toDto(appVrHist.get());
                var res = customeModelMapper.mapping(dto, AppVrSvo.SearchLatestAppVrOutSvo.class);
                res.setCrtDtm(DateUtil.formatOfPattern(dto.getCrtDtm(),"yyyy-MM-dd"));
                return customeModelMapper.mapping(res, AppVrCvo.SearchLatestAppVrResCvo.class);
            } else {
                throw new DefaultException("최신 앱 정보가 존재하지 않습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
    }


}
