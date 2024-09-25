package com.developer.fillme.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class UserInfoDetail implements UserDetails {
    private String userId;
    private String username;
    private String password;
    private String status;
    private String email;
    private String mobile;
    private Set<GrantedAuthority> authorities;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoDetail user = (UserInfoDetail) o;
        return Objects.equals(userId, user.userId);
    }
}
