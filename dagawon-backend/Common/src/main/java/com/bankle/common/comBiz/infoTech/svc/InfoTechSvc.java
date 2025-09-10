package com.bankle.common.comBiz.infoTech.svc;

import com.bankle.common.comBiz.commSvc.FileEncrypterDecrypter;
import com.bankle.common.comBiz.infoTech.vo.InfoTechCvo;
import com.bankle.common.comBiz.infoTech.vo.InfoTechSvo;
import com.bankle.common.config.ApiConfig;
import com.bankle.common.config.CommonConfig;
import com.bankle.common.dto.*;
import com.bankle.common.entity.*;
import com.bankle.common.enums.Sequence;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.mapper.*;
import com.bankle.common.repo.*;
import com.bankle.common.userAuth.UserAuthSvc;
import com.bankle.common.util.*;
import com.bankle.common.util.httpapi.HttpApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
@RequiredArgsConstructor
public class InfoTechSvc {
    private final TbEscrFileRepository tbEscrFileRepository;
    private final TbRgstrCaseInfoRepository tbRgstrCaseInfoRepository;
    private final TbRgstrCaseInfoHistRepository tbRgstrCaseInfoHistRepository;
    private final CustomeModelMapper customeModelMapper;
    private final TbEscrFeePaymentRepository tbEscrFeePaymentRepository;
    private final TbEscrMasterRepository tbEscrMasterRepository;
    private final TbRgstrIsnHistRepository tbRestrIsnHistRepository;
    private final UserAuthSvc userAuthSvc;
    private final FileUtil fileUtil;
    private final BizUtil bizUtil;

    /**
     * 등기고유번호 주소 검색 api
     *
     * @param : InfoTech.InfoTechInSvo
     * @return : InfoTech.InfoTechResCvo
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public InfoTechCvo.InfoTechResCvo fidAddr(String uniqNo) {

        InfoTechCvo.InfoTechResCvo resData;

        try {

            InfoTechSvo.SearchAddrInSvo reqData = InfoTechSvo.SearchAddrInSvo.builder()
                    .appCd(ApiConfig.INFOTECH_USERID2)
                    .orgCd("iros")
                    .svcCd("C0000")
                    .vAddrCls("9")
                    .kindcls("전체")
                    .adminRegn1("전체")
                    .adminRegn3("")
                    .clsFlag("현행")
                    .uniqNo(uniqNo)
                    .build();

            log.debug("reqData ===========> " + reqData.toString());

            var api = HttpApi.create(HttpMethod.POST, ApiConfig.INFOTECH_URL)
                    .header("Content-Type", "application/json")
                    .header("api-cloud-key", ApiConfig.INFOTECH_APIKEY)
                    .inserter(BodyInserters.fromValue(
                            reqData
                    ));

            var res = api.sync(InfoTechSvo.IrosC0000OutSvo.class);
            log.debug("res->{}", res.getBody().toString());
            InfoTechSvo.IrosC0000OutSvo irosC0000OutSvo = res.getBody();
            resData = customeModelMapper.mapping(irosC0000OutSvo, InfoTechCvo.InfoTechResCvo.class);
            log.debug("결과 : " + resData.toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
        return resData;
    }


    /**
     * 주소로 등기고유번호 검색 api
     *
     * @param : InfoTech.InfoTechInSvo
     * @return : InfoTech.InfoTechResCvo
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public List<InfoTechCvo.SearchAddrResCvo> fidUniqNo(String addr) {

        List<InfoTechCvo.SearchAddrResCvo> resData = new ArrayList<>();

        try {

            InfoTechSvo.SearchUniqNoInSvo reqData = InfoTechSvo.SearchUniqNoInSvo.builder()
                    .appCd(ApiConfig.INFOTECH_USERID2)
                    .orgCd("iros")
                    .svcCd("C0000")
                    .vAddrCls("3")
                    .kindcls("전체")
                    .adminRegn1("전체")
                    .adminRegn3("")
                    .clsFlag("현행")
                    .simpleAddress(addr)
                    .build();

            log.debug("reqData ===========> " + reqData.toString());

            var api = HttpApi.create(HttpMethod.POST, ApiConfig.INFOTECH_URL)
                    .header("Content-Type", "application/json")
                    .header("api-cloud-key", ApiConfig.INFOTECH_APIKEY)
                    .inserter(BodyInserters.fromValue(
                            reqData
                    ));

            var resMono = api.async(InfoTechSvo.IrosC0000OutSvo.class);
            resMono.just(getData()).doOnNext(vo -> log.debug(vo.toString()));
            var res = resMono.block();
            log.debug("res->{}", res.getBody().toString());
            InfoTechSvo.IrosC0000OutSvo irosC0000OutSvo = res.getBody();
            irosC0000OutSvo.getOut().getOutC0000().getList().forEach(dataItem -> resData.add(InfoTechCvo.SearchAddrResCvo.builder().uniqNo(dataItem.getPropertyUniqueId()).addr(dataItem.getPropertyLocation()).build()));

            log.debug("결과 : " + resData.toString());

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
        return resData;
    }

    public String getData(){
        log.debug("중간~");
        return "성공";
    }


    /**
     * 고유번호로 등기신청사건 조회 api
     *
     * @param : InfoTech.InfoTechInSvo
     * @return :
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public String fidRgstrIcdntInfo(InfoTechSvo.SearchRgstrChgYnInSvo req) {

        //변동여부
        String chgYn = "";

        try {

            if (!tbEscrMasterRepository.existsByEscrNo(req.getEscrNo())) {
                throw new DefaultException("에스크로 정보를 찾을 수 없습니다. 에스크로 번호를 확인해주세요.");
            }

            //-------------------------------------------------------------------------
            // 등기고유번호로 등기신청사건 조회 api 호출
            //-------------------------------------------------------------------------

            InfoTechSvo.SearchRgstrReqInfoSvo reqData = InfoTechSvo.SearchRgstrReqInfoSvo.builder()
                    .orgCd("iros") //기관코드
                    .svcCd("C0003") //서비스 코드
                    .userId(ApiConfig.INFOTECH_USERID2) //로그인아이디
                    .userPw(ApiConfig.INFOTECH_USERPW2) //로그인 비밀번호
                    .rstNo(req.getUniqNo()) // 등기고유번호
                    .ownerName(req.getOwnerName()) //등기소유자이름
                    .build();

            log.debug("reqData ===========> " + reqData.toString());

            var api = HttpApi.create(HttpMethod.POST, ApiConfig.INFOTECH_URL)
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("api-cloud-key", ApiConfig.INFOTECH_APIKEY)
                    .inserter(BodyInserters.fromValue(
                            reqData
                    ));

            var res = api.sync(InfoTechSvo.IrosC0003OutSvo.class);
            log.debug("res->{}", Objects.requireNonNull(res.getBody()).toString());
            //api 호출 결과
            InfoTechSvo.IrosC0003OutSvo irosC0003OutSvo = res.getBody();
            log.debug("========================= 등기신청사건 조회 결과 =========================");
            log.debug("등기신청사건 조회 결과 =====> " + irosC0003OutSvo.toString());
            log.debug("====================================================================");

            //-------------------------------------------------------------------------
            // 등기 정보를 저장한다.
            //-------------------------------------------------------------------------
            InfoTechSvo.OutC0003 resInfo = irosC0003OutSvo.getOut().getOutC0003();

            //-------------------------------------------------------------------------
            // 수수료 승인일자를 가져온다.
            //-------------------------------------------------------------------------
            long   feePayDt = 0; //수수료 승인일자 (납부일자)
            long   acptDt    = 0;  //접수일자

            Optional<TbEscrFeePayment> tbEscrFeePayment = tbEscrFeePaymentRepository.findTopByEscrNoOrderByCrtDtmDesc(req.getEscrNo());

            if(tbEscrFeePayment.isEmpty()){
               throw new DefaultException("에스크로 수수료 결제 기록을 찾을 수 없습니다.");
            }

            TbEscrFeePaymentDto tbEscrFeePaymentDto = TbEscrFeePaymentMapper.INSTANCE.toDto(tbEscrFeePayment.get());
            feePayDt =  Long.parseLong(DateUtil.formatOfPattern(tbEscrFeePaymentDto.getAprvDtm() , "yyyyMMdd"));

            if ("0000".equals(irosC0003OutSvo.getResCd())) { //조회 성공 시

                List<InfoTechSvo.C0003DataItem> list = resInfo.getList();

                if (list != null && !list.isEmpty()) {

                    List<InfoTechSvo.C0003DataItem> dataList = new ArrayList<>();

                    //열람내용 저장
                    ObjectMapper objectMapper = new ObjectMapper();
                    String rgstrReadCnts = objectMapper.writeValueAsString(irosC0003OutSvo);

                    for (InfoTechSvo.C0003DataItem data : list) {

                        acptDt = Long.parseLong(data.getAcptDt());

                        // 수수료납입일자 이후에 발생한 사건내역만 저장한다.
                        log.debug("=====>> 수수료 승인일자 [" + acptDt + "]   feePayDt [" + feePayDt + "]");

                        if (acptDt >= feePayDt) {
                            dataList.add(data);
                        }

                    }
                    if (dataList.size() > 0) { chgYn = saveRgstrIcdnt(dataList, req.getEscrNo() , rgstrReadCnts);}
                }else{
                    chgYn = "N";
                }

            } else {
                log.error("=====>> 등기사건조회오류 EscrNo [" + req.getEscrNo() + "]\nerrMsg : " + resInfo.getErrMsg());
                throw new DefaultException("등기사건 조회중 오류가 발생하였습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }
        return chgYn;
    }

    /**
     * 등기사건조회 기록 저장
     *
     * @param : 등기사건 정보 , 에스크로 번호 , 열람내용
     * @return :
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public String saveRgstrIcdnt(List<InfoTechSvo.C0003DataItem> req  , String escrNo , String rgstrReadCnts ) throws Exception {

        // 변경여부
        AtomicReference<String> chgYn = new AtomicReference<>("N");

        try {

            //----------------------------------------------------------------
            // 새로운 등기사건 데이터리스트를 만든다.
            //----------------------------------------------------------------
            String toDay = DateUtil.getDateNowStr("yyyyMMdd");
            List<TbRgstrCaseInfoDto> tbRgstrCaseInfoDtos = req.stream().map(data -> TbRgstrCaseInfoDto.builder()
                    .rgstrCaseNo(Long.parseLong(bizUtil.getSeq(Sequence.RGSTR_CASE))) //등기사건번호
                    .srchDt(toDay) //조회일자
                    .rgstrReadCnts(rgstrReadCnts) //등기부등본 열람내용
                    .acptDt(data.getAcptDt()) //접수일자
                    .acptNo(data.getAcptNo()) //접수번호
                    .cptRego(data.getCptRego()) //관활등기소
                    .regoDept(data.getRegoDept()) //등기소 부서
                    .lotnumAddr(data.getLotnumAddr()) //지번주소
                    .rgstrPrps(data.getRgstrPrps()) //등기목적
                    .procStat(data.getProcStat()) //처리상태
                    .escrNo(escrNo) //에스크로 번호
                    .build()
            ).toList();

            //----------------------------------------------------------------
            // 에스크로 번호에 해당하는 등기사건 정보를 가져온다.
            //----------------------------------------------------------------
            List<TbRgstrCaseInfo> tbRgstrCaseInfos = tbRgstrCaseInfoRepository.findByEscrNo(escrNo);

            if (tbRgstrCaseInfos != null && !tbRgstrCaseInfos.isEmpty()) {

                //----------------------------------------------------------------
                // 등기변동 여부를 판단한다.
                //----------------------------------------------------------------
                tbRgstrCaseInfos.forEach(info -> {
                    //등기사건번호에 해당하는 등기사건 이력을 가져온다.
                    Optional<TbRgstrCaseInfoHist> tbRgstrCaseInfoHistOpt = tbRgstrCaseInfoHistRepository.findByRgstrCaseNo(info.getRgstrCaseNo());

                    tbRgstrCaseInfoHistOpt.ifPresent(caseInfoHistEntity -> {

                        TbRgstrCaseInfoHistDto caseInfoHist = TbRgstrCaseInfoHistMapper.INSTANCE.toDto(caseInfoHistEntity);

                        boolean hasChanges = tbRgstrCaseInfoDtos.stream().anyMatch(data ->
                                // 최근 이력의 접수일자 접수번호가 같은 등기사건 필터
                                data.getAcptDt().equals(caseInfoHist.getAcptDt()) && data.getAcptNo().equals(caseInfoHist.getAcptNo()) && (
                                        // 최근 이력과 등기사건 데이터에 변경된 데이터 필터
                                        !data.getCptRego().equals(Optional.ofNullable(caseInfoHist.getCptRego()).orElse(data.getCptRego())) || // 관할 등기소
                                                !data.getRegoDept().equals(Optional.ofNullable(caseInfoHist.getRegoDept()).orElse(data.getRegoDept())) || // 등기소 부서
                                                !data.getLotnumAddr().equals(Optional.ofNullable(caseInfoHist.getLotnumAddr()).orElse(data.getLotnumAddr())) || // 지번 주소
                                                !data.getRgstrPrps().equals(Optional.ofNullable(caseInfoHist.getRgstrPrps()).orElse(data.getRgstrPrps())) || // 등기 목적
                                                !data.getProcStat().equals(Optional.ofNullable(caseInfoHist.getProcStat()).orElse(data.getProcStat())))); // 처리 상태

                        //변경된 내용이 있다면 변경여부를 Y으로 변경해준다.
                        if (hasChanges) {
                            chgYn.set("Y");
                        }
                    });
                });

            }

            // 변경여부를 추가한다.
            tbRgstrCaseInfoDtos.forEach(data -> data.setChgYn(chgYn.get()));

            //----------------------------------------------------------------
            // 등기사건이력데이터를 생성한다.
            //----------------------------------------------------------------
            List<TbRgstrCaseInfoHistDto> rgstrCaseInfoHistDtos = tbRgstrCaseInfoDtos.stream().map(data -> {
                        TbRgstrCaseInfoHistDto histDto = customeModelMapper.mapping(data, TbRgstrCaseInfoHistDto.class);
                        histDto.setCaseNoSeq(Long.parseLong(bizUtil.getSeq(Sequence.RGSTR_CASE_HIST)));
                        return histDto;
                    })
                    .toList();


            //----------------------------------------------------------------
            // 기존의 에스크로 번호에 해당하는 등기사건 데이터를 삭제한다.
            //----------------------------------------------------------------
            if(tbRgstrCaseInfoRepository.existsByEscrNo(escrNo)) { //에스크로 번호에 해당하는 데이터가 있는지 확인한다.
                tbRgstrCaseInfoRepository.deleteByEscrNo(escrNo);
            }

            //----------------------------------------------------------------
            // 등기사건 데이터를 저장한다.
            //----------------------------------------------------------------
            tbRgstrCaseInfoRepository.saveAll(TbRgstrCaseInfoMapper.INSTANCE.toEntityList(tbRgstrCaseInfoDtos));

            //----------------------------------------------------------------
            // 등기사건이력을 저장하여준다.
            //----------------------------------------------------------------
            tbRgstrCaseInfoHistRepository.saveAll(TbRgstrCaseInfoHistMapper.INSTANCE.toEntityList(rgstrCaseInfoHistDtos));


        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }

        return chgYn.get();
    }



    /**
     * 등기본등본 발급 API
     *
     * @param :
     * @return :
     * @throws
     */
    @Transactional(rollbackFor = Exception.class)
    public void oprRgstrIsn(InfoTechSvo.RgstrIsnInSvo req) throws Exception {

        String uniqNo = req.getRgstrUnqNo();

        try {

            //--------------------------------------
            // 에스크로 정보가 존재하는지 확인한다.
            //--------------------------------------

            if (!tbEscrMasterRepository.existsByEscrNo(req.getEscrNo())) {
                throw new DefaultException("에스크로 정보를 찾을 수 없습니다. 에스크로 번호를 확인해주세요.");
            }

            //--------------------------------------
            // 1시간 이내 사용 기록이 존재하는 경우 기존 아이디를 사용, 아니면 신규 아이디 사용
            //--------------------------------------
            int ironUserIndex = getToUseIronUserIndex(uniqNo);
            if (ironUserIndex < 0) {
                // 10회 시도 후에도 해당아이디가 사용중인 경우 : 잠시 후에 발급하도록 유도
                throw new DefaultException("현재 등기부등본 발급 사용자가 많습니다. 잠시 후에 시도하세요.");
            }

            String[] ironUserInfo = getIronUserInfo(ironUserIndex);

            InfoTechSvo.CcrstIsnApiInSvo reqData = InfoTechSvo.CcrstIsnApiInSvo.builder()
                    .appCd(ApiConfig.INFOTECH_USERID2)
                    .orgCd("iros")
                    .svcCd("B1001")
                    .uniqNo(uniqNo)
                    .userId(ironUserInfo[0])
                    .userPw(ironUserInfo[1])
                    .payNo(ApiConfig.INFOTECH_PAYNO)
                    .payPw(ApiConfig.INFOTECH_PAYPW)
                    .irosIP(ApiConfig.INFOTECH_IP)
                    .tifYn("Y")
                    .tifPath("C:\\infotech\\1.tif")
                    .tradeCheck("N")
                    .build();

            log.debug("reqData ===========> " + reqData.toString());

            var api = HttpApi.create(HttpMethod.POST, ApiConfig.INFOTECH_URL)
                    .header("Content-Type", "application/json")
                    .header("api-cloud-key", ApiConfig.INFOTECH_APIKEY)
                    .inserter(BodyInserters.fromValue(
                            reqData
                    ));

            var res = api.sync(InfoTechSvo.CcrstIsnApiOutSvo.class);
            InfoTechSvo.CcrstIsnApiOutSvo ccrstIsnRes = res.getBody();
            log.debug("등기부등본 발급 결과 : " + ccrstIsnRes.toString());

            if ("Y".equals(ccrstIsnRes.getOut().getErrYn())) {
                throw new DefaultException("등기부등본 발급 중 예외 상황이 발생하였습니다. \n잠시 후 다시 시도해 주세요.");
            }

            //--------------------------------------
            // 등기부 등본 발급현황을 저장한다.
            //--------------------------------------

            //내용
            ObjectMapper mapper = new ObjectMapper();
            String content = mapper.writeValueAsString(ccrstIsnRes);

            InfoTechSvo.RgstrIsnHistInfoInSvo rgstrIsnHistInfoInSvo = InfoTechSvo.RgstrIsnHistInfoInSvo.builder()
                    .rgstrUnqNo(uniqNo)
                    .readDtm(LocalDateTime.now())
                    .issuIdNum(ironUserIndex)
                    .content(content)
                    .build();

            tbRestrIsnHistRepository.save(TbRgstrIsnHistMapper.INSTANCE.toEntity(customeModelMapper.mapping(rgstrIsnHistInfoInSvo, TbRgstrIsnHistDto.class)));

            if(!"0000".equals(ccrstIsnRes.getResCd())){ //결과가 정상이 아니라면
                throw new DefaultException("등기부등본 발급 중 예외 상황이 발생하였습니다. \n잠시 후 다시 시도해 주세요.");
            }

            //--------------------------------------
            // 문자열 형태로 되어있는 파일을 파일형태로 저장한다.
            //--------------------------------------

            hexToFile(InfoTechSvo.HexToFileSvo.builder()
                    .membNo(getMembNo())
                    .outData(ccrstIsnRes.getOut())
                    .escrNo(req.getEscrNo())
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
            throw new DefaultException(e.getMessage());
        }

    }


    // 등기부 등본 발급 아이디 사용 여부 배열
    static Boolean[] userInfoUse = {false, false, false, false, false, false, false, false, false, false};

    private final ReentrantLock lock = new ReentrantLock();

    // 등기부 등본 발급 아이디 사용 여부 배열의 Boolean 값 설정
    public static synchronized void setUserInfoUse(int index, boolean value) {
        userInfoUse[index] = value;
    }

    public static synchronized boolean isUserInfoUse(int index) {
        return userInfoUse[index];
    }

    // 신규 ID Index 가져오기
    private int getNewIronUserIndex(String uniqNo) throws InterruptedException {

        int useIndex = (int) (Long.parseLong(uniqNo) % 10);
        if (Boolean.FALSE.equals(userInfoUse[useIndex])) {
            setUserInfoUse(useIndex, true);
            return useIndex;
        }

        useIndex = -1;
        int iValue = -1;
        int loofCount = 10;

        for (int i = 0; i < loofCount; i++) {

            // 순차적으로 비교하면 앞의 순번에 배정이 많아지므로 random하게 선택하도록 함
            iValue = (int) (Math.random() * 10);

            if (Boolean.FALSE.equals(userInfoUse[iValue])) {
                setUserInfoUse(iValue, true);
                useIndex = iValue;
                break;
            }

            if (i < loofCount - 1) {
                Thread.sleep(1000);
            }

        }

        return useIndex;
    }

    private String getMembNo() {

        String membNo = "System";
        try {
            membNo = userAuthSvc.getSessionUser().getMembNo();
        } catch (Exception e) {

        }
        return membNo;
    }

    // 사용할 ID, Password 설정
    public String[] getIronUserInfo(int useIndex) {

        String[] ironUserInfo = new String[2];

        switch (useIndex) {
            case 0:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID1;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW1;
                break;
            case 1:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID2;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW2;
                break;
            case 2:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID3;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW3;
                break;
            case 3:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID4;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW4;
                break;
            case 4:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID5;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW5;
                break;
            case 5:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID6;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW6;
                break;
            case 6:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID7;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW7;
                break;
            case 7:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID8;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW8;
                break;
            case 8:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID9;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW9;
                break;
            case 9:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID10;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW10;
                break;
            default:
                ironUserInfo[0] = ApiConfig.INFOTECH_USERID1;
                ironUserInfo[1] = ApiConfig.INFOTECH_USERPW1;
                break;
        }

        return ironUserInfo;
    }

    // 사용할 사용자 Index 가져오기
    public int getToUseIronUserIndex(String uniqNo) throws Exception {
        // 1시간 이내 사용한 ID Index 가져오기
        int userIndex = getUsedIronUserIndex(uniqNo);
        if (userIndex > -2) {
            return userIndex;
        }
        return getNewIronUserIndex(uniqNo);
    }


    // 1시간 이내 사용한 ID Index 가져오기
    public int getUsedIronUserIndex(String uniqNo) throws Exception {
        int userIndex = -2;

        // 등기부등본 발급 현황 조회
        log.debug("### 부동산 고유번호:[" + uniqNo + "]");
        Optional<TbRgstrIsnHist> restrHistDtm = tbRestrIsnHistRepository.getRestrHistDtm(uniqNo);

        // 사용한 ID가 없는 경우
        if (restrHistDtm.isEmpty()) {
            log.debug("### 부동산 고유번호 [" + uniqNo + "] (으)로 1시간이내 발급 기록 없음");
            return -2;
        }

        InfoTechSvo.RgstrHistInfoSvo rgstrHistInfo = customeModelMapper.mapping(TbRgstrIsnHistMapper.INSTANCE.toDto(restrHistDtm.get()), InfoTechSvo.RgstrHistInfoSvo.class);

        // 열람일시에 1시간을 더한 값을 1시간 후 열람일시 값에 넣어준다.
        rgstrHistInfo.setAfter1HourReadDtm(rgstrHistInfo.getReadDtm().plusHours(1));

        LocalDateTime after1Hour = rgstrHistInfo.getAfter1HourReadDtm();
        userIndex = rgstrHistInfo.getIssuIdNum();

        log.debug("### 무료로 발급 가능한 시간:[" + after1Hour.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]");
        log.debug("### 무료로 발급 가능한 ID 순번:[" + userIndex + "]");

        // 사용한 ID가 있는 경우 해당 아이디 사용 가능 여부를 10번 체크
        boolean availability = false;
        int loopCount = 10;

        LocalDateTime curTm;
        for (int i = 0; i < loopCount; i++) {
            curTm = LocalDateTime.now();
            log.debug("### 해당 순번의 ID가 사용 가능한지 체크한 시간:[" + curTm.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "]");
            // 현재 시간이 사용 시간 + 1시간 이후이면 중단 (이후 신규 아이디로 발급)
            if (curTm.isAfter(after1Hour)) {
                log.debug("### 현재 시간이 발급 가능 시간 이후입니다. 신규 아이디를 발급합니다.");
                return -2;
            }

            synchronized (userInfoUse) {
                if (!isUserInfoUse(userIndex)) {
                    setUserInfoUse(userIndex, true);
                    availability = true;
                    break;
                }
            }

            if (i < loopCount - 1) {
                Thread.sleep(1000);  // 1초 대기
            }
        }

        if (!availability) {
            log.debug("### 10번 체크 후에도 ID를 사용할 수 없습니다.");
            return -1;
        }

        return userIndex;
    }

    /**
     * 등기부등본 파일저장 서비스
     */
    public void hexToFile(InfoTechSvo.HexToFileSvo req) throws Exception {

        //----------------------------------------------------------------
        //  hexstring을 tif file로 저장한다.
        //----------------------------------------------------------------
        String tifHexString = req.getOutData().getTifHexString();

        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = Long.toString(System.nanoTime()) + ".tif";

        // tiff 파일 경로
        String tiffPath = FileUtil.getloadDir("IMAGE_BIZ", "01") + File.separator + currentDate + File.separator;

        if ("local".equals(CommonConfig.ENV)) {    //테스트를 위한 경로 하드코딩
            tiffPath = "/Users/rojoon" + tiffPath;
        }

        log.debug("tiffPath :" + tiffPath);

        File file = new File(tiffPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(tiffPath + fileName);

        if (file.exists()) {
            file.delete();
        }

        int len = tifHexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(tifHexString.charAt(i), 16) << 4) + Character.digit(tifHexString.charAt(i + 1), 16));
        }

        Files.write(Paths.get(tiffPath + fileName), data, StandardOpenOption.CREATE);
        // hexstring을 tif file로 저장 ]


        // tif 를 png로 저장
        try (InputStream is = new FileInputStream(file)) {
            try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(is)) {
                Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
                if (iterator == null || !iterator.hasNext()) {
                    throw new RuntimeException("지원되지 않는 이미지 파일 형식 입니다. : " + file.getAbsolutePath());
                }

                // We are just looking for the first reader compatible:
                ImageReader reader = iterator.next();
                reader.setInput(imageInputStream);

                int numPage = reader.getNumImages(true);

                String name = FilenameUtils.getBaseName(file.getAbsolutePath());

                //이미지 그룹번호
                String grpNo = fileUtil.getGrpNo( "IMAGE_BIZ","01");

                //이미지 정보 리스트
                List<TbEscrFileDto> imgInfoLst = new ArrayList<>();

                for (int imageIndex = 0; imageIndex < numPage; imageIndex++) {

                    String strimageIndex = String.format("%03d", imageIndex);

                    String storedFileName = name + strimageIndex + ".png";
                    String storedImgFilePath = tiffPath + storedFileName;
                    String storedEncFilePath = storedImgFilePath + ".enc";

                    File imgfile = new File(storedImgFilePath);
                    BufferedImage tiff = reader.read(imageIndex);
                    ImageIO.write(tiff, "png", imgfile);

                    // 파일암호화
                    byte[] keyData = ApiConfig.TIFF_SECRET_KEY.getBytes();

                    SecretKey secretKey = new SecretKeySpec(keyData, "AES");
                    FileEncrypterDecrypter fileEncrypterDecrypter = new FileEncrypterDecrypter(secretKey);
                    fileEncrypterDecrypter.encrypt(storedImgFilePath, storedEncFilePath);

                    //이미지 시퀀스
                    Long seq = Long.parseLong(bizUtil.getSeq(Sequence.IMAGE));


                    TbEscrFileDto imgSave = TbEscrFileDto.builder()
                            .seq(seq)
                            .grpNo(grpNo)
                            .wkCd("IMAGE_BIZ")
                            .fileCd("01")
                            .fileNm(storedFileName)
                            .fileOrgnNm(storedFileName)
                            .fileExt("png")
                            .filePth(storedEncFilePath)
                            .fileSize(BigDecimal.valueOf(Math.round(imgfile.length() / 1024.0)))
                            .membNo(getMembNo())
                            .escrNo(req.getEscrNo())
                            .delYn("N")
                            .build();

                    imgInfoLst.add(imgSave);

                   // imgfile.delete();// 암호화안된 png파일 삭제
                }

                //파일정보를 저장한다.
                tbEscrFileRepository.saveAll(TbEscrFileMapper.INSTANCE.toEntityList(imgInfoLst));

                } catch(IOException e){
                    e.printStackTrace();
                    throw new IOException(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
            // tif 파일 삭제
            file.delete();
        }

    }




