//package kr.co.anbu.controller;
//
//import java.util.HashMap;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.kakaocert.api.KakaocertException;
//import com.kakaocert.api.KakaocertService;
//import com.kakaocert.api.ResponseESign;
//import com.kakaocert.api.VerifyResult;
//import com.kakaocert.api.esign.RequestESign;
//import com.kakaocert.api.esign.ResultESign;
//
//import kr.co.anbu.component.properties.KakaocertProperties;
//import kr.co.anbu.domain.mapper.AccountMapper;
//import kr.co.anbu.infra.Response;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/kakao")
//public class KakaocertController {
//
//    private final KakaocertService kakaocertService;
//    private final KakaocertProperties kakaocertProperties;
//    private final Response response;
//
//    private final AccountMapper accountMapper;
//
//    private RequestESign request;
//
//    private String env;
//
//    /**
//     * 카카오 전자서명 요청
//     * @param params
//     * @return
//     */
//    @PostMapping("/sign")
//    public ResponseEntity<?> RequestESign(@RequestBody HashMap<String, String> params) {
//        try {
//            String receiptId = "";
//
//            env = System.getProperty("spring.profiles.active");
//            if ("".equals(StringCustUtils.nvl(env))) { env = "dev";}
//
//log.debug("=====>> KakaocertController RequestESign env [" + env + "]");
//
//            if (!StringCustUtils.equalsAny(env, "local", "dev")) {
//                setEnvInfoInfo();
//                setRequestInfo(params);
//
//                ResponseESign responseESign =
//                        kakaocertService.requestESign(kakaocertProperties.getClientCode(), request, false);
//
//                /* 인증요청 관계자 정보 임시 저장 */
//                receiptId = responseESign.getReceiptId();
//
//            } else {
//                receiptId = StringCustUtils.getDate("yyyyMMddHHmmssSSS");
//                receiptId = receiptId.substring(1, receiptId.length()) + "01";
//            }
//            params.put("receiptId", receiptId);
//
//            int dbCnt = accountMapper.insertEscrPrtyCtfc(params);
//
//
//            return response.success(params, "success", HttpStatus.OK);
//            //return response.success(responseESign, "success", HttpStatus.OK);
//        } catch (KakaocertException ke) {
//            return response.fail(ke.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 전자서명 상태조회
//     * @param receiptId
//     * @return
//     */
//    @GetMapping("/state/{receiptId}")
//    public ResponseEntity<?> getESignState(@PathVariable String receiptId){
//
//        try {
//            ResultESign responseESign =
//                    kakaocertService.getESignState(kakaocertProperties.getClientCode(), receiptId);
//
//            return response.success(responseESign, "success", HttpStatus.OK);
//        } catch (KakaocertException e) {
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 전자서명 verify
//     * @param receiptId
//     * @return
//     */
//    @GetMapping("/verify/{receiptId}")
//    public ResponseEntity<?> verifyESign(@PathVariable String receiptId) throws InterruptedException{
//        try {
//
//            HashMap<String, Object> rsltMap = getVerifyResult(receiptId);
//
//            int    state   = Integer.parseInt(StringCustUtils.mapToStringL(rsltMap, "state")+"");
//            String rsltMsg = StringCustUtils.mapToString(rsltMap, "verifyResult");
//
//            if (state == 1) {
//                return response.success(getVerifyResult(receiptId), "success", HttpStatus.OK);
//            } else {
//                return response.fail(rsltMsg, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception e) {
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 응답정보 조회
//     * @param receiptId
//     * @return
//     */
//    private HashMap<String, Object> getVerifyResult(String receiptId) throws Exception{
//        HashMap<String, Object> result = new HashMap();
//        int tryNum = 0;
//        int state;
//        try {
//
//            env = System.getProperty("spring.profiles.active");
//            if ("".equals(StringCustUtils.nvl(env))) { env = "dev";}
//
//            if (!StringCustUtils.equalsAny(env, "local", "dev")) {
//                do {
//                    Thread.sleep(3000);             //3초간 중지
//                    ResultESign responseESign =
//                            kakaocertService.getESignState(kakaocertProperties.getClientCode(), receiptId);
//                    state = responseESign.getState();
//                    log.info("=================== state : "+state+" / try : " + ++tryNum);
//
//                }while(state == 0 || tryNum == 100);        //100회 시도
//            } else {
//                state = 1;
//            }
//
//log.debug("=====>> [" + env + "]   Kakao 인증 결과(getVerifyResult) state [" + state + "]");
//
//            if(state == 1) {
//
//                VerifyResult verifyResult = null;
//                if (!StringCustUtils.equalsAny(env, "local", "dev")) {
//                    verifyResult = kakaocertService.verifyESign(kakaocertProperties.getClientCode(), receiptId);
//                }
//
//                // 에스크로 관계자 정보 등록 및 TET_ESCR_M 의 지급계좌 관계자정보 update
//                HashMap<String, String> paramMap = new HashMap<String, String>();
//                paramMap.put("receiptId", receiptId);
//
//                int dbCnt = accountMapper.insertEscrPrtyD(paramMap);
//
//                if (dbCnt < 1) {
//                    result.put("state"       , -1);
//                    result.put("verifyResult", "동일 휴대전화번호로 인증된 관계자가 존재합니다.");
//                }
//
//                result.put("state", state);
//                result.put("verifyResult", verifyResult);
//            }else {
//                result.put("state", state);
//                result.put("verifyResult", "expired");
//            }
//
//        } catch (KakaocertException | InterruptedException e) {
//            throw new RuntimeException(e.getMessage());
//        } catch (Exception Ex) {
//            Ex.printStackTrace();
//        }
//
//        return result;
//    }
//
//    private void setEnvInfoInfo(){
//        request = new RequestESign();
//        // 고객센터 전화번호  , 카카오톡 인증메시지 중 "고객센터" 항목에 표시
//        request.setCallCenterNum("1600-9999");
//        // 별칭코드, 이용기관이 생성한 별칭코드 (파트너 사이트에서 확인가능)
//        // 카카오톡 인증메시지 중 "요청기관" 항목에 표시
//        // 별칭코드 미 기재시 이용기관의 이용기관명이 "요청기관" 항목에 표시
//        request.setSubClientID("");
//        // 인증요청 만료시간(초), 최대값 : 1000  인증요청 만료시간(초) 내에 미인증시, 만료 상태로 처리됨 (권장 : 300)
//        request.setExpires_in(300);
//        /*
//         * 인증서 발급유형 선택
//         * true : 휴대폰 본인인증만을 이용해 인증서 발급
//         * false : 본인계좌 점유 인증을 이용해 인증서 발급
//         */
//        request.setAllowSimpleRegistYN(true);
//        /*
//         * 수신자 실명확인 여부
//         * true : 카카오페이가 본인인증을 통해 확보한 사용자 실명과 ReceiverName 값을 비교
//         * false : 카카오페이가 본인인증을 통해 확보한 사용자 실명과 RecevierName 값을 비교하지 않음.
//         */
//        request.setVerifyNameYN(true);
//    }
//
//    private void setRequestInfo(HashMap<String, String> params) {
//        // 수신자 생년월일, 형식 : YYYYMMDD
//        request.setReceiverBirthDay(params.get("receiverBirthDay"));
//        // 수신자 휴대폰번호
//        request.setReceiverHP(params.get("receiverHP").replaceAll("-",""));
//        // 수신자 성명
//        request.setReceiverName(params.get("receiverName"));
//        // 인증요청 메시지 부가내용, 카카오톡 인증메시지 중 상단에 표시
//        request.setTMSMessage(""); // 부가메시지 내용
//        // 인증요청 메시지 제목, 카카오톡 인증메시지 중 "요청구분" 항목에 표시
//        request.setTMSTitle("에스크로 이용동의");
//        // 전자서명 원문
//        request.setToken("token value");
//        // PayLoad, 이용기관이 API 요청마다 생성한 payload(메모) 값
//        request.setPayLoad("memo Info");
//    }
//}
