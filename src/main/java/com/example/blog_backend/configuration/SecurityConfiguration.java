package com.example.blog_backend.configuration;

import com.example.blog_backend.util.security.JWTFilter;
import com.example.blog_backend.util.security.JwtAccessDeniedHandler;
import com.example.blog_backend.util.security.JwtAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTFilter filter;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    private static final String[] AUTH_WHITELIST = {
            "/api/auth/**",
            "/swagger-ui/**",
            "v3/api-docs/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**",

            "/api/post/get-all-page-by-filter",
            "/api/post/comment/get-all-page-by-filter",
            "api/post/reaction/get-all-page-by-filter",


            "/api/post/get/*",
            "/api/post/get-all",

            "/api/user/get/profile/*",
            "/api/category/**",

            "/api/context-type/**",

    };

    private static final String[] USER_AUTH_WHITELIST = {
            "/api/user/update/*",

            "/api/post/create",
            "/api/post/update/*",
            "/api/post/publish-request/*",
            "/api/post/delete/*",
            "/api/post/my-posts",

            "/api/post/reaction/create",
            "/api/post/reaction/delete/*",

            "/api/comment/reaction/create",
            "/api/comment/reaction/delete/*",

            "/api/post/comment/create",
            "/api/post/comment/reply/*",
            "/api/post/comment/update/*",
            "/api/post/comment/delete/*",

            /*"/api/post/comment/get-all",*/
            "/api/post/comment/get/*", // geçici adminde olacak userda değil test amaçlı koydum

  /*          "/api/post/get-all-page-by-filter",*/
    };

    private static final String[] USER_ADMIN_AUTH_WHITELIST = {
            /*"/user/delete/**",*/
    };

    private static final String[] ADMIN_AUTH_WHITELIST = {
            "/api/role/**",
            "/api/admin/**",
            /*"/api/comment/get-all",
            "/api/comment/get/*"*/
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // CSRF koruması devre dışı
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(List.of("*"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                    configuration.setAllowedHeaders(List.of("*"));
                    configuration.setExposedHeaders(List.of("Content-Disposition"));
                    return configuration;
                }))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(AUTH_WHITELIST).permitAll()
                            .requestMatchers(USER_ADMIN_AUTH_WHITELIST).hasAnyRole("ADMIN","USER")
                            .requestMatchers(USER_AUTH_WHITELIST).hasRole("USER")
                            .requestMatchers(ADMIN_AUTH_WHITELIST).hasRole("ADMIN");

                            /*.anyRequest().authenticated();*/ // Diğer tüm istekler doğrulama gerektirir
                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session yönetimi
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(unauthorizedHandler)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // JWT Filtresi

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
