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

            // If the Authorization header is missing or doesn't start with "Bearer", continue to the next filter.
            filterChain.doFilter(request, response);         
            return;   
          }

          // Extract the JWT token from the Authorization header.
          jwt = authHeader.substring(7);
          try {

             // Extract the user email from the JWT token.
            userEmail = jwtService.extractUsername(jwt);
          } catch (JOSEException e) {

            // Handle the error if there's an issue with extracting the username from the JWT.
            String errorMessage = "Error extracting username from JWT: " + e.getMessage();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); 
            response.getWriter().write(errorMessage);
          }

          // Check if the user email is not null and the user is not already authenticated.
          if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null ) {

            // Load the user details from the user service using the extracted email.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            try {

              // Check if the JWT token is valid for the user details.
              if (jwtService.isTokenValid(jwt, userDetails)) {

                 // Create an authentication token and set it in the security context.
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

              // Handle the error if there's an issue with processing the JWT token.
              String errorMessage = "Error while processing the JWT token: " + e.getMessage();
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              response.getWriter().write(errorMessage);
            }

          }

          // Continue with the filter chain.
          filterChain.doFilter(request, response);
    }
    
}
