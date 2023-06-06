package com.quadrangle.depofenvkmla.Config;

import com.quadrangle.depofenvkmla.Filter.CsrfCookieFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {

    public ProjectSecurityConfig() {
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");

        http.securityContext((context) -> context.requireExplicitSave(false))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));
        http.cors((cors) -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(7300L);
            return config;
        }));
        http.csrf((csrf) -> csrf.csrfTokenRequestHandler(requestAttributeHandler).ignoringRequestMatchers("/api/v1/users/get/post")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers("/api/v1/users/get/**").authenticated()
                        .requestMatchers("/api/v1/users/delete").authenticated()
                        .requestMatchers("/api/v1/users/update/**").authenticated()
                        .requestMatchers("/api/v1/users/post").permitAll()
                        .requestMatchers("/api/v1/daily/**").authenticated()
                        .requestMatchers("/api/v1/weekly/**").authenticated()
                        .requestMatchers("/api/v1/biweekly/**").authenticated()
                        .requestMatchers("/api/v1/final/**").authenticated());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
