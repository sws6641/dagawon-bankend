//package kr.co.anbu.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.Map;
//
//@Slf4j
//@Service
//public class TokenProvider {
//    private static final String SECRET_KEY = "NMA8JPctFuna59f5";
//
//    public String create(Members member, Date willbeExpired){
//        return Jwts.builder()
//                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
//                .setSubject(member.getMembId())
//                .setIssuer("KOS")
//                .setIssuedAt(new Date())
//                .setExpiration(willbeExpired)
//                .compact();
//    }
//
//    public String validateAndGetMembId(String token) throws Exception{
//        Claims claims = Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//
//    public Map<String, Object> verify(String token){
//
//        Map<String, Object> claimMap = null;
//
//        try{
//            Claims claims = Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            claimMap = claims;
//        }catch(ExpiredJwtException e){
//            log.error("token is expired =============",e);
//        }catch(Exception e){
//            log.error("Error ===== ", e);
//        }
//
//        return claimMap;
//    }
//}
