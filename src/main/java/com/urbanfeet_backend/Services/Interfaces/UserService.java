package com.urbanfeet_backend.Services.Interfaces;

import com.urbanfeet_backend.Model.AuthResponse;
import com.urbanfeet_backend.Model.RegisterRequest;

public interface UserService {

    AuthResponse registerCliente(RegisterRequest request);
    AuthResponse registerAdmin(RegisterRequest request);
    
}
