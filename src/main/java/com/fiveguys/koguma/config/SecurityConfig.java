package com.fiveguys.koguma.config;

import com.fiveguys.koguma.util.filter.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("securityConfig 진입");
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsFilter)
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .antMatchers(
                        "/product/**",
                        "/club/**",
                        "/post/**",
                        "/chat/**",
                        "/comment/**",
                        "/relationship/**",
                        "/paymenthistory/**",
                        "/review/**",
                            "/image/**",
//                        "/review/**"
//                ).hasAnyRole("ROLE_AUTH_MEMBER","ROLE_ADMIN")
//                .antMatchers(
                        "/member/**",
                        "/location/**"
                ).authenticated();

//                .and()
//                .oauth2Login().loginPage("/api/auth/login");
        http.logout()
                .logoutUrl("/auth/logout")  //Post 요청시, client에서 sessionStrage에서 accessToken 삭제
                .logoutSuccessHandler((request, response, authentication) -> {
                    System.out.println("로그아웃");
                    response.sendRedirect("/auth/login");
                })
                .deleteCookies("refreshToken");

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}