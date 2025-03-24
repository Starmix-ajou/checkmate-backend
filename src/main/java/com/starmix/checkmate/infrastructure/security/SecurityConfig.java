package com.starmix.checkmate.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        http.cors(cors -> {});
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(HttpBasicConfigurer::disable);
        http.authorizeHttpRequests((authorizeRequests) -> {
//          TODO: 인가 필요한 API 추가 시 엔드포인트 등록
//          authorizeRequests.requestMatchers(ApiPaths.AUTHENTICATED_ENDPOINTS).authenticated();
            authorizeRequests.anyRequest().permitAll();
        });
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(
                jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
        );
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        return http.build();
    }
}