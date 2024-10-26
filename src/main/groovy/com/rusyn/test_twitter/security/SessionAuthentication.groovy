package com.rusyn.test_twitter.security

import com.rusyn.test_twitter.model.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class SessionAuthentication implements Authentication {

    SessionAuthentication(boolean isAuthenticated, User user) {
        this.user = user
        this.isAuthenticated = isAuthenticated
    }

    private User user
    private boolean isAuthenticated;

    @Override
    boolean isAuthenticated() {
        return isAuthenticated
    }

    @Override
    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated
    }

    User getUser() {
        return user
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("read"))
    }

    @Override
    Object getCredentials() {
        return null
    }

    @Override
    Object getDetails() {
        return user
    }

    @Override
    Object getPrincipal() {
        return user
    }

    @Override
    String getName() {
        return null
    }
}
