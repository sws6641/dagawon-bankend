package com.bankle.common.comBiz.infoTech.vo;

import com.bankle.common.util.httpapi.vo.BaseResponseVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class InfoTechSvo {


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchAddrInSvo {
        @JsonProperty("appCd")
        @Schema(description = "어플리케이션 코드")
        String appCd;

        @JsonProperty("orgCd")
        @Schema(description = "기관코드")
        String orgCd;

        @JsonProperty("svcCd")
        @Schema(description = "서비스 코드")
        String svcCd;

        @JsonProperty("vAddrCls")
        @Schema(description = "")
        String vAddrCls;

        @JsonProperty("kindcls")
        @Schema(description = "")
        String kindcls;

        @JsonProperty("admin_regn1")
        @Schema(description = "")
        String adminRegn1;

        @JsonProperty("admin_regn3")
        @Schema(description = "")
        String adminRegn3;

        @JsonProperty("cls_flag")
        @Schema(description = "")
        String clsFlag;

        @JsonProperty("uniqNo")
        @Schema(description = "고유번호")
        String uniqNo;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchUniqNoInSvo {
        @JsonProperty("appCd")
        @Schema(description = "어플리케이션 코드")
        String appCd;

        @JsonProperty("orgCd")
        @Schema(description = "기관코드")
        String orgCd;

        @JsonProperty("svcCd")
        @Schema(description = "서비스 코드")
        String svcCd;

        @JsonProperty("vAddrCls")
        @Schema(description = "")
        String vAddrCls;

        @JsonProperty("kindcls")
        @Schema(description = "")
        String kindcls;

        @JsonProperty("admin_regn1")
        @Schema(description = "")
        String adminRegn1;

        @JsonProperty("admin_regn3")
        @Schema(description = "")
        String adminRegn3;

        @JsonProperty("cls_flag")
        @Schema(description = "")
        String clsFlag;

        @JsonProperty("simple_address")
        @Schema(description = "주소")
        String simpleAddress;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IrosC0000OutSvo extends BaseResponseVo {

        @JsonProperty("resCd")
        private String resCd;

        @JsonProperty("resMsg")
        private String resMsg;

        @JsonProperty("out")
        private OutC0000Data out;

        @JsonProperty("reqTm")
        private String reqTm;

        @JsonProperty("reqTmSs")
        private String reqTmSs;

        @JsonProperty("resTm")
        private String resTm;

        @JsonProperty("resTmSs")
        private String resTmSs;

        @JsonProperty("reqCd")
        private String reqCd;


    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutC0000Data {
        @JsonProperty("uid")
        private String uid;

        @JsonProperty("outC0000")
        private OutC0000 outC0000;

        @JsonProperty("orgCd")
        private String orgCd;

        @JsonProperty("resCd")
        private String resCd;

        @JsonProperty("errMsg")
        private String errMsg;

        @JsonProperty("appCd")
        private String appCd;

        @JsonProperty("reqCd")
        private String reqCd;

        @JsonProperty("svcCd")
        private String svcCd;

        @JsonProperty("resMsg")
        private String resMsg;

        @JsonProperty("device")
        private String device;

        @JsonProperty("errYn")
        private String errYn;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutC0000 {
        @JsonProperty("errYn")
        private String errYn;

        @JsonProperty("errMsg")
        private String errMsg;

        @JsonProperty("list")
        private List<C0000DataItem> list;

        @JsonProperty("totCnt")
        private String totCnt;

        // Getters and Setters
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class C0000DataItem {
        @JsonProperty("부동산고유번호")
        private String propertyUniqueId;

        @JsonProperty("구분")
        private String category;

        @JsonProperty("부동산소재지번")
        private String propertyLocation;

        @JsonProperty("소유자")
        private String owner;

        @JsonProperty("상태")
        private String status;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRgstrChgYnInSvo {
        @Schema(description = "에스크로번호" , example = "에스크로번호")
        @Length(min = 14, max = 14)
        private String escrNo;
        @Schema(description = "등기고유번호")
        @Size(min = 14, max = 14)
        private String uniqNo;
        @Schema(description = "매도인명" , example = "매도인명")
        @Length(min = 1, max = 100)
        private String ownerName;
//        @Schema(description = "1: 등기신청인 , 2:소유자", example = "1: 등기신청인 , 2:소유자")
//        @Length(min = 1, max = 1)
//        private String nameTape;
    }


    /*
    고유번호로 등기신청사건 조회 API
     */
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRgstrReqInfoSvo {
        @JsonProperty("appCd")
        @Schema(description = "어플리케이션 코드")
        String appCd;

        @JsonProperty("orgCd")
        @Schema(description = "기관코드")
        String orgCd;

        @JsonProperty("svcCd")
        @Schema(description = "서비스 코드")
        String svcCd;

        @JsonProperty("userId")
        @Schema(description = "")
        String userId;

        @JsonProperty("userPw")
        @Schema(description = "")
        String userPw;

        @JsonProperty("selRegt")
        @Schema(description = "")
        String selRegt;

        @JsonProperty("inpRecevNo")
        @Schema(description = "")
        String inpRecevNo;

        @JsonProperty("rstNo")
        @Schema(description = "")
        String rstNo;

        @JsonProperty("nameType")
        @Schema(description = "등기신청인/소유자구분(1:등기신청인,2:소유자)")
        String nameType;

        @JsonProperty("ownerName")
        @Schema(description = "등기소유자이름")
        String ownerName;

        @JsonProperty("passYn")
        @Schema(description = "")
        String passYn;

    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IrosC0003OutSvo extends BaseResponseVo {

        @JsonProperty("resCd")
        private String resCd;

        @JsonProperty("resMsg")
        private String resMsg;

        @JsonProperty("out")
        private OutC0003Data out;

        @JsonProperty("reqTm")
        private String reqTm;

        @JsonProperty("reqTmSs")
        private String reqTmSs;

        @JsonProperty("resTm")
        private String resTm;

        @JsonProperty("resTmSs")
        private String resTmSs;

        @JsonProperty("reqCd")
        private String reqCd;


    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutC0003Data {
        @JsonProperty("uid")
        private String uid;

        @JsonProperty("outC0003")
        private OutC0003 outC0003;

        @JsonProperty("orgCd")
        private String orgCd;

        @JsonProperty("resCd")
        private String resCd;

        @JsonProperty("errMsg")
        private String errMsg;

        @JsonProperty("appCd")
        private String appCd;

        @JsonProperty("reqCd")
        private String reqCd;

        @JsonProperty("svcCd")
        private String svcCd;

        @JsonProperty("resMsg")
        private String resMsg;

        @JsonProperty("device")
        private String device;

        @JsonProperty("errYn")
        private String errYn;

    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutC0003 {
        @JsonProperty("errYn")
        private String errYn;

        @JsonProperty("errMsg")
        private String errMsg;

        @JsonProperty("list")
        private List<C0003DataItem> list;

        @JsonProperty("변동여부")
        private String fluctuationsYn;

        @JsonProperty("outStr")
        private String outStr;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class C0003DataItem {
        @JsonProperty("일련번호")
        private String uniqNo;

        @JsonProperty("접수번호")
        private String acptNo;

        @JsonProperty("접수일자")
        private String acptDt;

        @JsonProperty("관할등기소")
        private String cptRego;

        @JsonProperty("관할등기소번호")
        private String cptRegoNo;

        @JsonProperty("계")
        @Schema(description = "등기소 부서")
        private String regoDept;

        @JsonProperty("소재지번")
        @Schema(description = "지번주소")
        private String lotnumAddr;

        @JsonProperty("소재지번목록")
        private List<SeatLotn> lotnumAddrList;

        @JsonProperty("등기목적")
        private String rgstrPrps;

        @JsonProperty("처리상태")
        private String procStat;

        @JsonProperty("국민주택채권매입환급액")
        private String natlHusBondPurchaseRefundAmt;

        @JsonProperty("국민주택채권")
        private List<NatlHousingBond> natlHousingBondList;

        @JsonProperty("신청구분")
        private String applicationType;

        @JsonProperty("등기필정보등교부상태")
        private String registrationInformationDeliveryStatus;

        @JsonProperty("소유자")
        private String owner;

        @JsonProperty("등기목적코드")
        private String registrationPurposeCode;

        @JsonProperty("상태")
        private String status;


    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatLotn {
        @JsonProperty("부동산고유번호")
        private String realEstateUniqueId;

        @JsonProperty("부동산소재지번")
        private String realEstateAddr;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NatlHousingBond {
        @JsonProperty("순번")
        private String seq;

        @JsonProperty("채권번호")
        private String bndNum;

        @JsonProperty("은행")
        private String bank;

        @JsonProperty("매입액")
        private String purchaseAmt;

        @JsonProperty("환급액")
        private String refundAmt;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchCcrstIsnYnInSvo {
        @Schema(description = "등기고유번호", example = "13451996177867")
        @Size(min = 14, max = 14)
        private String uniqNo;
        @Schema(description = "차주명", example = "차주명")
        @Size(min = 1, max = 100)
        private String dbtrNm;
        @Schema(description = "당보제공자명", example = "담보제공자명")
        private String pwpsNm;
        @Schema(description = "법무사명", example = "법무사명")
        private String lwyrNm;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RgstrHistInfoSvo {
        @Schema(description = "열람일시")
        private LocalDateTime readDtm;
        @Schema(description = "1시간 후 열람일시")
        private LocalDateTime after1HourReadDtm;
        @Schema(description = "발급 아이디 순번")
        private Integer issuIdNum;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RgstrIsnInSvo {
        @Schema(description = "에스크로번호")
        private String escrNo;
        @Schema(description = "등기고유번호")
        private String rgstrUnqNo;
    }


    /*
    고유번호로 등기신청사건 조회 API 요청데이터
     */
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CcrstIsnApiInSvo {

        @JsonProperty("appCd")
        private String appCd;

        @JsonProperty("orgCd")
        private String orgCd;

        @JsonProperty("svcCd")
        private String svcCd;

        @JsonProperty("uniqNo")
        private String uniqNo;

        @JsonProperty("userId")
        private String userId;

        @JsonProperty("userPw")
        private String userPw;

        @JsonProperty("payNo")
        private String payNo;

        @JsonProperty("payPw")
        private String payPw;

        @JsonProperty("irosIP")
        private String irosIP;

        @JsonProperty("tifYn")
        private String tifYn;

        @JsonProperty("tifPath")
        private String tifPath;

        @JsonProperty("tradeCheck")
        private String tradeCheck;

    }


    /*
    고유번호로 등기신청사건 조회 API 응답 데이터
     */
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CcrstIsnApiOutSvo extends BaseResponseVo {

        @JsonProperty("resCd")
        private String resCd;

        @JsonProperty("resMsg")
        private String resMsg;

        @JsonProperty("out")
        private OutData out;

        @JsonProperty("reqTm")
        private String reqTm;

        @JsonProperty("reqTmSs")
        private String reqTmSs;

        @JsonProperty("resTm")
        private String resTm;

        @JsonProperty("resTmSs")
        private String resTmSs;

        @JsonProperty("reqCd")
        private String reqCd;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutData {

        @JsonProperty("errMsg")
        private String errMsg;

        @JsonProperty("appCd")
        private String appCd;

        @JsonProperty("reqCd")
        private String reqCd;

        @JsonProperty("svcCd")
        private String svcCd;

        @JsonProperty("resMsg")
        private String resMsg;

        @JsonProperty("outList")
        private OutList outList;

        @JsonProperty("tifHexString")
        private String tifHexString;

        @JsonProperty("uid")
        private String uid;

        @JsonProperty("orgCd")
        private String orgCd;

        @JsonProperty("apprNo")
        private String apprNo;

        @JsonProperty("resCd")
        private String resCd;

        @JsonProperty("device")
        private String device;

        @JsonProperty("errYn")
        private String errYn;

        @JsonProperty("xmlData")
        private String xmlData;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OutList {

        @JsonProperty("과다등기여부")
        private String excessiveRegistration;

        @JsonProperty("중복등기부")
        private String duplicateRegistration;

        @JsonProperty("고유번호")
        private String uniqNo;

        @JsonProperty("거래가액")
        private String transactionAmt;

        @JsonProperty("대지권비율")
        private String landOwnershipRatio;

        @JsonProperty("지번_및_번호")
        private String parcelAndNumber;

        @JsonProperty("건물내역")
        private String buildingDetails;

        @JsonProperty("요약정보")
        private SummaryInfo summaryInfo;

        @JsonProperty("소유지분현황_갑구")
        private List<OwnershipInfo> ownershipInfoList;

        @JsonProperty("근_저당권_및_전세권_등_을구")
        private List<LienAndLeaseInfo> lienAndLeaseInfoList;

        @JsonProperty("표제부_토지의_표시")
        private List<String> landMarkingList;

        @JsonProperty("표제부_1동의_건물의_표시")
        private List<BuildingMarking> buildingMarkingList;

        @JsonProperty("표제부_대지권의_목적인_토지의_표시")
        private List<LandMarkingPurpose> landMarkingPurposeList;

        @JsonProperty("표제부_전유부분_건물의_표시")
        private List<BuildingPartialMarking> buildingPartialMarkingList;

        @JsonProperty("표제부_대지권의_표시")
        private List<LandOwnershipMarking> landOwnershipMarkingList;

        @JsonProperty("소유권에_관한_사항_갑구")
        private List<OwnershipDetails> ownershipDetailsList;

        @JsonProperty("권리변동_예정사항")
        private String scheduledChanges;

        @JsonProperty("열람일시")
        private String viewingDateTime;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SummaryInfo {

        @JsonProperty("개별공시지가")
        private List<String> individualPublicLandPrice;

        @JsonProperty("토지이용계획")
        private Map<String, String> landUsePlan;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnershipInfo {

        @JsonProperty("등기명의인")
        private String registeredOwner;

        @JsonProperty("주민_등록번호")
        private String birthday;

        @JsonProperty("최종지분")
        private String finalOwnershipRatio;

        @JsonProperty("순위번호")
        private String rankNumber;



    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LienAndLeaseInfo {

        @JsonProperty("순위번호")
        private String rankNo;

        @JsonProperty("등기목적")
        private String purposeOfRegistration;

        @JsonProperty("접수정보")
        private String receiptInfo;

        @JsonProperty("주요등기사항")
        private String majorRegistrationInfo;

        @JsonProperty("주요등기사항밑줄")
        private String majorRegistrationUnderline;

        @JsonProperty("대상소유자")
        private String targetOwner;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuildingMarking {

        @JsonProperty("표시번호")
        private String markingNumber;

        @JsonProperty("접수")
        private String receipt;

        @JsonProperty("소재지번_건물명칭_및_번호")
        private String parcelAndBuildingNameNumber;

        @JsonProperty("건물내역")
        private String buildingDetails;

        @JsonProperty("등기원인_및_기타사항")
        private String registrationCauseAndOtherMatters;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandMarkingPurpose {

        @JsonProperty("표시번호")
        private String markingNumber;

        @JsonProperty("소재지번")
        private String parcelNumber;

        @JsonProperty("지목")
        private String designation;

        @JsonProperty("면적")
        private String area;

        @JsonProperty("등기원인_및_기타사항")
        private String registrationCauseAndOtherMatters;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class BuildingPartialMarking {

        @JsonProperty("표시번호")
        private String markingNumber;

        @JsonProperty("접수")
        private String receipt;

        @JsonProperty("건물번호")
        private String buildingNumber;

        @JsonProperty("건물내역")
        private String buildingDetails;

        @JsonProperty("등기원인_및_기타사항")
        private String registrationCauseAndOtherMatters;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LandOwnershipMarking {

        @JsonProperty("표시번호")
        private String markingNumber;

        @JsonProperty("대지권종류")
        private String landOwnershipType;

        @JsonProperty("대지권비율")
        private String landOwnershipRatio;

        @JsonProperty("등기원인_및_기타사항")
        private String registrationCauseAndOtherMatters;

    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnershipDetails {

        @JsonProperty("순위번호")
        private String rankNumber;

        @JsonProperty("등기목적")
        private String registrationPurpose;

        @JsonProperty("접수")
        private String receipt;

        @JsonProperty("등기원인")
        private String registrationCause;

        @JsonProperty("권리자_및_기타사항")
        private String rightHolderAndOtherMatters;

    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RgstrIsnHistInfoInSvo {
        @Schema(description = "고유번호")
        private String rgstrUnqNo;
        @Schema(description = "열람일시")
        private LocalDateTime readDtm;
        @Schema(description = "발급 아이디 순번")
        private Integer issuIdNum;
        @Schema(description = "생성회원번호")
        private String crtMembNo;
        @Schema(description = "내용")
        private String content;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HexToFileSvo {
        @Schema(description = "등기부든본 데이터")
        private OutData outData;
        @Schema(description = "에스크로 번호")
        private String escrNo;
        @Schema(description = "회원번호")
        private String membNo;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImgSaveInSvo {
        @Schema(description = "이미지일련번호")
        private Long seq;
        @Schema(example = "이미지 그룹번호")
        private Long grpNo;
        @Size(min = 1,max = 2)
        @Schema(description = "첨부파일코드")
        private String attcFilCd;
        @Size(min = 9 ,max = 10)
        @Schema(description = "업무코드")
        private String wkCd;
        @Size(min = 1, max = 500)
        @Schema(description = "첨부파일위치명")
        private String attcFilLocNm;
        @Size(min = 1 ,max = 500)
        @Schema(description = "첨부파일명")
        private String attcFilNm;
        @Size(min = 1 ,max = 500)
        @Schema(description = "변경첨부파일명")
        private String chgAttcFilNm;
        @Size(max = 500)
        @Schema(description = "은행첨부파일명")
        private String bankAttcFilNm;
        @Size(max = 500)
        @Schema(description = "은행첨부파일코드")
        private  String bankAttcFilCd;
        @Size(max = 3)
        @Schema(description = "은행코드")
        private String bankCd;
        @Size(min = 1 ,max = 250)
        @Schema(description = "파일사이즈")
        private  String filSize;
        @Size(max = 10)
        @Schema(description = "사업자등록번호")
        private String bizNo;
        @Size(max = 11)
        @Schema(description = "여신번호")
        private String loanNo;
        @Schema(description = "관리자요청참조일련번호")
        private Long adminReqRfrSeq;
        @Size(max = 10)
        @Schema(description = "보험시작일(보험가입증명서)")
        private String stDtm;
        @Size(max = 10)
        @Schema(description = "보험종료일(보험가입증명서)")
        private String endDtm;
        @Size(max = 20)
        @Schema(description = "등기처리번호")
        private String rgstrProcNo;
        @Schema(description = "계약번호")
        @Size(max = 20)
        private String contNo;
        @Size(max = 1)
        @Schema(description = "OCR성공여부")
        private String ocrSuccYn;
        @Size(max = 1000)
        @Schema(description = "첨부파일키1내용")
        private String attcFilKey1Cnts;
        @Size(max = 1000)
        @Schema(description = "첨부파일키2내용")
        private  String attcFilKey2Cnts;
        @Size(max = 1000)
        @Schema(description = "첨부파일키3내용")
        private String attcFilKey3Cnts;
        @Size(max = 1)
        @Schema(description = "삭제여부")
        private String delYn;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TbWoTrnFa6100F1InfoSvo {

        private TbWoTrnFa6100F1IdInfoSvo id;

        private BigDecimal tgLen;

        private String tgDsc;

        private String bnkTgNo;

        private String faTgNo;

        private String kosTgSndNo;

        private LocalDateTime tgSndDtm;

        private LocalDateTime tgRcvDtm;

        private String resCd;

        private String rsrvItmH;

        private String bnkTtlReqNo;

        private String ttlArdEntrEane;

        private String ttlEntrcmpy;

        private String ttlScrtNo;

        private String lndDsc;

        @Size(max = 2)
        private String lndKndCd;

        @Size(max = 2)
        private String fndUseCd;

        @Size(max = 9)
        private String bnkLndPrdtCd;

        @Size(max = 200)
        private String bnkLndPrdtNm;

        @Size(max = 2)
        private String grntDsc;

        @Size(max = 1)
        private String stndAplYn;

        @Size(max = 1)
        private String rrcpCnfmReqYn;

        @Size(max = 1)
        private String mvhrCnfmReqYn;

        @Size(max = 1)
        private String bfSrvtrgtReqYn;

        @Size(max = 1)
        private String afSrvtrgtReqYn;

        @Size(max = 14)
        private String rgstrUnqNo1;

        @Size(max = 14)
        private String rgstrUnqNo2;

        @Size(max = 14)
        private String rgstrUnqNo3;

        @Size(max = 14)
        private String rgstrUnqNo4;

        @Size(max = 14)
        private String rgstrUnqNo5;

        @Size(max = 1)
        private String rlesDsc;

        @Size(max = 2)
        private String trgtRlesDsc;

        @Size(max = 300)
        private String trgtRlesAddr;

        @Size(max = 8)
        private String sscptAskDt;

        @Size(max = 8)
        private String lndPlnDt;

        @Size(max = 8)
        private String lndExprdDt;

        private BigDecimal slPrc;

        private BigDecimal isrnEntrAmt;

        private BigDecimal lndPrd;

        private BigDecimal lndAmt;

        private BigDecimal bnkFxcltRgstrRnk;

        private BigDecimal bnkFxcltBndMaxAmt;

        @Size(max = 50)
        private String dbtrNm;

        @Size(max = 13)
        private String dbtrBirthDt;

        @Size(max = 300)
        private String dbtrAddr;

        @Size(max = 14)
        private String dbtrPhno;

        @Size(max = 14)
        private String dbtrHpno;

        @Size(max = 50)
        private String pwpsNm;

        @Size(max = 13)
        private String pwpsBirthDt;

        @Size(max = 300)
        private String pwpsAddr;

        @Size(max = 14)
        private String pwpsPhno;

        @Size(max = 14)
        private String pwpsHpno;

        @Size(max = 200)
        private String rmkFct;

        @Size(max = 1)
        private String lndHndgSlfDsc;

        @Size(max = 50)
        private String bnkBrnchNm;

        @Size(max = 50)
        private String bnkDrctrNm;

        @Size(max = 14)
        private String bnkBrnchPhno;

        @Size(max = 14)
        private String bnkDrctrHp;

        @Size(max = 14)
        private String bnkBrnchFax;

        @Size(max = 100)
        private String bnkBrnchAddr;

        @Size(max = 50)
        private String slmnCmpyNm;

        @Size(max = 50)
        private String slmnNm;

        @Size(max = 14)
        private String slmnPhno;

        @Size(max = 50)
        private String lwfmNm;

        @Size(max = 12)
        private String lwfmBizno;

        @Size(max = 1)
        private String dbtrWdngPlnYn;

        @Size(max = 1)
        private String rrcpCnfmYn;

        @Size(max = 50)
        private String spusNm;

        @Size(max = 8)
        private String wdngPlnDt;

        @Size(max = 8)
        private String rschWkDdlnReqDt;

        private BigDecimal isrnPrmm;

        @Size(max = 20)
        private String rfrLnAprvNo;

        @Size(max = 1)
        private String rgstrMtdDsc;

        @Size(max = 20)
        private String rgstrReqNo;

        @Size(max = 1)
        private String odprtRpyEane;

        @Size(max = 50)
        private String eltnEstbsLwyrNm;

        @Size(max = 12)
        private String eltnEstbsLwyrBizno;

        @Size(max = 1)
        private String eltnRpyLoaAplYn;

        @Size(max = 16)
        private String eltnRpyLoaSqn;

        @Size(max = 6)
        private String eltnRpyLoaCtfcNo;

        private BigDecimal whlRpyCnt;

        private BigDecimal whlRpyAmt;

        private BigDecimal ebnkRpyTotAmt;

        private BigDecimal dfbnkRpyTotAmt;

        @Size(max = 2)
        private String rpyTrgtRnkNo1;

        @Size(max = 8)
        private String rpyTrgtAcptDt1;

        @Size(max = 6)
        private String rpyTrgtAcptNo1;

        private BigDecimal rpyTrgtBndAmt1;

        @Size(max = 2)
        private String rpyTrgtRnkNo2;

        @Size(max = 8)
        private String rpyTrgtAcptDt2;

        @Size(max = 6)
        private String rpyTrgtAcptNo2;

        private BigDecimal rpyTrgtBndAmt2;

        @Size(max = 2)
        private String rpyTrgtRnkNo3;

        private String rpyTrgtAcptDt3;

        private String rpyTrgtAcptNo3;

        private BigDecimal rpyTrgtBndAmt3;

        private String rpyTrgtRnkNo4;

        private String rpyTrgtAcptDt4;

        private String rpyTrgtAcptNo4;

        private BigDecimal rpyTrgtBndAmt4;

        private String rpyTrgtRnkNo5;

        private String rpyTrgtAcptDt5;

        private String rpyTrgtAcptNo5;

        private BigDecimal rpyTrgtBndAmt5;

        private String afrgstrScrtYn;

        private String rsrvItmB;

        private LocalDateTime regDtm;

        private String oblMLnAprvNo;

        private Integer oblTotCnt;

        private Integer oblGrpRnkNo;

        private String lnAprvNo2;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TbWoTrnFa6100F1IdInfoSvo {
        @NotNull
        @Size(max = 20)
        String loanNo;
        @NotNull
        LocalDateTime chgDtm;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminReqeustReqInSvo {
        /**
         * 여신번호
         */
        @Schema(description = "여신번호", example = "22264019244")
        @JsonProperty("loanNo")
        private String loanNo;
        /**
         * 업무코드
         */
        @Schema(description = "업무코드", example = "IMAGE_CUST")
        @JsonProperty("wkCd")
        private String wkCd;
        /**
         * 첨부파일코드
         */
        @Schema(description = "대출실행번호", example = "2")
        @JsonProperty("attcFilCd")
        private String attcFilCd;
        /**
         * 은행코드
         */
        @Schema(description = "은행코드", example = "020")
        @JsonProperty("bankCd")
        private String bankCd;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AdminReqeustReqOutSvo extends BaseResponseVo {
        @JsonProperty("code")
        String code;
        @JsonProperty("msg")
        String msg;
        @JsonProperty("data")
        String data;
    }



    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CntrMasterInfoSvo {
        /**
         * 여신번호
         */
        private String loanNo;
        /**
         * 사업자등록번호
         */
        private String bizNo;
        /**
         * 보험구분코드
         */
        private String isrnGbCd;
        /**
         * 은행지점코드
         */
        private String bnkBrnchCd;
        /**
         * 은행구분코드
         */
        private String bnkGbCd;
        /**
         * 은행구분명
         */
        private String bnkNm;
        /**
         * 지점명
         */
        private String bnkBrnchNm;
        /**
         * 은행 담당자 명
         */
        private String bnkDrctrNm;
        /**
         * 은행 지점 전화번호
         */
        private String bnkBrnchPhno;
        /**
         * 대출종류코드
         */
        private String lndKndCd;
        /**
         * 대출종류명
         */
        private String lndKndNm;
        /**
         * 상태코드
         */
        private String statCd;
        /**
         * 상태명
         */
        private String statNm;

        /**
         * 대출상태코드
         */
        private String lndStatCd;
        /**
         * 대출상태명
         */
        private String lndStatNm;
        /**
         * 등기 구분 코드
         */
        private String rgstrGbCd;
        /**
         * 등기 구분명
         */
        private String rgstrGbNm;
        /**
         * 대출상품명
         */
        private String lndPrdtNm;
        /**
         * 차주명
         */
        private String dbtrNm;
        /**
         * 차주생년월일
         */
        private String dbtrBirthDt;
        /**
         * 차주 주소지
         */
        private String dbtrAddr;
        /**
         * 차주 핸드폰 번호
         */
        private String dbtrHpno;
        /**
         * 담보제공자명
         */
        private String pwpsNm;
        /**
         * 담보제공자 생년월일
         */
        private String pwpsBirthDt;
        /**
         * 담보제공자 핸드폰 번호
         */
        private String pwpsHpno;
        /**
         * 실행예정일자
         */
        private String execPlnDt;
        /**
         * 실행금액
         */
        private BigDecimal execAmt;
        /**
         * 대출실행일자
         */
        private String execDt;
        /**
         * 매매가액
         */
        private BigDecimal slPrc;
        /**
         * 법무사타은행코드1
         */
        private String lwyrDiffBankCd1;
        /**
         * 법무사타은행코드2
         */
        private String lwyrDiffBankCd2;
        /**
         * 법무사타은행코드3
         */
        private String lwyrDiffBankCd3;
        /**
         * 법무사타은행코드4
         */
        private String lwyrDiffBankCd4;
        /**
         * 법무사타은행코드5
         */
        private String lwyrDiffBankCd5;
        /**
         * 법무사타은행코드6
         */
        private String lwyrDiffBankCd6;
        /**
         * 법무사타은행코드7
         */
        private String lwyrDiffBankCd7;
        /**
         * 법무사타은행코드8
         */
        private String lwyrDiffBankCd8;
        /**
         * 법무사타은행코드9
         */
        private String lwyrDiffBankCd9;
        /**
         * 법무사타은행코드10
         */
        private String lwyrDiffBankCd10;
        /**
         * 말소(감액)해당없음메시지
         */
        private String ersuClsMsg;
        /**
         * 설정법무사 사업자번호
         */
        private String ebtsLwyrBizNo;
        /**
         * 등기소명
         */
        private String regoNm;
        /**
         * 등기접수일시
         */
        private LocalDateTime rgstrAcptDtm;
        /**
         * 이미지 키
         */
        private String imgKey;
        /**
         * 대출구분코드
         */
        private String kndCd;
        /**
         * 대출구분명
         */
        private String kndNm;
        /**
         * 대출물건지주소(asis:address)
         */
        private String lndThngAddr;
        /**
         * 등기부등본접수번호
         */
        private String ccrstAcptNum;
        /**
         * 근저당설정계약서BPR등록여부
         */
        private String clsctSctrtBprRegYn;
        /**
         * 등기부등본BPR등록여부
         */
        private String ccrstBprRegYn;
        /**
         * 잔금완납영수증
         */
        private String blncFpymnRcpt;
        /**
         * 등기필정보BPR등록여부
         */
        private String regifBprRegYn;
        /**
         * 전자등기사업자번호
         */
        private String elregBizNo;
        /**
         * 도로명포함주소지
         */
        private String rdnmInclAddr;
        /**
         * 그룹번호(ASIS:M_APPROVAL_NUM)
         */
        private String grpNo;
        /**
         * 비고
         */
        private String rmk;
        /**
         * INS_DVSN
         */
        private String insDvsn;
        /**
         * 전문인입횟수
         */
        private Long trnInCnt;
        /**
         * 상환금 수령용 계좌 등록 여부
         */
        private String refndAcctRegYn;
        /**
         * 상환금 수령용 계좌 최초 등록 일자
         */
        private String refndAcctRegDate;
        /**
         * 전자등기 + 자담 건인지 여부
         */
        private String eltnSecuredYn;
        /**
         * 실행금액 변경 여부
         */
        private String execAmtChangYn;
        /**
         * 견적서 등록여부
         */
        private String estmRegYn;
        /**
         * 등기정보 등록 여부
         */
        private String regifRegYn;
        /**
         * 지급정보 등록 여부
         */
        private String payRegYn;
        /**
         * 견적서 확정 여부
         */
        private String estmCnfmYn;
        /**
         * 대출금 또는 상환금 지급 여부
         */
        private String lndAmtPayYn;
        /**
         * 보정 여부
         */
        private String revisionCheckYn;
        /**
         * 설정 - 6200 설정등기 확인결과서 발송 여부
         */
        private String estmCntrFnYn;
        /**
         * 매매 계약서 유무
         */
        private String slCntrctEane;
        /**
         * 매매 계약서 파일명
         */
        private String slCntrctFlnm;
        /**
         * 전입세대열람원 제출여부
         */
        private String mvhhdSbmtYn;
        /**
         * 주민등록등본 제출여부
         */
        private String rrcpSbMtYn;
        /**
         * 수정임대차 계약서 제출 여부
         */
        private String rtalSbmtYn;
        /**
         * 조건부 계약여부
         */
        private String cndtCntrYn;
        /**
         * 등기신청번호 등록여부
         */
        private String rgstrAcptSbmtYn;
        /**
         * 이전등기 가능 법무사 유무
         */
        private String cnvntLwyrYn;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrnFa6100F1SaveSvo {
        /**
         * 여신번호
         */
        private String loanNo;
        /**
         * 등기부등본 데이터
         */
        private OutList outList;
        /**
         * 원장데이터
         */
        private CntrMasterInfoSvo cntrMasterInfoSvo;
        /**
         * FA의뢰내역 데이터
         */
        private TbWoTrnFa6100F1InfoSvo tbWoTrnFa6100F1InfoSvo;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetBnkFxcltRgstrAcptDtInSvo {
        /**
         * 여신번호
         */
        private String loanNo;
        /**
         * 은행 근저당권 채권 최고 금액
         */
        private BigDecimal bnkFxcltBndMaxAmt;
        /**
         * 등기부등본 데이터
         */
        private OutList outList;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetOwnOwnshMvRgstrAcptDtInSvo {
        /**
         * 여신번호
         */
        private String loanNo;
        /**
         * 차주명
         */
        private String dbtrNm;
        /**
         * 등기부등본 데이터
         */
        private OutList outList;
    }



    /*
    보험FA확인내역
     */
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class TrnFa6200F2InfoId{
        @Size(max = 20)
        private String loanNo;
        private LocalDateTime chgDtm;
    }

    /*
    보험FA확인내역
   */
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static  class TrnFa6200F2Info{
        private TrnFa6200F2InfoId id;
        private  BigDecimal tgLen;
        @Size(max = 4)
        private  String tgDsc;
        @Size(max = 8)
        private  String bnkTgNo;
        @Size(max = 8)
        private  String faTgNo;
        @Size(max = 14)
        private  String kosTgSndNo;
        @Size(max = 14)
        private  String tgSndDtm;
        @Size(max = 14)
        private  String tgRcvDtm;
        @Size(max = 3)
        private  String resCd;
        @Size(max = 35)
        private  String rsrvItmH;
        @Size(max = 20)
        private  String bnkTtlReqNo;
        @Size(max = 2)
        private  String procDsc;
        @Size(max = 2)
        private  String lndKndCd;
        @Size(max = 2)
        private  String fndUseCd;
        @Size(max = 1)
        private  String lndPmntCnfmSlfCd;
        @Size(max = 1)
        private  String rcptInfoCnfmSlfCd;
        @Size(max = 1)
        private  String thdyRgstrAcptNoInptYn;
        @Size(max = 9)
        private  String estbsRgstrAcptNo1;
        @Size(max = 9)
        private  String estbsRgstrAcptNo2;
        @Size(max = 9)
        private  String estbsRgstrAcptNo3;
        @Size(max = 9)
        private  String ersrAcptNo1;
        @Size(max = 9)
        private  String ersrAcptNo2;
        @Size(max = 9)
        private  String ersrAcptNo3;
        @Size(max = 100)
        private  String rmkB1;
        @Size(max = 1)
        private  String bnkFxcltEstbsFnYn;
        @Size(max = 1)
        private  String bnkFxcltRnkMthYn;
        @Size(max = 1)
        private  String bnkFxcltEstbsNoMthYn;
        @Size(max = 300)
        private  String rgstAtcpThngAddr;
        @Size(max = 14)
        private  String rgstrUnqNo1;
        @Size(max = 14)
        private  String rgstrUnqNo2;
        @Size(max = 14)
        private  String rgstrUnqNo3;
        @Size(max = 14)
        private  String rgstrUnqNo4;
        @Size(max = 14)
        private  String rgstrUnqNo5;
        @Size(max = 8)
        private  String bnkFxcltRgstrAcptDt;
        @Size(max = 8)
        private  String ownOwnshMvRgstrAcptDt;
        @Size(max = 50)
        private  String ownNm1;
        @Size(max = 50)
        private  String ownNm2;
        @Size(max = 50)
        private  String ownNm3;
        @Size(max = 13)
        private  String ownBirthDt1;
        @Size(max = 13)
        private  String ownBirthDt2;
        @Size(max = 13)
        private  String ownBirthDt3;
        @Size(max = 150)
        private  String rmkB2;
        @Size(max = 1)
        private  String dbtrRrcpSbmtYn;
        @Size(max = 1)
        private  String dbtrFrcSbmtYn;
        @Size(max = 1)
        private  String rrcpDbtrSlfRgstYn;
        @Size(max = 1)
        private  String rrcpSpusRgstYn;
        @Size(max = 1)
        private  String frcSpusCnfmYn;
        @Size(max = 150)
        private  String rmkB3;
        @Size(max = 1)
        private  String dbtrTgrcSbmtYn;
        @Size(max = 1)
        private  String tgrcDbtrSlfMvinYn;
        @Size(max = 1)
        private  String tgrcDbtrOtsdSprtHshldEane;
        @Size(max = 1)
        private  String exedtMvMvinEane;
        @Size(max = 1)
        private  String bnkDbtrTgrcMthYn;
        @Size(max = 14)
        private  String mvinHshldRdDtm;
        @Size(max = 255)
        private  String rmkB4;
        @Size(max = 30)
        private  String rschAgncNm;
        @Size(max = 50)
        private  String srchrNm;
        @Size(max = 20)
        private  String srchrPhno;
        @Size(max = 14)
        private  String rschDtm;
        @Size(max = 1)
        private  String srvRschDsc;
        @Size(max = 50)
        private  String ojtAns;
        @Size(max = 200)
        private  String sjtAns1;
        @Size(max = 200)
        private  String sjtAns2;
        @Size(max = 200)
        private  String sjtAns3;
        @Size(max = 200)
        private  String sjtAns4;
        @Size(max = 200)
        private  String sjtAns5;
        @Size(max = 50)
        private  String slfCtfcAgnc;
        @Size(max = 12)
        private  String slfCtfcTm;
        @Size(max = 1)
        private  String resyn;
        @Size(max = 1)
        private  String pslCnfmStrdOptmYn;
        @Size(max = 1)
        private  String pslCnfmRsltBnkTrnsYn;
        @Size(max = 12)
        private  String pslCnfmRsltBnkRcvTm;
        @Size(max = 353)
        private  String rsrvItmB;
        private  LocalDateTime regDtm;
        @Size(max = 20)
        private  String lnAprvNo2;
    }


}