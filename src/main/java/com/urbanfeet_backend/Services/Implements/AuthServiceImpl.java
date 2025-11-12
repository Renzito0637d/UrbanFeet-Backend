package com.urbanfeet_backend.Services.Implements;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.urbanfeet_backend.Config.CookieUtils;
import com.urbanfeet_backend.Config.Auth.ForbiddenRefreshException;
import com.urbanfeet_backend.Config.Auth.InvalidTokenException;
import com.urbanfeet_backend.Config.Auth.JwtService;
import com.urbanfeet_backend.Config.Auth.TokenExpiredException;
import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.AuthenticationRequest;
import com.urbanfeet_backend.Services.Interfaces.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    private final JwtService jwtService;

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    // Configurables por entorno
    @Value("${app.cookies.secure:}")
    private boolean cookieSecure;

    @Value("${app.cookies.domain:}")
    private String cookieDomain;

    @Value("${app.cookies.samesite:}")
    private String cookieSameSite;

    @Value("${security.jwt.access-exp-seconds:3600}")
    private long accessExp;

    @Value("${security.jwt.refresh-exp-seconds:604800}")
    private long refreshExp;

    public AuthServiceImpl(JwtService jwtService, AuthenticationManager authManager,
            UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
    }

    public record LoginResult(ResponseCookie cookie, AuthResponse body) {
    }

    public record LogoutCookies(ResponseCookie delAccess, ResponseCookie delRefresh) {
    }

    public record MeResponse(Integer id, String email, String apellido, List<String> roles) {
    }

    @Override
    public LoginResult login(AuthenticationRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();
        String jwt = jwtService.generateAccessToken(user);

        boolean isCliente = user.getAuthorities().stream()
                .anyMatch(a -> "ROLE_CLIENTE".equals(a.getAuthority()));

        // CLIENTE => cookie persistente (maxAge). Otros roles => cookie de sesión (sin
        // maxAge).
        long maxAge = isCliente ? accessExp : 0;

        ResponseCookie cookie = CookieUtils.accessCookie(
                jwt,
                cookieSecure,
                emptyToNull(cookieDomain),
                cookieSameSite,
                maxAge);

        return new LoginResult(cookie, new AuthResponse("ok"));
    }

    /**
     * Obtiene la información del usuario autenticado actualmente.
     */
    public MeResponse getMe(Authentication auth) {
        Object principal = auth.getPrincipal();

        String email = (principal instanceof UserDetails ud)
                ? ud.getUsername()
                : principal.toString();

        // Si tu UserDetails implementa métodos personalizados como getId() o
        // getApellido()
        Integer id = tryInvoke(principal, "getId", Integer.class);
        String apellido = tryInvoke(principal, "getApellido", String.class);

        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // p.ej. "ROLE_ADMIN"
                .toList();

        return new MeResponse(id, email, apellido, roles);
    }

    public ResponseCookie refresh(String refreshToken) {
        if (refreshToken == null)
            throw new InvalidTokenException("no refresh token");
        if (!"refresh".equals(jwtService.getType(refreshToken)))
            throw new InvalidTokenException("bad token type");
        if (jwtService.isExpired(refreshToken))
            throw new TokenExpiredException("refresh token expired");

        String username = jwtService.getUsername(refreshToken);
        var user = userDetailsService.loadUserByUsername(username);

        // Solo permitir si sigue siendo CLIENTE
        boolean isCliente = user.getAuthorities().stream()
                .anyMatch(a -> "ROLE_CLIENTE".equals(a.getAuthority()));
        if (!isCliente)
            throw new ForbiddenRefreshException("forbidden");

        String newAccess = jwtService.generateAccessToken(user);
        return CookieUtils.accessCookie(
                newAccess,
                cookieSecure,
                emptyToNull(cookieDomain),
                cookieSameSite,
                accessExp);
    }

    /**
     * Genera las cookies de eliminación para el logout.
     */
    public LogoutCookies logout() {
        var delAccess = CookieUtils.delete("ACCESS_TOKEN", cookieSecure, emptyToNull(cookieDomain), cookieSameSite);
        var delRefresh = CookieUtils.delete("REFRESH_TOKEN", cookieSecure, emptyToNull(cookieDomain), cookieSameSite);
        return new LogoutCookies(delAccess, delRefresh);
    }

    // --- Helpers (movidos del Controller) ---
    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    @SuppressWarnings("unchecked")
    private static <T> T tryInvoke(Object obj, String method, Class<T> type) {
        try {
            return (T) obj.getClass().getMethod(method).invoke(obj);
        } catch (Exception e) {
            // Loguear el error podría ser útil
            // log.warn("No se pudo invocar el método {} en {}", method,
            // obj.getClass().getName(), e);
            return null;
        }
    }
}
