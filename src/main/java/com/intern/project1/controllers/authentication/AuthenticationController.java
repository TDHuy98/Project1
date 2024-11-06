package com.intern.project1.controllers.authentication;

import com.intern.project1.entities.dto.AuthenticationRequest;
import com.intern.project1.entities.dto.AuthenticationResponse;
import com.intern.project1.entities.dto.RegisterRequest;
import com.intern.project1.services.authentication.AuthenticationService;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register{userName}")
    public ResponseEntity<AuthenticationResponse> registerForGuest(
            @Valid @RequestBody RegisterRequest request, @Nullable @PathVariable String userName
    ) {
        return ResponseEntity.ok(service.register(userName, request));
    }

//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @Valid @RequestBody RegisterRequest guest, @Nullable @PathVariable String userId
//    ) {
//        return ResponseEntity.ok(service.register(userId, guest));
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
