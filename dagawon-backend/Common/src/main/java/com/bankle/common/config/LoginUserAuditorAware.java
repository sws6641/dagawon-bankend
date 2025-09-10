package com.bankle.common.config;

import com.bankle.common.userAuth.UserAuthSvc;
import com.bankle.common.userAuth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUserAuditorAware implements AuditorAware<String> {

    private final UserAuthSvc userAuthSvc;

    @Override
    public Optional<String> getCurrentAuditor() {
        try {
            UserPrincipal userPrincipal = userAuthSvc.getSessionUser();
            if (StringUtils.hasText(userPrincipal.getMembNo()))
                return Optional.ofNullable(userPrincipal.getMembNo());
            return null;
        } catch (Exception e) {
            return Optional.of("nosession");
        }
    }
}
