package com.bankle.common.comBiz.test.svc;

import com.bankle.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestSvc {

    public void dateTest() {
        String s = "20240402";
        System.out.println("DateUtil.daysBetween(strDate,strDate) = " + DateUtil.getLastDayStrNow());
    }
}
