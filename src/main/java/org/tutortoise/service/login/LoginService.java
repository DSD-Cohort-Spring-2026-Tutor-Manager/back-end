package org.tutortoise.service.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tutortoise.service.admin.Admin;
import org.tutortoise.service.admin.AdminRepository;
import org.tutortoise.service.parent.Parent;
import org.tutortoise.service.parent.ParentRepository;
import org.tutortoise.service.tutor.Tutor;
import org.tutortoise.service.tutor.TutorRepository;

/**
 * LoginService handles user authentication logic.
 * Supports three roles: PARENT, TUTOR, and ADMIN.
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param loginRequest the login request containing email, credentials, and role
     * @return LoginResponse with user details if authentication is successful
     * @throws InvalidRoleException if the role is not valid
     * @throws UserNotFoundException if the user is not found
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    public LoginResponse authenticate(LoginRequest loginRequest) {
        log.info("Authenticating user with email: {} and role: {}", loginRequest.getEmail(), loginRequest.getRole());

        String role = loginRequest.getRole().toLowerCase();

        return switch (role) {
            case "parent" -> authenticateParent(loginRequest);
            case "tutor" -> authenticateTutor(loginRequest);
            case "admin" -> authenticateAdmin(loginRequest);
            default -> throw new InvalidRoleException("Invalid role: %s. Valid roles are: parent, tutor, admin".formatted(loginRequest.getRole()));
        };
    }

    /**
     * Authenticates a parent user.
     *
     * @param loginRequest the login request
     * @return LoginResponse with parent details
     */
    private LoginResponse authenticateParent(LoginRequest loginRequest) {
        Parent parent = parentRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("Parent not found with email: {}", loginRequest.getEmail());
                    return new UserNotFoundException("Parent not found with email: %s".formatted(loginRequest.getEmail()));
                });

        validatePassword(loginRequest.getCredentials(), parent.getPasswordEncrypted());

        log.info("Parent authenticated successfully: {}", parent.getEmail());
        return LoginResponse.builder()
                .userId(parent.getParentId())
                .firstName(parent.getFirstName())
                .lastName(parent.getLastName())
                .email(parent.getEmail())
                .role("PARENT")
                .success(true)
                .message("Parent login successful")
                .build();
    }

    /**
     * Authenticates a tutor user.
     *
     * @param loginRequest the login request
     * @return LoginResponse with tutor details
     */
    private LoginResponse authenticateTutor(LoginRequest loginRequest) {
        Tutor tutor = tutorRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("Tutor not found with email: {}", loginRequest.getEmail());
                    return new UserNotFoundException("Tutor not found with email: %s".formatted(loginRequest.getEmail()));
                });

        validatePassword(loginRequest.getCredentials(), tutor.getPasswordEncrypted());

        log.info("Tutor authenticated successfully: {}", tutor.getEmail());
        return LoginResponse.builder()
                .userId(tutor.getTutorId())
                .firstName(tutor.getFirstName())
                .lastName(tutor.getLastName())
                .email(tutor.getEmail())
                .role("TUTOR")
                .success(true)
                .message("Tutor login successful")
                .build();
    }

    /**
     * Authenticates an admin user.
     *
     * @param loginRequest the login request
     * @return LoginResponse with admin details
     */
    private LoginResponse authenticateAdmin(LoginRequest loginRequest) {
        Admin admin = adminRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("Admin not found with email: {}", loginRequest.getEmail());
                    return new UserNotFoundException("Admin not found with email: " + loginRequest.getEmail());
                });

        validatePassword(loginRequest.getCredentials(), admin.getPasswordEncrypted());

        log.info("Admin authenticated successfully: {}", admin.getEmail());
        return LoginResponse.builder()
                .userId(admin.getAdminId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .role("ADMIN")
                .success(true)
                .message("Admin login successful")
                .build();
    }

    /**
     * Validates the provided credentials against the stored encrypted password.
     *
     * @param encodedCredentials the base64 encoded credentials from the request
     * @param storedPassword the encrypted password from the database
     * @throws InvalidCredentialsException if the credentials are invalid
     */
    private void validatePassword(String encodedCredentials, String storedPassword) {
        String decodedCredentials = Base64DecoderUtil.decode(encodedCredentials);
        String password = Base64DecoderUtil.extractPassword(decodedCredentials);


        if (Strings.CS.equals(password, storedPassword)) {
            return;
        }
        log.warn("Invalid password provided");
        throw new InvalidCredentialsException("Invalid email or password");
    }
}

