package org.devcourse.shop_gamza.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.devcourse.shop_gamza.auth.Jwt;
import org.devcourse.shop_gamza.auth.JwtAuthenticationFilter;
import org.devcourse.shop_gamza.auth.JwtAuthenticationProvider;
import org.devcourse.shop_gamza.auth.JwtSecurityContextRepository;
import org.devcourse.shop_gamza.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {
    private final JwtProperties jwtProperties;

    public SecurityConfig(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, e) -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Object principal = authentication != null ? authentication.getPrincipal() : null;

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("ACCESS DENIED");
            response.getWriter().flush();
            response.getWriter().close();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Jwt jwt() {
        log.info("jwtProperties.getSecretKey() -> {}", jwtProperties.getSecretKey());
        log.info("jwtProperties.getExpirySeconds() -> {}", jwtProperties.getExpirySeconds());
        return new Jwt(
                jwtProperties.getIssuer(),
                jwtProperties.getSecretKey(),
                jwtProperties.getExpirySeconds()
        );
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(LoginService loginService, Jwt jwt) {
        return new JwtAuthenticationProvider(jwt, loginService);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            JwtAuthenticationProvider provider) {

        return new ProviderManager(provider);
    }

    public SecurityContextRepository securityContextRepository() {
        Jwt jwt = jwt();
        return new JwtSecurityContextRepository(jwtProperties.getHeader(), jwt);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/orders").hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtProperties.getHeader(), jwt()),
                        AuthorizationFilter.class
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .securityContext(context -> context
                        .securityContextRepository(securityContextRepository())
                );

        return http.build();
    }
}
