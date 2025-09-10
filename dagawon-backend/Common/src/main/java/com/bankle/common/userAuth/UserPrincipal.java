package com.bankle.common.userAuth;


import com.bankle.common.asis.domain.dto.MembersDto;
import com.bankle.common.dto.TbCustMasterDto;
import com.bankle.common.enums.Role;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ToString
public class UserPrincipal implements  UserDetails {

    @Getter
    private final String membNo;
    @Getter
    private final String membNm;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String membNo , String membNm, Collection<? extends GrantedAuthority> authorities) {
        this.membNo = membNo;
        this.membNm = membNm;
        this.authorities = authorities;
    }

    public static UserPrincipal create(TbCustMasterDto user) {
        List<GrantedAuthority> authorities = Collections
                                                     .singletonList(new SimpleGrantedAuthority(Role.USER.toString()));
        return new UserPrincipal(
                user.getMembNo(),
                user.getMembNm(),
                authorities);
    }
    public String getId() {
        return "";
    }

    public String getEmail() {
        return "";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return membNm;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
