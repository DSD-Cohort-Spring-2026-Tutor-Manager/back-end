package org.tutortoise.service.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginResponse DTO for successful authentication response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String message;
    private boolean success;
}

