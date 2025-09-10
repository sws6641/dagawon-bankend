package com.bankle.common.asis.domain.service;

import com.bankle.common.asis.aspect.PerfLogging;
import com.bankle.common.asis.domain.entity.ContractEscrow;
import com.bankle.common.asis.domain.entity.Members;
import com.bankle.common.asis.domain.entity.MembersHistory;
import com.bankle.common.asis.domain.entity.ids.MembersHistoryId;
import com.bankle.common.asis.domain.enums.EnterStatus;
import com.bankle.common.asis.domain.mapper.MemberMapper;
import com.bankle.common.asis.domain.repositories.MemberHistoryRepository;
import com.bankle.common.asis.domain.repositories.MemberRepository;
import com.bankle.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final ApplicationEventPublisher publisher;
    private final MemberRepository memberRepository;
    private final MemberHistoryRepository memberHistoryRepository;
//    private final MembersServiceWithRedis membersServiceWithRedis;
    private final MemberMapper memberMapper;

    /**
     * 회원전체 조회
     *
     * @param start
     * @param end
     * @param sort
     * @return
     */
    public Page<Members> getAllMembers(Integer start, Integer end, String sort) {

        start = (start == null) ? 0 : start;
        end = (end == null) ? 10 : end;

        Pageable pageable;
        if (StringUtils.hasText(sort)) {
            pageable = PageRequest.of(start, end);
        } else {
            Sort sorting = Sort.by(sort).ascending();
            pageable = PageRequest.of(start, end, sorting);
        }

        Page<Members> members = memberRepository.findAll(pageable);

//        log.info("Total Page : " + members.getTotalPages());
//        log.info("Total Count : " + members.getTotalElements());
//        log.info("page number : " + members.getNumber());
//        log.info("page size : " + members.getSize());
//        log.info("has next page? " + members.hasNext());
//        log.info("first page ? " + members.isFirst());


        return members;
    }

    /**
     * 회원 조회 by UUID
     *
     * @param udid
     * @return
     */
    public Members getMemberByUdid(String udid) {
        if (StringUtils.hasText(udid)) {
            log.warn("udid cannot be null or empty");
            throw new RuntimeException("udid cannot be null or empty");
        }
        return memberRepository.findByUdidAndEntrStc(udid, "1").orElse(null);
    }

    /**
     * udid로 membId 조회
     *
     * @param udid
     * @return
     * @throws Exception
     */
    public String getMemberHistoryByUdid(String udid) throws Exception {
        List<MembersHistoryId> byUdid = memberHistoryRepository.findByUdidOrderByMembersHistoryIdChgDtmDesc(udid);
        Optional<MembersHistoryId> first = byUdid.stream().findFirst();
        if (first.isEmpty())
            return null;

        return first.get().getMembNo();
    }

    /**
     * 회원 조회 by 회원Id
     *
     * @param membId
     * @return
     */
    public Members getMemberByMembId(String membId) {
        if (StringUtils.hasText(membId)) {
            log.warn("member id cannot be null or empty");
            throw new RuntimeException("member id cannot be null or empty");
        }

        return memberRepository.findByMembNo(membId).orElse(null);
    }

    /**
     * 회원조회 By 휴대폰번호
     *
     * @param hpNo
     * @return
     */
    public Members getMemberByHpNo(String hpNo) {
        if (StringUtils.hasText(hpNo))
            throw new RuntimeException("hpNo cannot be null");

        return memberRepository.findByHpNoAndEntrStc(hpNo, "1").orElse(null);
    }

    /**
     * 회원명 조회
     *
     * @param membId
     * @return
     */
    public String getMembNm(String membId) {
        return getMemberByMembId(membId).getMembNm();
    }

    /**
     * 회원 ID 생성
     * 생성규칙 : 일자(YYYYMMDD) + 회원구분코드(MEMB_DSC: 개인(1)) + 일련번호(5)
     *
     * @return
     */
    public String makeMembId() {
        String tmpMembId = DateUtil.getThisDate("yyyyMMdd") + "1";
        return tmpMembId + getMaxSequence(tmpMembId);
    }

    /**
     * 새로운 sequence 조회
     *
     * @param tmpMembId 일자(YYYYMMDD) + 회원구분코드(MEMB_DSC: 개인(1))
     * @return
     */
    public String getMaxSequence(String tmpMembId) {

        //오늘날짜에 가입한 모든 회원의 ID 조회
        List<String> list = memberRepository.getMembIdsInThisDay(tmpMembId);
        if (list.size() == 0) {
            return StringUtil.leftPad("1", 5, "0");
        } else {
            //가장 마지막 회원번호 PICK
            Optional<Long> first = list.stream()
                    .map(membId -> Long.valueOf(membId.replace(tmpMembId, "")))
                    .min(Comparator.reverseOrder());
            //가장 마지막 회원번호 + 1
            Long newSequence = first.get() + 1;

            return StringUtil.leftPad(String.valueOf(newSequence), 5, "0");
        }
    }

    /**
     * 회원 저장
     *
     * @param member
     * @return
     */
    @Transactional
    public Members save(Members member) {
        //Entity 유효성 검사
        validate(member);
        //가입일자
        member.setEntrDt((StringUtils.hasText(member.getEntrDt()))
                ? DateUtil.getThisDate("yyyyMMdd") : member.getEntrDt());

        return memberRepository.save(member);
    }

    @Transactional
    public Members update(Members member) {
        return memberRepository.save(member);
    }

    /**
     * 회원 히스토리 저장
     *
     * @param member
     */
    @Transactional
    public void addHistory(Members member) {
        validate(member);
        memberHistoryRepository.save(MembersHistory.of(member));
    }

    /**
     * 회원 삭제
     *
     * @param uuid
     */
    @Transactional
    @PerfLogging
    public boolean deleteMember(String uuid) throws Exception {
        Members member = getMemberByUdid(uuid);
        publisher.publishEvent(member);

        member.setFnlCnctDt(DateUtil.getThisDate("yyyymmdd"));
        member.setEntrStc(EnterStatus.DROP_OUT.getCode());

        try {
            memberRepository.save(member);
            memberHistoryRepository.save(MembersHistory.of(member));
//            membersServiceWithRedis.deleteToken(member.getMembId());

        } catch (Exception re) {
            log.error("error deleting entity", member.getMembNo(), re);
            throw new RuntimeException("error deleting entity " + member.getMembNo());
        }

        return true;

    }

    /**
     * Entity 유효성 검사
     *
     * @param member
     */
    private void validate(Members member) {
        if (member == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (member.getMembNo() == null) {
            log.warn("unknown member");
            throw new RuntimeException("unknown member");
        }
    }

    /**
     * 앱 노티 설정저장
     *
     * @param body
     * @return
     */
    public Members setAppInfo(HashMap<String, String> body) throws Exception {
        if (StringUtils.hasText(body.get("udid")))
            throw new RuntimeException("잘못된 요청입니다.[udid is null]");

        Members member = getMemberByUdid(body.get("udid"));
        if (member == null)
            throw new RuntimeException("잘못된 요청입니다. [조회된 회원이 없습니다.]");

        if (body.get("escrNotiYn") != null)
            member.setEscrNotiYn(body.get("escrNotiYn"));
        if (body.get("pushNotiYn") != null)
            member.setPushNotiYn(body.get("pushNotiYn"));
        if (body.get("katokNotiYn") != null)
            member.setKatokNotiYn(body.get("katokNotiYn"));
        if (body.get("mktNotiYn") != null)
            member.setMktNotiYn(body.get("mktNotiYn"));

        return memberRepository.save(member);
    }

    /**
     * 앱설정정보 조회
     *
     * @param uuid
     * @return
     * @throws Exception
     */
    public HashMap<String, String> getAppInfo(String uuid) throws Exception {

        HashMap<String, String> map = new HashMap<>();

        Members member = getMemberByUdid(uuid);
        if (member == null)
            throw new RuntimeException("잘못된 요청입니다. [조회된 회원이 없습니다.]");

        map.put("escrNotiYn", member.getEscrNotiYn());
        map.put("pushNotiYn", member.getPushNotiYn());
        map.put("katokNotiYn", member.getKatokNotiYn());
        map.put("mktNotiYn", member.getMktNotiYn());
        return map;

    }

    /**
     * 비밀번호 재설정
     *
     * @param body
     * @return
     */
    public boolean resetPwd(HashMap<String, String> body) throws Exception {
        Members member = getMemberByUdid(body.get("udid"));
        member.setPwd(body.get("pwd"));

        Members save = memberRepository.save(member);
        memberHistoryRepository.save(MembersHistory.of(save));

        return true;
    }

    public String getReceiver(ContractEscrow escrow) throws Exception {

        StringBuilder sb = new StringBuilder();
        Members member = getMemberByMembId(escrow.getMembNo());
        sb.append("{\"name\": \"").append(member.getMembNm()).append("\" ,\"mobile\": \"").append(member.getHpNo()).append("\" ,");
        sb.append(" \"note1\": \"\", \"noti2\": \"\", \"noti3\": \"\", \"noti4\": \"\", \"noti5\": \"\"  }");
        return "[" + sb + "]";
    }

    public HashMap<String, String> getDevicdInfo(String membId, HashMap<String, String> params) {
        Members memberByMembId = getMemberByMembId(membId);
        params.put("fcmId", memberByMembId.getFcmId());
        params.put("dvcKnd", memberByMembId.getDvcKnd());

        return params;
    }


    public Members getMemberByCi(String ci) {
        Optional<Members> byCi = memberRepository.findByCi(ci);
        return (byCi.isPresent() ? byCi.get() : null);
    }

    /**
     * 멤버정보 세팅
     *
     * @param body
     * @return
     */
    public Members setMember(HashMap<String, Object> body) {

        Members member = null;

        //등록된 CI인지 체크
        int entrCnt = memberMapper.chkMembDump(body);

        if (entrCnt > 0) {
            member = memberMapper.getMemberInfo(body);
        }

        //등록된 CI인 경우, 기존 회원정보 업데이트
        if (member != null) {

            body.put("memb_id", member.getMembNo());
            member = updateMember(member, body);
            member.setExistEntrYn("Y");

        } else {

            member = insertMember(body);
            member.setExistEntrYn("N");

        }
        return member;
    }

    /**
     * 회원 업데이트
     *
     * @param members
     * @param body
     */
    public Members updateMember(Members members, HashMap<String, Object> body) {

        int dbCnt = memberMapper.updateMembInfo(body);
        return getMemberByMembId(members.getMembNo());
    }

    /**
     * 회원 등록
     *
     * @param body
     * @return
     */
    public Members insertMember(HashMap<String, Object> body) {
        //새로운 멤버Id 생성
        String membId = makeMembId();
        //새로운 멤버 생성

        Members newMember = Members.builder()
                .membNo(membId)
                .pwd((String) body.get("pwd"))
                .membDsc("1")   //개인
                .membNm((String) body.get("membNm"))
                .telopCd((String) body.get("telopCd"))
                .hpNo((String) body.get("hpNo"))
                .birthDt((String) body.get("birthDt"))
                .sex((String) body.get("sex"))
                .ntvFrnrDsc((String) body.get("ntvFrnrDsc"))
                .email((String) body.get("email"))
                .fcmId((String) body.get("fcmId"))
                .udid((String) body.get("udid"))
                .ci((String) body.get("ci"))
                .di((String) body.get("di"))
                .entrStc(EnterStatus.SINED_UP.getCode())   //가입
                .dvcKnd((String) body.get("dvcKnd"))
                .escrNotiYn("Y")
                .pushNotiYn("Y")
                .katokNotiYn("Y")
                .mktNotiYn((String) body.get("mktNotiYn"))
                .build();

        return save(newMember);
    }

    /**
     * 회원가입 중복여부 체크
     *
     * @param ci
     * @return
     */
    public Members isDuplicateAccount(String ci) {
        return getMemberByCi(ci);
    }
}
