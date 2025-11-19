package com.urbanfeet_backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.urbanfeet_backend.Config.Auth.JwtFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfig(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
        this.jwtFilter = jwtFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth

                        // 👉 Publico
                        .requestMatchers("/auth/**").permitAll()

                        // 👉 Rutas con roles (dejamos TODO igual)
                        .requestMatchers("/admin/**", "/pedidos/**",
                                "/inventario/**", "/ventas/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/pedidos/**")
                        .hasAnyRole("ADMIN", "PEDIDOS")

                        .requestMatchers("/inventario/**")
                        .hasRole("INVENTARIO")

                        .requestMatchers("/ventas/**")
                        .hasRole("VENTAS")

                        .requestMatchers("/cliente/**")
                        .hasRole("CLIENTE")
                        .requestMatchers("/directions/**").hasAnyRole("ADMIN", "CLIENTE")

                        // 👉 Requiere autenticación
                        .anyRequest().authenticated());

        return http.build();
    }
}
