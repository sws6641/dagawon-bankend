//package kr.co.anbu.event.handler;
//
//import kr.co.anbu.event.TokenEvent;
//import kr.co.anbu.security.jwt.TokenProvider;
//import kr.co.anbu.utils.AuthUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class TokenEventHandler {
//
//    private final TokenProvider tokenProvider;
//
//    @EventListener
//    public void isValidToken(TokenEvent event) throws Exception{
//        String loggedInMember = tokenProvider.validateAndGetMembId(AuthUtils.parseBearerToken(event.getRequest()));
//
//         log.info("loggedInMember >>>>>>> "+loggedInMember);
//
//        if(!AuthUtils.isValidUser(loggedInMember)){
//           throw new RuntimeException(HttpStatus.FORBIDDEN.toString());
//        }
//    }
//}
