package com.commercial.commerce.UserAuth.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commercial.commerce.UserAuth.Request.AuthenticationRequest;
import com.commercial.commerce.UserAuth.Request.RefreshTokenRequest;
import com.commercial.commerce.UserAuth.Request.RegisterRequest;
import com.commercial.commerce.UserAuth.Service.AuthService;
import com.commercial.commerce.UserAuth.Service.CheckStatusService;
import com.commercial.commerce.UserAuth.Service.RefreshTokenService;
import com.commercial.commerce.Utils.Status;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final RefreshTokenService refreshTokenService;
    private final CheckStatusService checkStatusService;

    @GetMapping("/register")
    public ResponseEntity<Object> getRegisterData() {
        return ResponseEntity.ok(service.getRegisterData());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/check")
    public ResponseEntity<Object> checkEmailAvailable(
            @PathParam(value = "email") String email) {
        System.out.println(email);
        return ResponseEntity.ok(!service.chekcIfAlreadyExist(email));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        ///
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/checkTokenStatus")
    public ResponseEntity<Object> checkStatus(
            @RequestBody CheckStatusResponse request) {
        ///
        return ResponseEntity.ok(checkStatusService.checkAll(request));
    }

    @DeleteMapping("/refreshToken")
    public ResponseEntity<Object> removeRefreshToken(
            @RequestBody RefreshTokenRequest request) {
        refreshTokenService.removeRefreshToken(request.getRefresh_token());
        return ResponseEntity.ok(Status.builder().status("good").details("refresh token removed").build());
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<Object> refreshToken(
            @RequestBody RefreshTokenRequest request) {
        ///
        try {
            return ResponseEntity.ok(service.useRefreshToken(request));
        } catch (Exception e) {
            return ResponseEntity.ok(Status.builder().status("error").details(e.getMessage()).build());
        }

    }

}
