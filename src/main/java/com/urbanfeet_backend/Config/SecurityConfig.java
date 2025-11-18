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
                // Stateless: JWT, sin sesiones del servidor
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Provider (Boot lo autoconfigura si expusiste UserDetailsService +
                // PasswordEncoder)
                .authenticationProvider(authenticationProvider)
                // Orden correcto del filtro JWT
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                // Autorización por rutas/roles
                .authorizeHttpRequests(auth -> auth
                        // Bloque original de roles comentado para pruebas
                        /*
                         * .requestMatchers("/admin/**", "/pedidos/**", "/inventario/**",
                         * "/ventas/**").hasRole("ADMIN")
                         * .requestMatchers("/pedidos/**").hasAnyRole("ADMIN", "PEDIDOS")
                         * .requestMatchers("/inventario/**").hasRole("INVENTARIO")
                         * .requestMatchers("/ventas/**").hasRole("VENTAS")
                         * .requestMatchers("/cliente/**").hasRole("CLIENTE")
                         * .anyRequest().authenticated()
                         */

                        // Nueva línea para probar todos los endpoints sin seguridad
                        .anyRequest().permitAll());

        return http.build();
    }

}
