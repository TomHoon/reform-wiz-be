package com.reform.wiz.config;

import java.util.Arrays;

import com.reform.wiz.security.filter.filter.JWTCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
@EnableMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {

 private JWTCheckFilter jwtCheckFilter;

 @Bean
 public PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }

 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

  http.csrf(config -> config.disable());

  http.logout(config -> config.disable());

  http.formLogin(httpSecurityFormLoginConfigurer -> {
   httpSecurityFormLoginConfigurer.disable();
  });

  // cors 제거
  http.cors(httpSecurityCorsConfigurer -> {
   httpSecurityCorsConfigurer.configurationSource(configurationSource());
  });

  http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.NEVER));

  http.addFilterBefore(jwtCheckFilter, UsernamePasswordAuthenticationFilter.class);

  return http.build();
 }

 @Bean
 public CorsConfigurationSource configurationSource() {
  CorsConfiguration configuration = new CorsConfiguration();

  configuration.setAllowedOrigins(Arrays.asList(
    "http://localhost:3000",
    "https://reformwiz.com"));

  configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
  configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
  configuration.setAllowCredentials(true);

  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", configuration);

  return source;
 }
}
