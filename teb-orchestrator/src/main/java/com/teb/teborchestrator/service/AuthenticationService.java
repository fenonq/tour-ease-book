package com.teb.teborchestrator.service;

import com.teb.teborchestrator.model.request.SignInRequest;
import com.teb.teborchestrator.model.request.SignUpRequest;
import com.teb.teborchestrator.model.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse signUp(SignUpRequest request);

    TokenResponse signIn(SignInRequest request);

}
