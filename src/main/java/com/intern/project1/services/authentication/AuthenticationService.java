package com.intern.project1.services.authentication;

import com.intern.project1.entities.User;
import com.intern.project1.entities.dto.AuthenticationRequest;
import com.intern.project1.entities.dto.AuthenticationResponse;
import com.intern.project1.entities.dto.LoginRequest;
import com.intern.project1.entities.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerUser) throws RuntimeException;

    AuthenticationResponse register(String userName, RegisterRequest request);

    boolean checkEmailExisted(RegisterRequest request);

    boolean checkMobileExisted(RegisterRequest request);

    boolean checkUsernameExisted(RegisterRequest request);

    boolean isLoginInformationMathched(AuthenticationRequest authenticationRequest);

    AuthenticationResponse authenticate(AuthenticationRequest request);


    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}
