//package kr.co.anbu.controller.connection;
//
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import kr.co.anbu.domain.service.extnLk.LguFirmBankingService;
//import kr.co.anbu.utils.FirmBankingUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequiredArgsConstructor
//@Slf4j
//public class LguFirmBankingController {
//
//    /*============================================================================================*/
//    /*============================================================================================*/
//    /*                                                                                            */
//    /*                     LGU+ Firm Banking Socket 전문 연계 Controller                          */
//    /*                                                                                            */
//    /*============================================================================================*/
//    /*============================================================================================*/
//    private final FirmBankingUtils      fbUtils;
//    private final LguFirmBankingService fbService;
//    private int   tgAllLen = 350;
//
//    /*===========================================================================================*/
//    /* Function Name : SetFBDbWonData (DW)                                                       */
//    /*                 Lgu+ 펌뱅킹 DB 원화거래 Socket 전문 연계                                  */
//    /*===========================================================================================*/
//    @PostMapping("/SetFBDbWonData")
//    public void SetFBDbWonData(HttpServletRequest request) throws Exception { SetFBData("DW", request); }
//
//    /*===========================================================================================*/
//    /* Function Name : SetFBDbVaData (DV)                                                        */
//    /*                 Lgu+ 펌뱅킹 DB 가상계좌 Socket 전문 연계                                  */
//    /*===========================================================================================*/
//    @PostMapping("/SetFBDbVaData")
//    public void SetFBDbVaData(HttpServletRequest request) throws Exception { SetFBData("DV", request); }
//
//    /*===========================================================================================*/
//    /* Function Name : SetFBEscrWonData (EW)                                                     */
//    /*                 Lgu+ 펌뱅킹 에스크로 가상계좌 Socket 전문 연계                            */
//    /*===========================================================================================*/
//    @PostMapping("/SetFBEscrWonData")
//    public void SetFBEscrWonData(HttpServletRequest request) throws Exception { SetFBData("EW", request); }
//
//    /*===========================================================================================*/
//    /* Function Name : SetFBEscrVaData (EV)                                                      */
//    /*                 Lgu+ 펌뱅킹 에스크로 가상계좌 Socket 전문 연계                            */
//    /*===========================================================================================*/
//    @PostMapping("/SetFBEscrVaData")
//    public void SetFBEscrVaData(HttpServletRequest request) throws Exception { SetFBData("EV", request); }
//
//
//    /*===========================================================================================*/
//    /* Function Name : SetFBData                                                                 */
//    /*                 Lgu+ 펌뱅킹 DB 가상계좌 Socket 전문 연계                                  */
//    /*===========================================================================================*/
//    public void SetFBData(String svcGbn, HttpServletRequest request) throws Exception{
//
//        int         len = request.getContentLength();
//        InputStream is  = request.getInputStream  ();
//
//        log.info("Request Tg Data Length :" + len);
//
//        byte[] data = new byte[len];
//        is.read(data, 0, len);
//
//        String strReqData = new String(data);
//
//        log.info("strReqData [" + strReqData + "]");
//
//        HashMap<String, Object> rsltMap = new HashMap<String, Object>();
//        InputStream input = new ByteArrayInputStream(data);
//        String      tg_cd = getTgCd(svcGbn, strReqData);
//
//        if ("9999".equals(tg_cd)) {
//            rsltMap.put("RES_CD" , "FB09"      );
//            rsltMap.put("RES_MSG", "전문 헤더 오류");
//        } else {
//            rsltMap = fbService.receiveSocket(svcGbn, tg_cd, input);
//        }
//    }
//
//    /*===========================================================================================*/
//    /* Function Name : getTgCd                                                                   */
//    /*                 전문헤더의 전문구분코드/업무구분코드 기준으로 TG_CD 셋팅                  */
//    /*===========================================================================================*/
//    public String getTgCd(String svcGbn, String strData) throws Exception {
//
//        String tg_dsc  = "";
//        String wk_dsc  = "";
//
//        tg_dsc = strData.substring(58,62);
//        wk_dsc = strData.substring(62,65);
//
//
//        return fbUtils.getTgCd(svcGbn, tg_dsc, wk_dsc);
//    }
//}
