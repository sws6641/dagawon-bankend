//package kr.co.anbu.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Slf4j
//public class AuthUtils {
//
//    public static String getAuthenticatedMembId() throws Exception{
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return (authentication == null) ? null : (String) authentication.getPrincipal();
//    }
//
//    public static boolean isValidUser(String membIdOfToken) throws Exception{
//        return kr.co.anbu.utils.StringCustUtils.equals(getAuthenticatedMembId(), membIdOfToken);
//    }
//
//    public static String parseBearerToken(HttpServletRequest request){
//
//        String bearerToken = request.getHeader("Authorization");
//
//        if(StringUtils.hasText(bearerToken)
//                && bearerToken.startsWith("Bearer ")){
//            return bearerToken.substring(7);
//        }
//
//        return null;
//    }
//
//}
