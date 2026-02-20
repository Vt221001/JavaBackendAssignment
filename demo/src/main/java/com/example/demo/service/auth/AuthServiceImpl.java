package com.example.demo.service.auth;


import com.example.demo.config.security.JwtService;

import com.example.demo.dto.user.AuthRequest;
import com.example.demo.dto.user.AuthResponse;
import com.example.demo.dto.user.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.ApiException;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.PasswordEncoder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request, HttpServletResponse response) {

        if (userRepository.findByEmail(request.getEmail().toLowerCase()).isPresent()) {
            throw new ApiException("Email already registered");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail().toLowerCase())
                .password(PasswordEncoder.passwordEncoder().encode(request.getPassword()))
                .build();

        userRepository.save(user);

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        saveRefreshToken(user, refreshToken);
        addCookies(response, accessToken, refreshToken);

        return new AuthResponse("success", "User registered successfully");
    }

    @Override
    public AuthResponse login(AuthRequest request, HttpServletResponse response) {

        User user = userRepository.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new ApiException("Invalid email or password"));

        boolean matches = PasswordEncoder.passwordEncoder()
                .matches(request.getPassword(), user.getPassword());

        if (!matches) {
            throw new ApiException("Invalid email or password");
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        saveRefreshToken(user, refreshToken);
        addCookies(response, accessToken, refreshToken);

        return new AuthResponse("success", "Login successful");
    }

    private void saveRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(Instant.now().plusSeconds(15 * 60));
        userRepository.save(user);
    }

    private void addCookies(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessCookie = new Cookie("access_token", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 3);

        Cookie refreshCookie = new Cookie("refresh_token", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60 * 15);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}