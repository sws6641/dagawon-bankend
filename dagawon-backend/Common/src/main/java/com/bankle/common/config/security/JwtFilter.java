package com.bankle.common.config.security;
import com.bankle.common.enums.Role;
import com.bankle.common.exception.*;
import com.bankle.common.userAuth.UserAuthSvc;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserAuthSvc userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        log.debug("**** SECURITY FILTER > " + request.getRequestURL());
        try {

            String authorizationHeader = request.getHeader("Authorization");
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.split(" ")[1].trim();
                log.debug("**** SECURITY FILTER > Token: {} ", token);
                setAuthentication(token, request, response);
            }
            filterChain.doFilter(request, response);
        } catch (BadRequestException e) {
            log.debug("**** SECURITY FILTER > BadRequestException !!!");
            e.printStackTrace();
            String errMsg = "NOT_VALIDATE_TOKEN";
            if (e.getMessage().equalsIgnoreCase("EXPIRED_ACCESS_TOKEN")) {
                errMsg = "EXPIRED_ACCESS_TOKEN";
            } else if (e.getMessage().equalsIgnoreCase("CANNOT_FOUND_USER")) {
                errMsg = "CANNOT_FOUND_USER";
            } else if (e.getMessage().equalsIgnoreCase("NOT_VALIDATE_TOKEN")) {
                errMsg = "NOT_VALIDATE_TOKEN";
            }
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            writeErrorLogs("BadRequestException",errMsg , response);
        } catch (NotFoundException e) {
            log.debug("**** SECURITY FILTER > NotFoundException !!!");
            e.printStackTrace();
            response.setStatus(HttpStatus.NOT_FOUND.value());
            writeErrorLogs("NOT_FOUND", e.getMessage(), response);
        } catch (DuplicateLoginException e) {
            log.debug("**** SECURITY FILTER > DuplicateLoginException !!!");
            response.setStatus(HttpStatus.VARIANT_ALSO_NEGOTIATES.value());
            writeErrorLogs("DuplicateLoginException", e.getMessage(), response);
        }
        catch (Exception e) {
            log.debug("**** SECURITY FILTER > Exception !!!");
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            writeErrorLogs("Exception", e.getMessage(), response);
        }
    }

    private void setAuthentication(String token, HttpServletRequest request,  HttpServletResponse response) {

        if (!jwtUtil.validateToken(token)) {
            throw new BadRequestException("NOT_VALIDATE_TOKEN");
        }

        if (jwtUtil.isExpiration(token)) { // 만료되었는지 체크
            throw new UnAuthException("EXPIRED_ACCESS_TOKEN");
        }

        String membNo = (String) jwtUtil.get(token).get("membNo");
        String dvceUnqNum= (String) jwtUtil.get(token).get("dvceUnqNum");
        String isAdmin = (String) jwtUtil.get(token).get("isAdmin");
        String lognId = (String) jwtUtil.get(token).get("lognId");

        // 회원권한 - 강제 셋팅
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(Role.USER.toString()));
        UsernamePasswordAuthenticationToken authentication ;

        if("Y".equals(isAdmin)){
            // Admin 으로 대체
            log.debug("isAdmin : " + isAdmin);
            log.debug("lognId : " + lognId);
             authentication = new UsernamePasswordAuthenticationToken(
                userService.getAdminDetails(lognId), null,roles);
        } else {
             authentication = new UsernamePasswordAuthenticationToken(
                userService.getUserDetails(membNo,dvceUnqNum), null,roles);
        }

        log.debug(" ==================================================================================");
        log.debug(" == Security Success  ");
        log.debug(" ==================================================================================");
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void  writeErrorLogs(String exception, String message,  HttpServletResponse response ) {
        log.error("**** " + exception + " ****");
        log.error("**** error message : " + message);
        log.error("**** error message : " + response.getStatus());
        log.error("**** error message : " + response);
        if("BadRequestException".equals(exception)) {
            throw new BadRequestException( message);
        } else if("Exception".equals(exception)){
            throw new DefaultException(message);
        } else if("NOT_FOUND".equals(exception)){
            throw new NotFoundException(message);
        } else if("DuplicateLoginException".equals(exception)){
            throw new DuplicateLoginException(message);
        }else {
            throw new DefaultException(message);
        }
    }


//    private void errorMsg(String errMsg, String msg, StackTraceElement[] traceMsg, HttpServletResponse response) {
//        writeErrorLogs(errMsg, msg, traceMsg);
//        response.setStatus(UNAUTHORIZED.value());
//        response.setContentType(APPLICATION_JSON_VALUE);
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            String json = mapper.writeValueAsString(
//                    new ErrorResponse( HttpStatus.UNAUTHORIZED.value(), msg , HttpStatus.UNAUTHORIZED));
//            response.getWriter().write(json);
//            response.getWriter().flush();
//            response.getWriter().close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }

}
