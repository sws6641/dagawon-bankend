package com.bankle.common.userAuth;


import com.bankle.common.asis.domain.dto.MembersDto;
import com.bankle.common.asis.domain.mapper.MembersMapper;
import com.bankle.common.asis.domain.repositories.MemberRepository;
import com.bankle.common.dto.TbAdminCustDto;
import com.bankle.common.dto.TbCustMasterDto;
import com.bankle.common.entity.TbCustMaster;
import com.bankle.common.exception.NotFoundException;
import com.bankle.common.mapper.TbAdminCustMapper;
import com.bankle.common.mapper.TbCustMasterMapper;
import com.bankle.common.repo.TbAdminCustRepository;
import com.bankle.common.repo.TbCustMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserAuthSvc {

    private final TbCustMasterRepository custMasterRepo;

    private final TbAdminCustRepository tbAdminCustRepo;

    @Transactional
    public UserDetails getUserDetails(String membNo, String dvceUnqNum) {

        // 승인된 사용자만 통과시키기(statCd)
        TbCustMasterDto membersDto = TbCustMasterMapper.INSTANCE.toDto(custMasterRepo.findByMembNo(membNo).orElseThrow(
                () -> new NotFoundException("고객 정보가 존재하지 않습니다!")
        ));
 /*
        if (!testMembNo.contains(superModel.getMembNo())) {
            if (!"System".equals(dvceUnqNum) && !tbCustMasterModel.getDvceUnqNum().equals(dvceUnqNum)) {
                throw new DuplicateLoginException("다른기기로 접속하였습니다. 다시 로그인 해주세요.");
            }
        }
        */
        return UserPrincipal.create(membersDto);
    }

    @Transactional
    public UserDetails getAdminDetails(String lognId) {

        TbAdminCustDto tbAdminCustDto = TbAdminCustMapper.INSTANCE.toDto(tbAdminCustRepo.findByLognId(lognId).orElseThrow(
                        () -> new NotFoundException("관리자 정보가 존재하지 않습니다!")));

        TbCustMasterDto tbCustMasterDto = new TbCustMasterDto();
        tbCustMasterDto.setMembNo(tbAdminCustDto.getLognId());
        tbCustMasterDto.setMembNm(tbAdminCustDto.getMngrNm());
        return UserPrincipal.create(tbCustMasterDto);
    }

    /**
     * 로그인한 사람 정보 조회
     *
     * @return : 회원번호,회원명
     * id :  회원번호 : MembNo
     * password : 회원이름 : MembNm
     */
    public UserPrincipal getSessionUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || Objects.isNull(principal)) {
            throw new NotFoundException("JWT Session 정보가 존재하지 않습니다. 다시 로그인 해주세요!");
        }
        return (UserPrincipal) principal;
    }

    public static UserPrincipal getStaticSession() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null || Objects.isNull(principal)) {
            throw new NotFoundException("JWT Session 정보가 존재하지 않습니다. 다시 로그인 해주세요!");
        }
        return (UserPrincipal) principal;
    }

    public static String getMembNo() {
        return getStaticSession().getMembNo();
    }

    public static String getMembNm() {
        return getStaticSession().getMembNm();
    }


}
