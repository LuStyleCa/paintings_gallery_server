package com.toonborghuis.gallery.paintings_gallery.auth.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.toonborghuis.gallery.paintings_gallery.auth.entities.User;
import com.toonborghuis.gallery.paintings_gallery.auth.entities.UserRole;
import com.toonborghuis.gallery.paintings_gallery.auth.repositories.UserRepository;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.AuthResponse;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.LoginRequest;
import com.toonborghuis.gallery.paintings_gallery.auth.utils.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final RefreshTokenService refreshTokenService;
        private final AuthenticationManager authenticationManager;

        public AuthResponse register(RegisterRequest registerRequest) {
                var user = User.builder()
                                .email(registerRequest.getEmail())
                                .username(registerRequest.getUsername())
                                .password(passwordEncoder.encode(registerRequest.getPassword()))
                                .role(UserRole.USER)
                                .build();

                User savedUser = userRepository.save(user);
                var accessToken = jwtService.generateToken(savedUser);
                var refreshToken = refreshTokenService.createRefreshToken(savedUser.getEmail());

                return AuthResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken.getRefreshToken())
                                .email(savedUser.getEmail())
                                .build();
        }

        public AuthResponse login(LoginRequest loginRequest) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                loginRequest.getEmail(),
                                                loginRequest.getPassword()));

                var user = userRepository.findByEmail(loginRequest.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
                var accessToken = jwtService.generateToken(user);
                var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

                return AuthResponse.builder()
                                .id(user.getUserId())
                                .accessToken(accessToken)
                                .refreshToken(refreshToken.getRefreshToken())
                                .email(user.getEmail())
                                .role(user.getRole().name())
                                .build();
        }
}
