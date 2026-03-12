package lk.ijse.agmsauthservice.dto;

import lk.ijse.agmsauthservice.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}
