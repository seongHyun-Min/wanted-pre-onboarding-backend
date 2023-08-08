package com.example.wantedboard.config.security;

import com.example.wantedboard.domain.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;


public class UserAuthentication extends AbstractAuthenticationToken {
    private final Long userId;

    public UserAuthentication(User user) {
        super(null);
        this.userId = user.getId();
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
