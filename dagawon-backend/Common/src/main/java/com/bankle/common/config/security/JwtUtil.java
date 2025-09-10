package com.bankle.common.config.security;


import com.bankle.common.enums.AuthMethod;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${app.auth.token-secret}")
    private String secret;
    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 180L; // 30 days
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 180L; // 30 days
    private static final Long ADMIN_ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 60 * 24 * 180L; // 3 hours



    //private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 7L; // 2 hours
    // private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 1 * 60 * 1L; // 1 분
    // private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 2 * 60  *1L; // 2분

    // 서버에서 서버를 호춯할때 dvceUnqNum에 "System"을 넣어준다.
    public String getAccessToken(String membNo , AuthMethod authMethod , String dvceUnqNum) {

        // log.debug("========================================" );
        // log.debug("getAccessToken =========================" );
        // log.debug("membNo : " + membNo );
        // log.debug("========================================" );

        HashMap<String, Object> claim = new HashMap<>();
        claim.put("membNo"      , membNo);
        claim.put("authMethod"  , authMethod);
        claim.put("isAdmin"     , "N");
        claim.put("dvceUnqNum"     , dvceUnqNum);
        return makeJwt("ACCESS_TOKEN", ACCESS_TOKEN_EXPIRATION_TIME, claim);
    }
    // 서버에서 서버를 호춯할때 dvceUnqNum에 "System"을 넣어준다.
    public String getRefreshToken(String membNo , AuthMethod authMethod , String dvceUnqNum) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("membNo"      , membNo);
        claim.put("authMethod"  , authMethod);
        claim.put("isAdmin"     , "N");
        claim.put("dvceUnqNum"     , dvceUnqNum);
        // claim.put("roles", roles);  // List<String> roles
        return makeJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
    }


    public String getAdminAccessToken(String lognId) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("lognId"      , lognId);
        claim.put("isAdmin"     , "Y");
        return makeJwt("ACCESS_TOKEN", ADMIN_ACCESS_TOKEN_EXPIRATION_TIME, claim);
    }

    public String getAdminRefreshToken(String lognId) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("lognId"      , lognId);
        claim.put("isAdmin"     , "Y");
        return makeJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
    }
//    public String getAdminRefreshToken(String membNo ,  String authMethod) {
//        HashMap<String, Object> claim = new HashMap<>();
//        claim.put("membNo"      , membNo);
//        claim.put("authMethod"  , authMethod);
//        claim.put("isAdmin"     , "Y");
//        // claim.put("roles", roles);  // List<String> roles
//        return makeJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
//    }

    public String makeJwt(String subject, Long expiration, HashMap<String, Object> claim) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        JwtBuilder jwtBuilder = Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(subject)
        .setIssuedAt(new Date())
        .signWith(key, SignatureAlgorithm.HS512);

        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }

        if (expiration != null) {
            jwtBuilder.setExpiration(new Date(new Date().getTime() + expiration));
        }

        return jwtBuilder.compact();
    }

    /**
     * 복호화
     */
    public Claims get(String jwt) throws JwtException {
        return Jwts
        .parserBuilder()
        .setSigningKey(secret)
        .build()
        .parseClaimsJws(jwt)
        .getBody();
    }

    /**
     * 토큰 만료 여부 체크
     *
     * @return true : 만료됨, false : 만료되지 않음
     */
    public boolean isExpiration(String jwt) throws JwtException {
        try {
            return get(jwt).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e){
            return true;
        }
    }

    // JWT token 검
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
            .setSigningKey(secret)
            .build()
            .parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
            return false;
			//throw new JwtException("유효하지 않는 토큰 : " + e.getMessage());
		} catch (ExpiredJwtException e) {
            return false;//
			//throw new JwtException("기간 만료 된 토큰 : " + e.getMessage());
		} catch (UnsupportedJwtException e) {
            return false;
			//throw new JwtException("유효하지 않는 토큰 : " + e.getMessage());
		} catch (IllegalArgumentException e) {
            return false;
			//throw new JwtException("유효하지 않는 토큰 : " + e.getMessage());
		} catch (Exception ex) {
            return false;
			//throw new JwtException("유효하지 않는 토큰!");
		}

	}

    /**
     * Refresh token refresh 여부 확인
     * 만료일 7일 이내 일 경우 refresh token 재발급
     */
    public boolean canRefresh(String refreshToken) throws JwtException {
        Claims claims = get(refreshToken);
        long expirationTime = claims.getExpiration().getTime();
        long weekTime = 1000 * 60 * 60 * 24 * 7L;

        return new Date().getTime() > (expirationTime - weekTime);
    }


      // 기본 적으로 유효기간은 1시간 이며 유저 정보를 이용해서 생성할 수 있는 방법이 어려개 있음. ( 공식문서 참고 )
    //   public String createFirebaseCustomToken(Map<String,Object> userInfo) throws Exception {

    //     UserRecord userRecord;
    //     String uid = userInfo.get("id").toString();
    //     String email = userInfo.get("email").toString();
    //     String displayName = userInfo.get("nickname").toString();

    //     // 1. 사용자 정보로 파이어 베이스 유저정보 update, 사용자 정보가 있다면 userRecord에 유저 정보가 담긴다.
    //     try {
    //         UpdateRequest request = new UpdateRequest(uid);
    //         request.setEmail(email);
    //         request.setDisplayName(displayName);
    //         userRecord = FirebaseAuth.getInstance().updateUser(request);

    //     // 1-2. 사용자 정보가 없다면 > catch 구분에서 createUser로 사용자를 생성한다.
    //     } catch (FirebaseAuthException e) {

    //         CreateRequest createRequest = new CreateRequest();
    //         createRequest.setUid(uid);
    //         createRequest.setEmail(email);
    //         createRequest.setEmailVerified(false);
    //         createRequest.setDisplayName(displayName);

    //         userRecord = FirebaseAuth.getInstance().createUser(createRequest);
    //     }

    //     // 2. 전달받은 user 정보로 CustomToken을 발행한다.
    //     return FirebaseAuth.getInstance().createCustomToken(userRecord.getUid());
    // }
}
