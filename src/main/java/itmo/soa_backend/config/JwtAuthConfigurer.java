package itmo.soa_backend.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthConfigurer extends AbstractHttpConfigurer<JwtAuthConfigurer, HttpSecurity> {
    
    private final JwtAuthService jwtAuthService;
    
    public JwtAuthConfigurer(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }
    
    @Override
    public void configure(HttpSecurity http) {
        JwtAuthFilter jwtAuthFilter = new JwtAuthFilter(jwtAuthService);
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}