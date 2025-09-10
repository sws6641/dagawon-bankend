package com.bankle.common.comBiz.infoTech.vo;

import com.bankle.common.util.httpapi.vo.BaseResponseVo;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;

public class InfoTechCvo {


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchAddrResCvo {
        @Schema(description = "등기고유번호")
        private String uniqNo;
        @Schema(description = "주소")
        private String addr;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoTechResCvo  {


        private String resCd;


        private String resMsg;


        private C0000OutData out;


        private String reqTm;


        private String reqTmSs;


        private String resTm;


        private String resTmSs;


        private String reqCd;

    }



        @Data
        @ToString
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class C0000OutData {

            private String uid;


            private OutC0000 outC0000;


            private String orgCd;


            private String resCd;


            private String errMsg;


            private String appCd;


            private String reqCd;


            private String svcCd;


            private String resMsg;


            private String device;


            private String errYn;

        }

        @Data
        @ToString
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OutC0000 {

            private String errYn;

            private String errMsg;

            private List<DataItem> list;

            private String totCnt;


        }


        @Data
        @ToString
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class DataItem {

            private String propertyUniqueId;

            private String category;

            private String propertyLocation;

            private String owner;

            private String status;

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
        private  String appCd;

        @JsonProperty("orgCd")
        @Schema(description = "기관코드")
        private  String orgCd;

        @JsonProperty("svcCd")
        @Schema(description = "서비스 코드")
        private  String svcCd;

        @JsonProperty("userId")
        @Schema(description = "")
        private  String userId;

        @JsonProperty("userPw")
        @Schema(description = "")
        private String userPw;

        @JsonProperty("selRegt")
        @Schema(description = "")
        private  String selRegt;

        @JsonProperty("inpRecevNo")
        @Schema(description = "")
        private String inpRecevNo;

        @JsonProperty("rstNo")
        @Schema(description = "")
        private  String rstNo;

        @JsonProperty("nameType")
        @Schema(description = "")
        private  String nameType;

        @JsonProperty("ownerName")
        @Schema(description = "")
        private String ownerName;

        @JsonProperty("passYn")
        @Schema(description = "")
        private String passYn;

    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRgstrChgYnReqCvo {
        @Schema(description = "에스크로번호" , example = "20240611000000")
        @Length(min = 14, max = 14)
        private String escrNo;
        @Schema(description = "등기고유번호" , example = "13131996060744")
        @Size(min = 14, max = 14)
        private String uniqNo;
        @Schema(description = "매도인명" , example = "이광운")
        @Length(min = 1, max = 100)
        private String ownerName;
//        @Schema(description = "1: 등기신청인 , 2:소유자" , example = "2")
//        @Length(min = 1 , max = 1)
//        private String nameTape;
    }


    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IrosC0003ResCvo{

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
        private List<InfoTechSvo.SeatLotn> lotnumAddrList;

        @JsonProperty("등기목적")
        private String rgstrPrps;

        @JsonProperty("처리상태")
        private String procStat;

        @JsonProperty("국민주택채권매입환급액")
        private String natlHusBondPurchaseRefundAmt;

        @JsonProperty("국민주택채권")
        private List<InfoTechSvo.NatlHousingBond> natlHousingBondList;

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
    public static class SearchCcrstIsnYnReqCvo{
        @Schema(description = "등기고유번호" , example =  "13451996177867" )
        @Size(min = 14 , max = 14)
        private String uniqNo;
        @Schema(description = "차주명" , example =  "차주명" )
        @Size(min = 1 , max = 100)
        private String dbtrNm;
        @Schema(description = "당보제공자명" , example =  "담보제공자명" )
        private String pwpsNm;
        @Schema(description = "법무사명" , example =  "법무사명" )
        private String lwyrNm;
    }

    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RgstrIsnReqCvo {
        @Schema(description = "에스크로번호" , example = "20240611000000")
        @Length(min = 14 , max = 14 , message = "애스크로번호는 14자리 입니다.")
        @NotBlank(message = "애스크로번호는 필수 입렵 값입니다.")
        private String escrNo;
        @Schema(description = "등기고유번호", example = "13131996060744")
        @Length(min = 14 , max = 14 , message = "등기고유번호는 14자리 입니다.")
        @NotBlank(message = "등기고유번호는 필 수 입력 값입니다.")
        private String rgstrUnqNo;
    }




    /*
    고유번호로 등기신청사건 조회 API 응답 데이터
     */
    @Data
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CcrstIsnApiResCvo extends BaseResponseVo {

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
        private InfoTechSvo.SummaryInfo summaryInfo;

        @JsonProperty("소유지분현황_갑구")
        private List<InfoTechSvo.OwnershipInfo> ownershipInfoList;

        @JsonProperty("근_저당권_및_전세권_등_을구")
        private List<InfoTechSvo.LienAndLeaseInfo> lienAndLeaseInfoList;

        @JsonProperty("표제부_토지의_표시")
        private List<String> landMarkingList;

        @JsonProperty("표제부_1동의_건물의_표시")
        private List<InfoTechSvo.BuildingMarking> buildingMarkingList;

        @JsonProperty("표제부_대지권의_목적인_토지의_표시")
        private List<InfoTechSvo.LandMarkingPurpose> landMarkingPurposeList;

        @JsonProperty("표제부_전유부분_건물의_표시")
        private List<InfoTechSvo.BuildingPartialMarking> buildingPartialMarkingList;

        @JsonProperty("표제부_대지권의_표시")
        private List<InfoTechSvo.LandOwnershipMarking> landOwnershipMarkingList;

        @JsonProperty("소유권에_관한_사항_갑구")
        private List<InfoTechSvo.OwnershipDetails> ownershipDetailsList;

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
    public static  class BuildingMarking {

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
    public static class BuildingPartialMarking {

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

    }



