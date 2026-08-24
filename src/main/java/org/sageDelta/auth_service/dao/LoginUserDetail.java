package org.sageDelta.auth_service.dao;

import lombok.*;
import org.sageDelta.auth_service.enums.ProviderEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDetail implements UserDetails, Serializable {
    private String username;
    private String password;
    private ProviderEnum provider;
    private String email;
    private List<GrantedAuthority> authorities;
}
