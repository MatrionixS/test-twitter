package com.rusyn.test_twitter.security

import com.rusyn.test_twitter.model.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class SessionSecurityFilter extends OncePerRequestFilter{

    private final CustomAuthenticationManager authenticationManager;

    SessionSecurityFilter(CustomAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        def user = (User) request.getSession().getAttribute("user")
        def authentication = new SessionAuthentication(false, user)
        authentication = authenticationManager.authenticate(authentication)
        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication)
        }
        filterChain.doFilter(request, response)
    }
}
