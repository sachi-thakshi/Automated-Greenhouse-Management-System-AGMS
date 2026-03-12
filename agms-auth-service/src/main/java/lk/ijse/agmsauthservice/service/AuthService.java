package lk.ijse.agmsauthservice.service;

import lk.ijse.agmsauthservice.dto.AuthResponse;
import lk.ijse.agmsauthservice.dto.LoginRequest;
import lk.ijse.agmsauthservice.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse login(LoginRequest request);
    void register(RegisterRequest request);
    String refreshToken(String refreshToken);
}
