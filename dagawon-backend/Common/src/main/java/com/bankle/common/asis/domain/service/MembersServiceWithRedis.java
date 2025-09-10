//package com.bankle.common.asis.domain.service;
//
//import com.bankle.common.asis.domain.entity.Members;
//import com.bankle.common.asis.domain.repositories.MemberRepository;
//import com.bankle.common.asis.infra.ResponseLoginDto;
//import com.bankle.common.util.StringUtil;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Optional;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class MembersServiceWithRedis {
//
//    private final RedisTemplate redisTemplate;
////    private final TokenProvider tokenProvider;
//
//    private final MemberRepository memberRepository;
//
//    @Value("${token.access.timeout}")
//    private long access;
//
//    @Value("${token.refresh.timeout}")
//    private long refresh;
//
//    /**
//     * 레이스에 토큰 저장
//     *
//     * @param loginDto
//     */
//    public void loadToRedis(ResponseLoginDto loginDto) {
//        String membId = loginDto.getMembId();
//
//        redisTemplate.opsForSet().add(membId,
//                loginDto.getAccessToken(),
//                loginDto.getRefreshToken(),
//                loginDto.getRole());
//    }
//
//    /**
//     * 토큰정보 조회
//     *
//     * @param membId
//     * @return
//     */
//    public HashSet<?> getTokenInfoFromRedis(String membId) {
//        Long size = redisTemplate.opsForSet().size(membId);
//        return (size == 0) ? new HashSet<>()
//                : (HashSet<?>) redisTemplate.opsForSet().members(membId);
//
//    }
//
//    /**
//     * 유효한 토큰인지 확인
//     *
//     * @param membId
//     */
//    public boolean isValidToken(String membId) {
//        HashSet<?> tokenInfoFromRedis = getTokenInfoFromRedis(membId);
//        Iterator<?> iterator = tokenInfoFromRedis.iterator();
//
//        boolean isValid = false;
//
//        while (iterator.hasNext()) {
//            String accessToken = (String) iterator.next();
//
////            Map<String, Object> verifiedMap = tokenProvider.verify(accessToken);
////            if(verifiedMap == null) {
////                log.info("access token is expired or invalid");
////                isValid = false;
////            }else{
//            // refresh access token
////                refreshToken((String) verifiedMap.get("sub"));
////                isValid = true;
////            }
////            break;
//        }
//
//        return isValid;
//    }
//
//    public ResponseLoginDto refreshToken(String membId) {
//
//        Optional<Members> byMembId = memberRepository.findByMembId(membId);
//        ResponseLoginDto responseLoginDto = (byMembId.isEmpty()) ?
//                null :
//                setResponseLoginDto(byMembId.get());
//
//        //redis 세팅
//        loadToRedis(responseLoginDto);
//
//        return responseLoginDto;
//    }
//
//    private ResponseLoginDto setResponseLoginDto(Members member) {
//
//        ResponseLoginDto loginDto = ResponseLoginDto.of(member);
//
////        final String accessToken = tokenProvider.create(member, Date.from(Instant.now().plus(access, ChronoUnit.MINUTES)));
////        final String refreshToken = tokenProvider.create(member, Date.from(Instant.now().plus(refresh, ChronoUnit.MINUTES)));
////        loginDto.setAccessToken(accessToken);
////        loginDto.setRefreshToken(refreshToken);
////        loginDto.setRole("MEMBER");
//
//        //redis 세팅
//        loadToRedis(loginDto);
//
//        return loginDto;
//    }
//
//    /**
//     * 로그인
//     *
//     * @param udid
//     * @param paramPwd
//     * @return
//     */
//    public ResponseLoginDto getByCredentials(final String udid, final String paramPwd) {
//
//        if (!StringUtils.hasText(udid))
//            return null;
//
//        Optional<Members> byMembId = memberRepository.findByUdidAndEntrStc(udid, "1");
//        if (byMembId.isEmpty())
//            return null;
//
//        Members member = byMembId.get();
//
//        if (StringUtil.equals(paramPwd, member.getPwd())) {
//            return setResponseLoginDto(member);
//        } else {
//            return null;
//        }
//    }
//
//    public void deleteToken(String membId) {
//        redisTemplate.delete(membId);
//    }
//}
