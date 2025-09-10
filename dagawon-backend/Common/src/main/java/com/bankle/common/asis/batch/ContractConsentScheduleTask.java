//package kr.co.anbu.batch;
//
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.entity.ContractEscrowParty;
//import kr.co.anbu.domain.repositories.ContractEscrowRepository;
//import kr.co.anbu.domain.service.ContractEscrowService;
//import kr.co.anbu.domain.service.MemberService;
//import kr.co.anbu.domain.service.VirtualAccountService;
//import kr.co.anbu.event.ContractEscrowEvent;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class ContractConsentScheduleTask {
//
///*
//    private final ContractEscrowRepository contractEscrowRepository;
//    private final ContractEscrowService contractEscrowService;
//    private final MemberService memberService;
//    private final VirtualAccountService virtualAccountService;
//
//    private final ApplicationEventPublisher publisher;
//
//
//    @Scheduled(cron = "0 45 * * * ?")
//    @Transactional
//    public void allConsented(){
//
//        log.info("==================== 동의완료 업데이트 BATCH 시작 ====================");
//
//        List<ContractEscrow> all = contractEscrowRepository.findAll();
//        log.info("==================== 총 계약건수 : "+all.size());
//        if(all.size() == 0)
//            return;
//
//        AtomicInteger uncompleted = new AtomicInteger();
//        AtomicInteger completed = new AtomicInteger();
//        all.stream().forEach(a -> {
//            if (StringCustUtils.equals(a.getEscrDtlPgc(), "01")) {
//                List<ContractEscrowParty> parties = a.getContractEscrowParties();
//                int size = parties.size();
//
//                Integer nop = (a.getSldlvLsNop() == null ? 0 : a.getSldlvLsNop())
//                        + (a.getPchsHreNop() == null ? 0 : a.getPchsHreNop());
//                if (size != nop) {
//                    log.info(a.getEscrMKey() + " : 이해관계자 모두 등록하지 않았습니다.");
//                    uncompleted.getAndIncrement();
//
//                }else if (parties.stream()
//                        .filter(p -> StringCustUtils.equals("N", p.getTrAsntYn()))
//                        .findAny()
//                        .isEmpty()) {
//
//                    //"계약당사자 동의 / 수수료납입 전"으로 업데이트
//                    a.setEscrDtlPgc("02");
//                    //수수료 납입 가상계좌 할당
//                    virtualAccountService.assignVirtualAccountNo(a.getEscrMKey(), "2", "");
//                    //매수자/임차인 본인 SMS 전송
//                    publisher.publishEvent(new ContractEscrowEvent(a));
//                    //publisher.publishEvent(new ContractKakaoEvent(a));
//
//                    contractEscrowService.save(a);
//
//                    completed.getAndIncrement();
//                }
//            }
//        });
//
//        log.info("==================== 동의완료 건수 : "+completed.get());
//        log.info("==================== 동의완료 업데이트 BATCH 종료 ====================");
//    }
//*/
//}
