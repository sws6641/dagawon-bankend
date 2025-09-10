package com.bankle.common.comBiz.kakao.svc;

import com.bankle.common.comBiz.kakao.vo.KakaoCvo;
import com.bankle.common.comBiz.kakao.vo.KakaoSvo;
import com.bankle.common.config.CommonConfig;
import com.bankle.common.dto.TbEscrMasterHistDto;
import com.bankle.common.dto.TbEscrRltnsListDto;
import com.bankle.common.entity.TbEscrMasterHist;
import com.bankle.common.exception.DefaultException;
import com.bankle.common.mapper.TbEscrMasterHistMapper;
import com.bankle.common.mapper.TbEscrMasterMapper;
import com.bankle.common.mapper.TbEscrRltnsListMapper;
import com.bankle.common.repo.TbEscrMasterHistRepository;
import com.bankle.common.repo.TbEscrMasterRepository;
import com.bankle.common.repo.TbEscrRltnsListRepository;
import com.bankle.common.util.CustomeModelMapper;
import com.barocert.BarocertException;
import com.barocert.kakaocert.KakaocertService;
import com.barocert.kakaocert.identity.Identity;
import com.barocert.kakaocert.sign.Sign;
import com.barocert.kakaocert.sign.SignReceipt;
import com.barocert.kakaocert.sign.SignResult;
import com.barocert.kakaocert.sign.SignStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.security.Signature;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoSvc {
    private final TbEscrRltnsListRepository tbEscrRltnsListRepository;

    private final KakaocertService kakaocertService;

    private final CustomeModelMapper customeModelMapper;

    private final TbEscrMasterRepository tbEscrMasterRepository;
    private final TbEscrMasterHistRepository tbEscrMasterHistRepository;

    private final static String TOKEN = "COKOSONE_KC" + " "+ "6iYuwBBz+B7pZ585vSd+gjLu82/9ogJPNQwqPtpAsjc=";

    /*
     * 카카오톡 이용자에게 단건(1건) 문서의 전자서명을 요청합니다.
     * https://developers.barocert.com/reference/kakao/java/sign/api-single#RequestSign
     */
    @Transactional(rollbackFor = Exception.class)
    public String requestSign(KakaoCvo.KakaoSignReq kakaoSign) throws BarocertException {

        KakaoCvo.KakaoCertReq kakaoCertReq = new KakaoCvo.KakaoCertReq();
        kakaoCertReq.setEscrNo(kakaoSign.getEscrNo());

        KakaoCvo.KakaoCertSignReq kakaoCertSignReq = new KakaoCvo.KakaoCertSignReq();

        kakaoCertSignReq.setRcvNm(kakaoSign.getRcvNm());
        kakaoCertSignReq.setRcvCphnNo(kakaoSign.getRcvCphnNo());
        kakaoCertSignReq.setRcvBirthDt(kakaoSign.getRcvBirthDt());
        kakaoCertSignReq.setSignTitle("에스크로 이용 동의");

        kakaoCertReq.setSign(kakaoCertSignReq);

        KakaoCvo.KakaoEnvCertReq kakaoEnvCertReq = new KakaoCvo.KakaoEnvCertReq();
        kakaoEnvCertReq.setSndNo("1600-9999");
        kakaoEnvCertReq.setSubClientId("안부");
        kakaoEnvCertReq.setExpeiresIn(300);

        kakaoCertReq.setEnv(kakaoEnvCertReq);

        KakaoSvo.KakaoCertIn kakaocertIn = customeModelMapper.mapping(kakaoCertReq,KakaoSvo.KakaoCertIn.class)

        ;
        Sign sign = new Sign();
        var envIn = kakaocertIn.getEnv();
        var signIn = kakaocertIn.getSign();
        {
            sign.setReceiverHP(kakaocertService.encrypt(signIn.getRcvCphnNo().replaceAll("-", "")));
            sign.setReceiverName(kakaocertService.encrypt(signIn.getRcvNm()));
            sign.setReceiverBirthday(kakaocertService.encrypt(signIn.getRcvBirthDt()));

            sign.setSignTitle(signIn.getSignTitle());
            if (StringUtils.hasText(signIn.getExtraMessage()))
                sign.setExtraMessage(kakaocertService.encrypt(signIn.getExtraMessage()));
            sign.setExpireIn(envIn.getExpeiresIn());
//            sign.setToken(kakaocertService.encrypt(signIn.getToken()));
            sign.setToken(kakaocertService.encrypt(TOKEN));
            sign.setTokenType("TEXT");

            sign.setAppUseYN(false);
        }

        try {
            SignReceipt result = kakaocertService.requestSign(CommonConfig.KAKAO_CLIENT_CODE, sign);

            String receipt = result.getReceiptID();

            if (receipt.length() > 0) {

                //해당 위치로 들어왔다면 전자서명 요청이 들어간 내용이다.
                // 전자서명 요청이 들어간 매도인 정보를 저장하도록 한다.
                var rltnsEntity = TbEscrRltnsListMapper.INSTANCE.toEntity(TbEscrRltnsListDto.builder()
                        .escrNo(kakaocertIn.getEscrNo())
                        .rltnsGbCd("01")
                        .rltnsNm(signIn.getRcvNm())
                        .rltnsCphnNo(signIn.getRcvCphnNo().replaceAll("-", ""))
                        .rltnsBirthDt(signIn.getRcvBirthDt())
                        .kakaoReceiptId(receipt)
                        .kakaoState(0)
                        .build());

                tbEscrRltnsListRepository.save(rltnsEntity);
            }
            return receipt;
        } catch (BarocertException ke) {
            throw new DefaultException("전자서명 요청 중 오류>>>>>>>" + ke.getMessage());
        }
    }

    /*
     * 전자서명(단건) 요청 후 반환받은 접수아이디로 인증 진행 상태를 확인합니다.
     * https://developers.barocert.com/reference/kakao/java/sign/api-single#GetSignStatus
     */
    @Transactional(rollbackFor = Exception.class)
    public String getSignStatus(String receiptID) {
        try {
            SignStatus result = kakaocertService.getSignStatus(CommonConfig.KAKAO_CLIENT_CODE, receiptID);
            /*SignStatus 결과에 대한 리턴 값의 정보이다. 아래의 내용중 꺼내서 사용하면 된다.
                receiptID	String	32	Y	접수아이디
                clientCode	String	12	Y	이용기관 코드
                state	    Integer	1	Y	상태
                                            └        0        대기
                                            └        1        완료
                                            └        2        만료
                requestDT	String	14	Y	전자서명 요청일시        → 형식: yyyyMMddHHmmss
                viewDT	    String	14	N	전자서명 조회일시        → 형식: yyyyMMddHHmmss
                completeDT	String	14	N	전자서명 완료일시        → 형식: yyyyMMddHHmmss
                expireDT	String	14	Y	전자서명 만료일시        → 형식: yyyyMMddHHmmss
                verifyDT	String	14	N	전자서명 검증일시        → 형식: yyyyMMddHHmmss
             */
            int status = result.getState();
            String ret = "";
            switch (status) {
                case 0 -> ret = "대기";
                case 1 -> ret = "완료";
                case 2 -> ret = "만료";
                default -> ret = "존재하지 않는 상태코드";
            }
            return ret;
        } catch (BarocertException ke) {
            throw new DefaultException("전자서명 상태조회 중 오류>>>>>>>" + ke.getMessage());
        }
    }

    /*
     * 완료된 전자서명을 검증하고 전자서명값(signedData)을 반환 받습니다.
     * 카카오 보안정책에 따라 검증 API는 1회만 호출할 수 있습니다. 재시도시 오류가 반환됩니다.
     * 전자서명 완료일시로부터 10분 이후에 검증 API를 호출하면 오류가 반환됩니다.
     * https://developers.barocert.com/reference/kakao/java/sign/api-single#VerifySign
     */
    @Transactional(rollbackFor = Exception.class)
    public String verifySign(String receiptID) {

        try {
        SignStatus fStatusResult = kakaocertService.getSignStatus(CommonConfig.KAKAO_CLIENT_CODE, receiptID);

                SignResult result = kakaocertService.verifySign(CommonConfig.KAKAO_CLIENT_CODE, receiptID);

            /*
                receiptID	    String	32	Y	접수아이디
                state	        Integer	1	Y	상태
                                                └    0    대기
                                                └    1    완료
                                                └    2    만료
                signedData	    String	-	Y	전자서명값
                ci	String	-	N	        AES 암호화    CI
                receiverName	String	-	Y	AES 암호화    수신자 성명
                receiverYear	String	-	Y	AES 암호화    수신자 출생년도
                receiverDay	    String	-	Y	AES 암호화    수신자 출생월일
                receiverHP	    String	-	Y	AES 암호화    수신자 휴대폰번호
             */
                int status = result.getState();

                String ret = "";
                if (status == 1) {
                    switch (status) {
                        case 0 -> ret = "대기";
                        case 1 -> ret = "완료";
                        case 2 -> ret = "만료";
                        default -> ret = "존재하지 않는 상태코드";
                    }

                    SignStatus statusResult = kakaocertService.getSignStatus(CommonConfig.KAKAO_CLIENT_CODE, receiptID);

                    var rltns = TbEscrRltnsListMapper.INSTANCE.toDto(
                            tbEscrRltnsListRepository.findByKakaoReceiptId(receiptID)
                                    .orElseThrow(() -> new DefaultException("존재하지 않는 관계자 입니다."))
                    );

                    rltns.setTrAgreeDt(LocalDate.now().toString().replaceAll("-", ""));
                    rltns.setTrAgreeYn("Y");
                    rltns.setKakaoRequestDt(statusResult.getRequestDT());
                    rltns.setKakaoVerifyDt(statusResult.getVerifyDT());
                    rltns.setKakaoExpireDt(statusResult.getExpireDT());
                    rltns.setKakaoCompleteDt(statusResult.getCompleteDT());
                    rltns.setKakaoViewDt(statusResult.getViewDT());
                    rltns.setKakaoState(status);

                    // 저장 진행
                    tbEscrRltnsListRepository.save(TbEscrRltnsListMapper.INSTANCE.toEntity(rltns));

                    // 카카오 인증이 완료되었으면 상태코드를 수수료 결제로 넘겨준다.

                    var escrMaster = TbEscrMasterMapper.INSTANCE.toDto(tbEscrMasterRepository.findByEscrNo(rltns.getEscrNo()).orElseThrow(()-> new DefaultException("존재하지 않는 에스크로 번호입니다.")));

                    escrMaster.setEscrDtlProgCd("02");

                    // 상태 코드 변경후 저장 완료
                    tbEscrMasterRepository.save(TbEscrMasterMapper.INSTANCE.toEntity(escrMaster));
                    tbEscrMasterHistRepository.save(TbEscrMasterHistMapper.INSTANCE.toEntity(customeModelMapper.mapping(escrMaster, TbEscrMasterHistDto.class)));

                }
                return ret;
        } catch (BarocertException ke) {
            throw new DefaultException("전자서명 검증 중 오류>>>>>>>"+ ke.getMessage());
        }
    }
}
