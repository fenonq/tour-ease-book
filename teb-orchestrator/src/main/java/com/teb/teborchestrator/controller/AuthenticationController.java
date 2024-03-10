package com.teb.teborchestrator.controller;

import com.teb.teborchestrator.model.request.SignInRequest;
import com.teb.teborchestrator.model.request.SignUpRequest;
import com.teb.teborchestrator.model.response.TokenResponse;
import com.teb.teborchestrator.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public TokenResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signIn")
    public TokenResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

}
