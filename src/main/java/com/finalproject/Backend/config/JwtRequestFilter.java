package com.finalproject.Backend.config;

import java.io.IOException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; 
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import com.finalproject.Backend.security.JwtUtils;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
     private JwtUtils jwtUtils;

    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }
    

    @Override 
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
           throws ServletException, IOException {

       String path = request.getRequestURI();
       String method = request.getMethod();

       if (method.equals("OPTIONS")) {
           response.setStatus(HttpServletResponse.SC_OK);
           chain.doFilter(request, response);
           return;
       }

       if (path.contains("/api/auth") || 
           path.contains("/api/countries/all") || 
           path.contains("/api/images/") ||
           (path.contains("/api/bookmarks") && method.equals("GET")) ||
           path.contains("/api/categories/all") ||
           path.contains("/api/tags/all")) {
           chain.doFilter(request, response);
           return;
       }
       
       final String requestTokenHeader = request.getHeader("Authorization");
       
       String email = null;
       String jwtToken = null;
       
       if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
           jwtToken = requestTokenHeader.substring(7);
           try {
               email = jwtUtils.getEmailFromJwtToken(jwtToken);
           } catch (IllegalArgumentException e) {
               logger.error("Unable to get JWT token");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unable to get JWT token");
                return;
           } catch (ExpiredJwtException e) {
               logger.error("JWT token expired");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "JWT token expired");
                return;
           }
       } else {
           logger.warn("JWT Token does not begin with Bearer String");
       }

       if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
           
           if (jwtUtils.validateJwtToken(jwtToken)) {
               UsernamePasswordAuthenticationToken authenticationToken = 
                       new UsernamePasswordAuthenticationToken(
                               userDetails, null, userDetails.getAuthorities());
               
               authenticationToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request));
               
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT Token");
                return;
            }
       }
       
       chain.doFilter(request, response);
   }
}
