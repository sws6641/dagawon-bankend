//package kr.co.anbu.controller;
//
//import kr.co.anbu.domain.entity.CodeDetail;
//import kr.co.anbu.domain.service.CodeService;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//@RestController
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/api/index")
//public class IndexRestController {
//
//    private final CodeService codeService;
//
//    /**
//     * 최신버전 조회
//     * @return
//     */
//    @GetMapping("/latest-version")
//    public @ResponseBody HashMap<String, Object> getLatestVersion() {
//
//        HashMap<String, Object> map = new HashMap<>();
//        List<HashMap<String, Object>> listVerInfo = new ArrayList<>();
//        HashMap<String, Object> mapAndroid = new HashMap<>();
//        HashMap<String, Object> mapIos = new HashMap<>();
//
//        List<CodeDetail> codeDetails = codeService.findByGrpCd("APP_FNL_VER").getCodeDetails();
//        for (CodeDetail codeDetail : codeDetails) {
//            if(StringCustUtils.equals(codeDetail.getCodeDetailId().getCmnCd(), "A")){
//                mapAndroid.put("os", "Android");
//                mapAndroid.put("version", codeDetail.getRfr1Cnts());
//            }else{
//                mapIos.put("os", "iOS");
//                mapIos.put("version", codeDetail.getRfr1Cnts());
//            }
//        }
//
//        listVerInfo.add(mapAndroid);
//        listVerInfo.add(mapIos);
//
//        map.put("resultCode", "success");
//        map.put("resultMessage", "최신버전 정보를 가져오는데 성공하였습니다.");
//        map.put("resultData", listVerInfo);
//
//        return map;
//    }
//}
