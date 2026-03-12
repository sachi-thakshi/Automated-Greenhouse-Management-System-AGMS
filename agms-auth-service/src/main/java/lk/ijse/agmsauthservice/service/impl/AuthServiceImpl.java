package lk.ijse.agmsauthservice.service.impl;

import lk.ijse.agmsauthservice.dto.AuthResponse;
import lk.ijse.agmsauthservice.dto.LoginRequest;
import lk.ijse.agmsauthservice.dto.RegisterRequest;
import lk.ijse.agmsauthservice.entity.User;
import lk.ijse.agmsauthservice.repository.UserRepository;
import lk.ijse.agmsauthservice.service.AuthService;
import lk.ijse.agmsauthservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            return new AuthResponse(accessToken, refreshToken);
        }
        throw new RuntimeException("Invalid Credentials");
    }

    @Override
    public void register(RegisterRequest request) {
        if(userRepository.existsById(request.getEmail())) {
            throw new RuntimeException("User already exists!");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
    }

    public String refreshToken(String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {
            String email = jwtUtil.extractUsername(refreshToken);
            User user = userRepository.findById(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (refreshToken.equals(user.getRefreshToken())) {
                return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
            }
        }
        throw new RuntimeException("Invalid or Expired Refresh Token");
    }
}
