package com.urbanfeet_backend.Controller;

import java.util.Map;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.AuthenticationRequest;
import com.urbanfeet_backend.Model.RegisterRequest;
import com.urbanfeet_backend.Services.Implements.AuthServiceImpl.LoginResult;
import com.urbanfeet_backend.Services.Implements.AuthServiceImpl.LogoutCookies;
import com.urbanfeet_backend.Services.Implements.AuthServiceImpl.MeResponse;
import com.urbanfeet_backend.Services.Interfaces.AuthService;
import com.urbanfeet_backend.Services.Interfaces.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final UserService userService;

        private final AuthService authService;

        public AuthController(AuthService authService, UserService userService) {
                this.authService = authService;
                this.userService = userService;
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody AuthenticationRequest req) {
                LoginResult result = authService.login(req);

                return ResponseEntity.ok()
                                .header("Set-Cookie", result.cookie().toString())
                                .body(result.body());
        }

        @GetMapping("/me")
        public MeResponse me(Authentication auth) {
                return authService.getMe(auth);
        }

        @PostMapping("/refresh")
        public ResponseEntity<?> refresh(@CookieValue(value = "REFRESH_TOKEN", required = false) String refreshToken) {
                ResponseCookie newAccessCookie = authService.refresh(refreshToken);

                return ResponseEntity.ok()
                                .header("Set-Cookie", newAccessCookie.toString())
                                .body(Map.of("message", "refreshed"));
        }

        @PostMapping("/logout")
        public ResponseEntity<?> logout() {
                LogoutCookies cookies = authService.logout();

                return ResponseEntity.ok()
                                .header("Set-Cookie", cookies.delAccess().toString())
                                .header("Set-Cookie", cookies.delRefresh().toString())
                                .body(Map.of("message", "logged out"));
        }

        @PostMapping("/registerCliente")
        public ResponseEntity<AuthResponse> registerCliente(@RequestBody RegisterRequest request) {
                return ResponseEntity.ok().body(userService.registerCliente(request));
        }

}
