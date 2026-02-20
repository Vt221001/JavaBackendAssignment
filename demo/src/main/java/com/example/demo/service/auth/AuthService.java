package com.example.demo.service.auth;



import com.example.demo.dto.user.AuthRequest;
import com.example.demo.dto.user.AuthResponse;
import com.example.demo.dto.user.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request, HttpServletResponse response);
    AuthResponse login(AuthRequest request, HttpServletResponse response);
}
