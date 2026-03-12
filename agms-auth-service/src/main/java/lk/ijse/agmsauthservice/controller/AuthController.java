package lk.ijse.agmsauthservice.controller;

import lk.ijse.agmsauthservice.dto.AuthResponse;
import lk.ijse.agmsauthservice.dto.LoginRequest;
import lk.ijse.agmsauthservice.dto.RegisterRequest;
import lk.ijse.agmsauthservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get( "refreshToken");
        String newAccessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }
}