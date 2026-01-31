package com.example.demo.config;




import com.example.demo.service.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration

@EnableWebSecurity

public class SecurityConfig {



    private final CustomUserDetailsService userDetailsService;



    public SecurityConfig(CustomUserDetailsService userDetailsService) {

        this.userDetailsService = userDetailsService;

    }



// Bean mã hóa mật khẩu sử dụng BCrypt

    @Bean

    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }



// Cấu hình AuthenticationManager từ cấu hình Spring Security

    @Bean

    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();

    }



// Cấu hình chính cho Spring Security (filter chain)

    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http

                .authorizeHttpRequests(auth -> auth

// Cho phép truy cập không cần đăng nhập với các đường dẫn sau:

                                .requestMatchers("/", "/Home/**", "/category/**", "/cart/**", "/product/**", "/search",

                                        "/search/**", "/api/products/**",

                                        "/feedback/**", "/detail/**", "/init-data",

                                        "/css/**", "/js/**", "/images/**", "/videos/**", "/static/**")

                                .permitAll()

// Chỉ cho phép ROLE_ADMIN truy cập vào /admin/**

                                .requestMatchers("/admin/**").hasRole("ADMIN")

// Các request còn lại đều được phép truy cập mà không cần đăng nhập

                                .anyRequest().permitAll()

                )

// Cấu hình form đăng nhập (vẫn giữ để người dùng có thể đăng nhập nếu muốn)

                .formLogin(form -> form

                        .loginPage("/Home/login")

                        .loginProcessingUrl("/login")

                        .successHandler(customAuthSuccessHandler())

                        .failureUrl("/Home/login?error")

                        .permitAll())

// Cấu hình logout

                .logout(logout -> logout

                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))

                        .logoutSuccessUrl("http://localhost:9090")

                        .permitAll())

// Vô hiệu hóa CSRF cho các route public

                .csrf(csrf -> csrf

                        .ignoringRequestMatchers("/", "/Home/**", "/category/**", "/cart/**", "/product/**",

                                "/detail/**", "/feedback/**", "/search/**", "/init-data")

                        .disable());



        return http.build();

    }



// Tùy chỉnh hành vi sau khi đăng nhập thành công

    public AuthenticationSuccessHandler customAuthSuccessHandler() {

        return (request, response, authentication) -> {

            var roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());



            if (roles.contains("ROLE_ADMIN")) {

                response.sendRedirect("/admin/dashboard");

            } else {

                response.sendRedirect("/");

            }

        };

    }

}

