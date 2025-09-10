//package kr.co.anbu.domain.service;
//
//import kr.co.anbu.domain.entity.MemberConsent;
//import kr.co.anbu.domain.entity.ids.MemberConsentId;
//import kr.co.anbu.domain.repositories.MemberConsentRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class MemberConsentService {
//    private final MemberConsentRepository memberConsentRepository;
//    private final ConsentService consentService;
//
//    /**
//     * 회원약관동의 저장
//     * @param newMemberId
//     * @param consents
//     */
//    @Transactional
//    public List<MemberConsent> save(String newMemberId, List<HashMap<String, Object>> consents){
//
//        List<MemberConsent> save = new ArrayList();
//
//        for (HashMap<String, Object> consent : consents) {
//
//            String entrAsntCd = (String) consent.get("cd");
//            Long entrAsntSqn = consentService.getLastEntrAsntSqn(entrAsntCd);
//
//            save.add(memberConsentRepository.save(MemberConsent.builder()
//                    .memberConsentId(new MemberConsentId(newMemberId, entrAsntCd, entrAsntSqn))
//                    .entrAsntAnsVal(((boolean) consent.get("checked")) ? "Y" : "N")
//                    .build()));
//        }
//
//        return save;
//    }
//}
