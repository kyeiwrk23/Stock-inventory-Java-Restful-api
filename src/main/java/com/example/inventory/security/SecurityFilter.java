package com.example.inventory.security;

import com.example.inventory.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtils tokenUtils;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseHeaderCookieToken(request);
            if (token != null && tokenUtils.validateToken(token)) {
                String username = tokenUtils.generateUsernameFromToken(token);
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            logger.error("Cannot set user authentication {}");
        }

        filterChain.doFilter(request, response);

    }

    public String parseHeaderCookieToken(HttpServletRequest request) {
        String cookieToken = tokenUtils.getTokenFromCookie(request);
        if (cookieToken != null) {
            return cookieToken;
        }

        String tokenHeader = tokenUtils.getTokenFromHeader(request);
        if (tokenHeader != null) {
            return tokenHeader;
        }
        return  null;
    }
}
