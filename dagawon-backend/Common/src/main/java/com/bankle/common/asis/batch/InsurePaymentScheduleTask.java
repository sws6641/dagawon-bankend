package kr.co.anbu.batch;

// import kr.co.anbu.domain.entity.ContractEscrow;
// import kr.co.anbu.domain.entity.ContractEscrowDetail;
// import kr.co.anbu.domain.service.ContractEscrowService;
// import kr.co.anbu.event.InsurePaymentPushEvent;
// import kr.co.anbu.utils.StringCustUtils;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.context.ApplicationEventPublisher;
// import org.springframework.scheduling.annotation.Scheduled;
// import org.springframework.stereotype.Component;
// import org.springframework.transaction.annotation.Transactional;
// 
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.concurrent.atomic.AtomicInteger;
// import java.util.stream.Collectors;
// 
// @Component
// @Slf4j
// @RequiredArgsConstructor
// public class InsurePaymentScheduleTask {
// 
//     private final ContractEscrowService contractEscrowService;
//     private final ApplicationEventPublisher publisher;
// 
//     /**
//      * 에스크로상태코드 -> 지급지연으로 변경
//      */
//     @Scheduled(cron = "0 0 05 * * ?")
//     @Transactional
//     public void paymentDelayed(){
// 
//         log.info("==================== 지급지연 업데이트 BATCH 시작 ====================");
// 
//         String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//         List<ContractEscrow> allData = getAllData();
//         allData.stream().forEach(a -> {
//             //입금완료 여부 체크
//             if(isCompletedDeposit(a)){
//                 //오늘이 지급완료 예정일인 데이터
//                 List<ContractEscrowDetail> dataOnToday = getDataOnToday(a.getContractEscrowDetails(), today);
//                 log.info("==================== 지급지연 건 : " + dataOnToday.size());
//                 if(dataOnToday.size() != 0){
//                     //지급지연 업데이트
//                     contractEscrowService.updateEscrDtlPgc(a.getEscrMKey(), a.getEscrPgc()+"6");
//                 }
//             }
//         });
// 
//         log.info("==================== 지급지연 업데이트 BATCH 종료 ====================");
//     }
// 
//     /**
//      * 지급지연 SMS 전송
//      */
//     @Scheduled(cron = "0 0 09 * * ?")
//     public void requestPaymentDelayed(){
// 
//         log.info("==================== (보험형) 지급지연 SMS 전송 BATCH 시작 ====================");
// 
//         //(보험형) 지급지연인 모든 데이터 조회
//         List<ContractEscrow> allData = getAllData();
//         AtomicInteger delayed = new AtomicInteger();
//         allData.stream().forEach(a -> {
//             //지급지연인지 체크
//             if(isPaymentDelayed(a)){
//                 delayed.getAndIncrement();
//                 publisher.publishEvent(new InsurePaymentPushEvent(a));
//             }
//         });
//         log.info("==================== 지급지연건 : " + delayed.get());
//         log.info("==================== (보험형) 지급지연 SMS 전송 BATCH 종료 ====================");
//     }
// 
//     /**
//      * 보험형 계약금, 중도금, 잔금상태인 데이터 전체
//      */
//     private List<ContractEscrow> getAllData(){
//         List<ContractEscrow> insureData = contractEscrowService.getInsureData("2");
//         if(insureData.size() == 0)
//             throw new RuntimeException("데이터 없음");
// 
//         return insureData.stream()
//                 .filter(i -> StringCustUtils.equalsAny(i.getEscrPgc(), "2", "3", "4"))
//                 .collect(Collectors.toList());
//     }
// 
//     /**
//      * 입금완료인지 체크
//      * @param escrow
//      * @return
//      */
//     private boolean isCompletedDeposit(ContractEscrow escrow){
//         return StringCustUtils.equalsAny(escrow.getEscrDtlPgc(), "22", "32", "42");
//     }
// 
//     /**
//      * 지급지연 인지 체크
//      * @param escrow
//      * @return
//      */
//     private boolean isPaymentDelayed(ContractEscrow escrow){
//         return StringCustUtils.equalsAny(escrow.getEscrDtlPgc(), "26", "36", "46");
//     }
// 
//     /**
//      * 입금예정일이 오늘인 데이터 추출
//      * @param details
//      * @param today
//      * @return
//      */
//     private List<ContractEscrowDetail> getDataOnToday(List<ContractEscrowDetail> details, String today){
//         //입금예정일이 오늘인 데이터 추출
//         return details.stream().filter(d -> StringCustUtils.equals(d.getRomPlnDt(), today))
//                 .collect(Collectors.toList());
//     }
// }
// 