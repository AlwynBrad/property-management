package com.alwyn.propertymanagement.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.JOSEException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
        ) throws ServletException, IOException {
          final String authHeader = request.getHeader("Authorization");
          final String jwt;
          String userEmail = null;
          if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);         
            return;   
          }

          jwt = authHeader.substring(7);
          try {
            userEmail = jwtService.extractUsername(jwt);
          } catch (JOSEException e) {
            String errorMessage = "Error extracting username from JWT: " + e.getMessage();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
            response.getWriter().write(errorMessage);
          }

          if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            try {
              if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                  userDetails,
                  null,
                  userDetails.getAuthorities()
                  );

                authToken.setDetails(
                  new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
              }
            } catch (JOSEException e) {
              String errorMessage = "Error while processing the JWT token: " + e.getMessage();
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getWriter().write(errorMessage);
            }

          }

          filterChain.doFilter(request, response);
    }
    
}
