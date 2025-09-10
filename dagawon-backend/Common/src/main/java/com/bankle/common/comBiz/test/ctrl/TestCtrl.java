package com.bankle.common.comBiz.test.ctrl;

import com.bankle.common.comBiz.test.svc.TestSvc;
import com.bankle.common.commvo.ResData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lsh
 * @package com.bankle.common.comBiz.test
 * @class TestCtrl.java
 * @date 5/2/24
 **/
@Tag(name = "TEST-01.TEST 컨트롤러", description = "테스트를 위해 만들었습니다. 지우지 말아주세요.")
@RestController
@Slf4j
@RequiredArgsConstructor
public class TestCtrl {

    private final TestSvc testSvc;

    @Operation(summary = "1.TEST", description = "TEST")
    @GetMapping(value = "/comm/test")
    public ResponseEntity<?> testController() throws Exception {
        testSvc.dateTest();
        return ResData.SUCCESS("");
    }
}
