package ra.project5.cecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.project5.cecurity.jwt.JwtAuthTokenFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint(){
        return  new CustomAuthenticationEntryPoint();
    }
    @Bean
    AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
        return  config.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(cus -> cus.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.error("errr", accessDeniedException.getMessage());
                    response.setStatus(403);
                    response.setHeader("error", "Forbiden");
                    Map<String, String> map = new HashMap<>();
                    map.put("message", "Bạn ko có quyền tuy cập");
                    new ObjectMapper().writeValue(response.getOutputStream(), map);
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(cus -> cus.requestMatchers("/api/v1/catalogs/**","/api/v1/products/**","/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/shopping-cart/**","/api/v1/account/**","/api/v1/wish-list/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
