/*
package kr.co.anbu.batch;

import kr.co.anbu.domain.entity.ContractEscrow;
import kr.co.anbu.domain.service.ContractEscrowService;
import kr.co.anbu.event.BasicPaymentPushEvent;
import kr.co.anbu.utils.DateCustUtils;
import kr.co.anbu.utils.StringCustUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@RequiredArgsConstructor
public class BasicPaymentScheduleTask {

    private final ContractEscrowService contractEscrowService;
    private final ApplicationEventPublisher publisher;

    */
/**
     * 기본형인 경우, 매매대금 또는 보증금 지급요청(승인요청)
     * 지급예정일 3일전, 1일전, 당일 SMS 전송
     * @throws Exception
     *//*

//    @Scheduled(cron = "0 0 09 * * ?")
    public void requestPayment() throws Exception {

        log.info("==================== 지급예정일 3일전, 1일전, 당일 SMS 전송 BATCH 시작 ====================");

        List<ContractEscrow> allData = getAllData();
        log.info("==================== 총 계약건수 >> "+allData.size());
        AtomicInteger dMinus3 = new AtomicInteger();
        AtomicInteger dMinus1 = new AtomicInteger();
        AtomicInteger dDay = new AtomicInteger();
        allData.stream().forEach(d -> {
            try {
                if(is3DaysBefore(d.getPmntPlnDt())){
                    publisher.publishEvent(new BasicPaymentPushEvent(d, "3"));
                    dMinus3.getAndIncrement();
                }

                if(is1DayBefore(d.getPmntPlnDt())){
                    publisher.publishEvent(new BasicPaymentPushEvent(d, "1"));
                    dMinus1.getAndIncrement();
                }

                if(isTheDay(d.getPmntPlnDt())){
                    publisher.publishEvent(new BasicPaymentPushEvent(d, "0"));
                    dDay.getAndIncrement();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        log.info("==================== D-3 >> "+dMinus3.get());
        log.info("==================== D-1 >> "+dMinus1.get());
        log.info("==================== D-day >> "+dDay.get());

        log.info("==================== 지급예정일 3일전, 1일전, 당일 SMS 전송 BATCH 종료 ====================");

    }

    */
/**
     * 기본형 대금지급지연 1,3일 지연
     * @throws Exception
     *//*

    @Scheduled(cron = "0 0 09 * * ?")
    public void delayedPayment() throws Exception{

        log.info("==================== 기본형 대금지급지연 1,3일전 지연 BATCH 시작 ====================");
        List<ContractEscrow> allData = getAllData();
        log.info("==================== 총 계약건수 >> "+allData.size());
        allData.stream().forEach(a -> {
            String delayedDays = DateCustUtils.howLongDelayed(a.getPmntPlnDt());
            if(!StringCustUtils.isEmpty(delayedDays)){
                log.info("==================== 계약번호 : "+a.getEscrMKey()+" >> 지연 "+delayedDays + "일");
                publisher.publishEvent(new BasicPaymentPushEvent(a, "-"+delayedDays));
            }
        });

        log.info("==================== 기본형 대금지급지연 1,3일전 지연 BATCH 종료 ====================");
    }

    */
/**
     * 지급예정일자가 있는 모든 데이터 조회(보험형)
     * @return
     * @throws Exception
     *//*

    private List<ContractEscrow> getAllData(){
        List<ContractEscrow> pmntPlnData = contractEscrowService.getPmntPlnData("1");
        if(pmntPlnData.size() == 0)
            throw new RuntimeException("데이터 없음.");

        return pmntPlnData;
    }

    */
/**
     * 지급에정일이 3일전인지 체크
     * @param pmntPlnDt
     * @return
     * @throws ParseException
     *//*

    private boolean is3DaysBefore(String pmntPlnDt) throws ParseException {
        String dayOfWeek = DateCustUtils.getDayOfWeek(pmntPlnDt);
        LocalDateTime now = LocalDateTime.now();

        if(StringCustUtils.equalsAny(dayOfWeek, "2","3","4")){
            String yyyyMMdd = DateCustUtils.getDateToString(now.minusDays(5), "yyyyMMdd");
            if(StringCustUtils.equals(yyyyMMdd, DateCustUtils.getDateToString(now, "yyyymmdd"))){
                //월,화,수 ->5일전 날짜가 오늘날짜랑 동일해야 함.
                return false;
            }else{
                return false;
            }

        }else if(StringCustUtils.equalsAny(dayOfWeek, "5","6")){
            String yyyyMMdd = DateCustUtils.getDateToString(now.minusDays(3), "yyyyMMdd");
            if(StringCustUtils.equals(yyyyMMdd, DateCustUtils.getDateToString(now, "yyyymmdd"))){
                //목, 금 -> 3일전 날짜가 오늘날짜랑 동일해야 함.
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    */
/**
     * 지급예정일이 1일전인지 체크
     * @param pmntPlnDt
     * @return
     * @throws ParseException
     *//*

    private boolean is1DayBefore(String pmntPlnDt) throws ParseException {
        String dayOfWeek = DateCustUtils.getDayOfWeek(pmntPlnDt);
        LocalDateTime now = LocalDateTime.now();

        if(StringCustUtils.equalsAny(dayOfWeek, "2")){
            String yyyyMMdd = DateCustUtils.getDateToString(now.minusDays(3), "yyyyMMdd");
            if(StringCustUtils.equals(yyyyMMdd, DateCustUtils.getDateToString(now, "yyyymmdd"))){
                //월 ->3일전 날짜가 오늘날짜랑 동일해야 함.
                return false;
            }else{
                return false;
            }

        }else if(StringCustUtils.equalsAny(dayOfWeek, "3","4","5","6")){
            String yyyyMMdd = DateCustUtils.getDateToString(now.minusDays(1), "yyyyMMdd");
            if(StringCustUtils.equals(yyyyMMdd, DateCustUtils.getDateToString(now, "yyyymmdd"))){
                //화,수,목,금 -> 1일전 날짜가 오늘날짜랑 동일해야 함.
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    */
/**
     * 지급예정일이 당일인지 체크
     * @param pmntPlnDt
     * @return
     * @throws ParseException
     *//*

    private boolean isTheDay(String pmntPlnDt) throws ParseException {
        String theDay = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyymmdd"));
        if(StringCustUtils.equals(pmntPlnDt, theDay)){
            return true;
        }else{
            return false;
        }
    }
}
*/
