//package kr.co.anbu.controller;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.Base64;
//import java.util.HashMap;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import kr.co.anbu.component.properties.TossKeyProperties;
//import kr.co.anbu.domain.entity.ContractEscrow;
//import kr.co.anbu.domain.mapper.FeeMapper;
//import kr.co.anbu.domain.service.ContractEscrowService;
//import kr.co.anbu.domain.service.FeeMasterService;
//import kr.co.anbu.domain.service.FeeRomDetailService;
//import kr.co.anbu.domain.service.FeeService;
//import kr.co.anbu.infra.Response;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//@RequestMapping("/api/fee")
//public class FeeRomDetailRestController {
//
//    private final FeeService            feeService;
//    private final FeeRomDetailService   feeRomDetailService;
//    private final FeeMasterService      feeMasterService;
//    private final ContractEscrowService contractEscrowService;
//
//    private final FeeMapper         feeMapper;
//    private final TossKeyProperties tossProp;
//
//    private final Response    response;
//
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    /**
//     * calculate
//     * @param params
//     * @return
//     */
//    @PostMapping("")
//    public ResponseEntity<?> getFeeAmt(@RequestBody HashMap<String,String> params){
//
//        return response.success(feeMasterService.calculateFee(params),
//                "success", HttpStatus.OK);
//
//    }
//
//    /**
//     * 수수료 조회
//     * @param body
//     * @return
//     */
//    @PostMapping("/calculate")
//    public ResponseEntity<?> fee(@RequestBody HashMap<String, String> body){
//
//        try {
//            Long feeAmt = feeMasterService.getFeeAmt(Long.valueOf(body.get("amt")), body.get("prdtTpc"), body.get("prdtDsc"));
//            return response.success(feeAmt, "success", HttpStatus.OK);
//        } catch (Exception e) {
//            return response.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * 수수료조회
//     * @param escrMKey
//     * @return
//     */
//    @GetMapping("/{escrMKey}")
//    public ResponseEntity<?> getFee(@PathVariable Long escrMKey){
//        ContractEscrow contract = contractEscrowService.getContractEscrow(escrMKey);
//        if(contract == null)
//            return response.fail("해당 계약이 없습니다.", HttpStatus.BAD_REQUEST);
//
//        return response.success(contract.getFeeAmt(), "success", HttpStatus.OK);
//    }
//
//    /**
//     * 수수료결재 내역 저장
//     * @param body
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/paid")
//    public ResponseEntity<?> paid(@RequestBody HashMap<String, String> body) throws Exception{
//
//
//        String escr_m_key = body.get("escr_m_key");
//
//        HashMap<String, String> selectMap    = feeMapper.selectcFeeStmtH(body);
//        String                  rslt_proc_yn = selectMap.get("RSLT_PROC_YN");
//
//        HashMap<String, String> resMap = new HashMap<String, String>();
//
//        if ("Y".equals(rslt_proc_yn)) {
//
//            resMap.put("RES_CD" ,"0000"    );
//            resMap.put("RES_MSG", "success");
//
//        } else {
//            resMap.put("RES_CD"  ,"1001"    );
//            resMap.put("RES_MSG" , "error"  );
//            resMap.put("TOSS_CD" , selectMap.get("RSLT_CD" ));
//            resMap.put("TOSS_MSG", selectMap.get("RSLT_MSG"));
//        }
//
//        //수수료내역 저장
//        String membId = feeRomDetailService.paid(body);
//
//        //에스크로 거래 계약 카드 리턴
//        return response.success(
//                contractEscrowService.getCards(membId), "success", HttpStatus.OK);
//    }
//
//    @GetMapping("/getTossKey")
//    public ResponseEntity<?> getTossKey() {
//
//        HashMap<String, String> tossMap = new HashMap<String, String>();
//
//        tossMap.put("clientKey"  , tossProp.getClientKey  ());
//        tossMap.put("secretKey"  , tossProp.getSecretKey  ());
//        tossMap.put("customerKey", tossProp.getCustomerKey());
//
//        return response.success(tossMap, "success", HttpStatus.OK);
//    }
//
//    @GetMapping("/payResult")
//    public ResponseEntity<?> payResult (
//                                           @RequestParam String orderId
//                                         , @RequestParam String paymentKey
//                                         , @RequestParam String amount
//                                       ) {
//
//        HashMap<String, Object> paramMap = new HashMap<>();
//
//        String escr_m_key = orderId.substring(12, orderId.length());  // "Contract-Fee" 제거
//
//        paramMap.put("orderId"   , orderId               );
//        paramMap.put("amount"    , String.valueOf(amount));
//        paramMap.put("escr_m_key", escr_m_key            );
//        paramMap.put("stmt_amt"  , String.valueOf(amount));
//
//        log.debug("===================================================================================");
//        log.debug("결제 결과 조회");
//        log.debug("===================================================================================");
//        log.debug("orderId    [" + orderId + "]   " + "paymentKey [" + paymentKey + "]   amount [" + amount + "]");
//        log.debug("===================================================================================");
//
//        HashMap<String, Object> resMap = feeService.callTossPayments("https://api.tosspayments.com/v1/payments/" + paymentKey, "1", paramMap);
//        return  response.success(resMap, "success", HttpStatus.OK);
//
//    }
//
//    public void getStmtInfo(HashMap<String, String> paramMap) {
//
//        try {
//
//            String orderId = paramMap.get("orderId");
//            HttpRequest request = (HttpRequest) HttpRequest.newBuilder()
//                                  .uri(URI.create("https://api.tosspayments.com/v1/payments/orders/" + orderId))
//                                  .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((tossProp.getSecretKey() + ":").getBytes()))
//                                  .method("GET", HttpRequest.BodyPublishers.noBody())
//                                  .build();
//            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//
//            String resBody     = response.body();
//
//            log.debug("=================================================================================================");
//            log.debug("결제 결과 조회");
//            log.debug("=================================================================================================");
//            log.debug(resBody);
//
//
//            JSONParser parser     = new JSONParser();
//            Object     obj        = parser.parse( resBody );
//            JSONObject jsonObj    = (JSONObject) obj;
//            JSONObject jsonDtlObj = (JSONObject)jsonObj.get("card");
//
//            log.debug("=========================================================================");
//            log.debug("카드 결제 상세 내역");
//            log.debug("=========================================================================");
//            log.debug("paymentKey     [" + jsonObj   .get("paymentKey"    ).toString() + "]");
//            log.debug("orderId        [" + jsonObj   .get("orderId"       ).toString() + "]");
//            log.debug("orderName      [" + jsonObj   .get("orderName"     ).toString() + "]");
//            log.debug("method         [" + jsonObj   .get("method"        ).toString() + "]");
//            log.debug("totalAmount    [" + jsonObj   .get("totalAmount"   ).toString() + "]");
//            log.debug("suppliedAmount [" + jsonObj   .get("suppliedAmount").toString() + "]");
//            log.debug("vat            [" + jsonObj   .get("vat"           ).toString() + "]");
//            log.debug("dtl company    [" + jsonDtlObj.get("company"       ).toString() + "]");
//            log.debug("dtl number     [" + jsonDtlObj.get("number"        ).toString() + "]");
//            log.debug("dtl receiptUrl [" + jsonDtlObj.get("receiptUrl"    ).toString() + "]");
//
//            paramMap.put("fee_stmt_dsc"  , "1");   // 카드결제
//            paramMap.put("crd_nm"        , jsonDtlObj.get("company"       ).toString());
//            paramMap.put("crd_no"        , jsonDtlObj.get("number"        ).toString());
//            paramMap.put("sply_prc"      , jsonDtlObj.get("suppliedAmount").toString());
//            paramMap.put("vat"           , jsonDtlObj.get("vat"           ).toString());
//
//        } catch (Exception Ex) {
//            Ex.printStackTrace();
//            log.error("=====>> getStmtInfo ERROR : \n" + Ex.getMessage());
//        }
//    }
//
//    public String getJsonData(JSONObject jsonObj, String keyValue) {
//
//        try                  { return jsonObj.get(keyValue).toString(); }
//        catch (Exception Ex) { return "";                               }
//    }
//}
