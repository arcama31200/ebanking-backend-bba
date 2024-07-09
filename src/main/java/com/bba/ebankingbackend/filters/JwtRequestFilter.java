package com.bba.ebankingbackend.filters;


import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

 @Autowired
 private UserDetailsService userDetailsService;

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
         throws ServletException, IOException {

     final String requestTokenHeader = request.getHeader("Authorization");

     String username = null;
     String jwtToken = null;

     if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
         jwtToken = requestTokenHeader.substring(7);
         try {
             Claims claims = Jwts.parser()
                     .setSigningKey("your_secret_key")
                     .parseClaimsJws(jwtToken)
                     .getBody();
             username = claims.getSubject();
         } catch (Exception e) {
             // gérer les erreurs
         }
     } else {
         logger.warn("JWT Token does not begin with Bearer String");
     }

     if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
         UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

         if (Boolean.TRUE.equals(Jwts.parser().setSigningKey("your_secret_key").parseClaimsJws(jwtToken).getBody().getSubject().equals(userDetails.getUsername()))) {
             UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                     userDetails, null, userDetails.getAuthorities());
             authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
         }
     }
     chain.doFilter(request, response);
 }
}

