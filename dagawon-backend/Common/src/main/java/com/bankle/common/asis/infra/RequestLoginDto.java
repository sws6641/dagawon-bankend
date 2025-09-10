package com.bankle.common.asis.infra;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestLoginDto {

    @NotEmpty(message = "멤버ID가 존재하지 않습니다.")
    private String username;

    @NotNull(message = "패스워드 존재하지 않습니다!")
    @Size(min = 6, message = "이메일은 최소 6자 이상이어야 합니다.")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
