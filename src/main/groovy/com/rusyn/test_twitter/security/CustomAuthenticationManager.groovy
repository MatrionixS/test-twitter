package com.rusyn.test_twitter.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationManager implements AuthenticationManager{

    private final SessionAuthenticationProvider authenticationProvider;

    CustomAuthenticationManager(SessionAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider
    }

    @Override
    Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authenticationProvider.supports(authentication.class)) {
            return authenticationProvider.authenticate(authentication)
        }
        throw new BadCredentialsException("Not found authentication provider for %s class".formatted(authentication.class))
    }
}
