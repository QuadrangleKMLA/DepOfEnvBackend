package com.quadrangle.depofenvkmla.Security;

import com.quadrangle.depofenvkmla.Documents.User.UserDetailsServiceImpl;
import com.quadrangle.depofenvkmla.Security.JWT.AuthEntryPointJwt;
import com.quadrangle.depofenvkmla.Security.JWT.AuthTokenFilter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ProjectSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    public ProjectSecurityConfig() {
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NonNull HttpSecurity http) throws Exception {

        http.cors((cors) -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowCredentials(true);
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setMaxAge(7300L);
            return config;
        }));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((auth) ->
                auth
                        .requestMatchers("/api/v1/users/get").permitAll()
                        .requestMatchers("/api/v1/users/get/**").permitAll()
                        .requestMatchers("/api/v1/users/delete").authenticated()
                        .requestMatchers("/api/v1/users/update/**").authenticated()
                        .requestMatchers("/api/v1/users/post").permitAll()
                        .requestMatchers("/api/v1/daily/get").permitAll()
                        .requestMatchers("/api/v1/daily/get/**").permitAll()
                        .requestMatchers("/api/v1/daily/put").authenticated()
                        .requestMatchers("/api/v1/weekly/get").permitAll()
                        .requestMatchers("/api/v1/weekly/get/**").permitAll()
                        .requestMatchers("/api/v1/weekly/put/**").authenticated()
                        .requestMatchers("/api/v1/biweekly/get").permitAll()
                        .requestMatchers("/api/v1/biweekly/get/**").permitAll()
                        .requestMatchers("/api/v1/biweekly/put/**").authenticated()
                        .requestMatchers("/api/v1/final/get").permitAll()
                        .requestMatchers("/api/v1/final/get/**").permitAll()
                        .requestMatchers("/api/v1/final/put/**").authenticated()
                        .requestMatchers("/api/v1/auth/**").permitAll());
        http.formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .securityContext((securityContext) -> securityContext.securityContextRepository(new HttpSessionSecurityContextRepository())
        );
        return http.build();
    }

    @Bean
    PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
