package com.rusyn.test_twitter.security


import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

@Component
class SessionAuthenticationProvider implements AuthenticationProvider {


    @Override
    Authentication authenticate(Authentication authentication) throws AuthenticationException {
        authentication = (SessionAuthentication) authentication
        if (authentication.getUser() != null) {
            authentication.setAuthenticated(true)
            return authentication
        }
        return authentication
    }

    @Override
    boolean supports(Class<?> authentication) {
        return SessionAuthentication.class == authentication
    }
}
