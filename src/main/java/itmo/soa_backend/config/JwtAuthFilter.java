package itmo.soa_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    
    private final JwtAuthService jwtAuthService;
    
    public JwtAuthFilter(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        String token = jwtAuthService.resolveToken(request.getHeader("Authorization"));
        
        if (token != null && jwtAuthService.validateToken(token)) {
            try {
                Authentication auth = jwtAuthService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                System.err.println("Cannot set authentication: " + e.getMessage());
                SecurityContextHolder.clearContext();
            }
        }
        
        filterChain.doFilter(request, response);
    }
}