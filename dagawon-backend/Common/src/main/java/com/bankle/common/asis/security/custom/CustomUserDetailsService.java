//package kr.co.anbu.security.custom;
//
//import kr.co.anbu.domain.entity.Members;
//import kr.co.anbu.domain.service.MemberService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@AllArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final MemberService memberService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Members member = null;
//
//        try {
//            member = memberService.getMemberByUdid(username);
//        } catch (Exception e) {
//            log.debug(e.getMessage());
//        }
//
//        if(member == null)
//            throw new UsernameNotFoundException(username);
//
//        return CustomUser.build(member);
//    }
//}
//
