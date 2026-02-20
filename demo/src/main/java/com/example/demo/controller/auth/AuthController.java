package com.example.demo.controller.auth;



import com.example.demo.dto.user.AuthRequest;
import com.example.demo.dto.user.AuthResponse;
import com.example.demo.dto.user.RegisterRequest;
import com.example.demo.service.auth.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        return authService.register(request, response);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody AuthRequest request,
            HttpServletResponse response
    ) {
        return authService.login(request, response);
    }
}