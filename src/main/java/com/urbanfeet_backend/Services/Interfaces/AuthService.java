package com.urbanfeet_backend.Services.Interfaces;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

import com.urbanfeet_backend.Model.AuthDTOs.AuthenticationRequest;
import com.urbanfeet_backend.Services.Implements.AuthServiceImpl.LoginResult;
import com.urbanfeet_backend.Services.Implements.AuthServiceImpl.LogoutCookies;
import com.urbanfeet_backend.Services.Implements.AuthServiceImpl.MeResponse;

public interface AuthService {
    
    public LoginResult login(AuthenticationRequest req);

    public MeResponse getMe(Authentication auth);

    public ResponseCookie refresh(String refreshToken);

    public LogoutCookies logout();
}
