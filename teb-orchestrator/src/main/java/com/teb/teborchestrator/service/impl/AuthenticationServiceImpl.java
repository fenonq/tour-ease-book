package com.teb.teborchestrator.service.impl;

import com.teb.teborchestrator.jwt.JwtService;
import com.teb.teborchestrator.model.entity.User;
import com.teb.teborchestrator.model.enums.Role;
import com.teb.teborchestrator.model.request.SignInRequest;
import com.teb.teborchestrator.model.request.SignUpRequest;
import com.teb.teborchestrator.model.response.TokenResponse;
import com.teb.teborchestrator.service.AuthenticationService;
import com.teb.teborchestrator.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public TokenResponse signUp(SignUpRequest request) {
        log.info("Signup user with username {}", request.getUsername());
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        userService.create(user);

        return TokenResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }

    public TokenResponse signIn(SignInRequest request) {
        log.info("Login user with username {}", request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = (User) userService.userDetailsService().loadUserByUsername(request.getUsername());
        return TokenResponse.builder()
                .accessToken(jwtService.generateToken(user))
                .build();
    }
}
