package org.tutortoise.service.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginRequest DTO for user authentication.
 * Expects base64 encoded credentials in format "username:password"
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Credentials (base64 encoded 'username:password') are required")
    private String credentials;

    @NotBlank(message = "Role is required")
    private String role; // "parent", "tutor", "admin"
}

