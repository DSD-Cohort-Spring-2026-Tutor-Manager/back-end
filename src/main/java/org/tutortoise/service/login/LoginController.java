package org.tutortoise.service.login;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController handles user authentication endpoints.
 */
@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Login endpoint that accepts base64 encoded credentials.
     *
     * Request format:
     * {
     *   "email": "user@example.com",
     *   "credentials": "base64_encoded_username:password",
     *   "role": "parent|tutor|admin"
     * }
     *
     * @param loginRequest the login request
     * @return ResponseEntity with LoginResponse containing user details
     */
    @PostMapping
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login request received for email: {} with role: {}", loginRequest.getEmail(), loginRequest.getRole());

        try {
            LoginResponse response = loginService.authenticate(loginRequest);
            log.info("Login successful for user: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException | InvalidCredentialsException | InvalidRoleException e) {
            log.error("Login failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .success(false)
                            .message(e.getMessage())
                            .build());
        }
    }
}
