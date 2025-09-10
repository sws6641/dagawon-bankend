//package kr.co.anbu.security.jwt;
//
//import kr.co.anbu.utils.AuthUtils;
//import kr.co.anbu.utils.StringCustUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final TokenProvider tokenProvider;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        String uri = request.getRequestURI();
//        if(!StringCustUtils.contains(uri, "/sign-in")){
//            try{
//                String token = AuthUtils.parseBearerToken(request);
//                log.info("JwtAuthenticationFilter is running ...");
//
//                if(token != null && !token.equalsIgnoreCase("null")){
//                    String membId = tokenProvider.validateAndGetMembId(token);
//                    log.info("Authenticated member Id : " + membId);
//
//                    AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                            membId,
//                            null,
//                            AuthorityUtils.createAuthorityList("MEMBER")
//                    );
//
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
//                    securityContext.setAuthentication(authentication);
//                    SecurityContextHolder.setContext(securityContext);
//                }
//            }catch (Exception e){
//                log.error("Could not set Member authentication in security context", e.getMessage());
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
