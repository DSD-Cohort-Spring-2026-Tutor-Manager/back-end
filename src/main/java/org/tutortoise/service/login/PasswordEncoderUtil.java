package org.tutortoise.service.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * PasswordEncoderUtil for encoding and verifying passwords.
 * Uses BCrypt hashing algorithm for secure password storage.
 */
@Component
public class PasswordEncoderUtil {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Encodes the raw password using BCrypt.
     *
     * @param rawPassword the raw password to encode
     * @return the encoded password
     */
    public String encodePassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    /**
     * Verifies that the raw password matches the encoded password.
     *
     * @param rawPassword the raw password to verify
     * @param encodedPassword the encoded password from database
     * @return true if passwords match, false otherwise
     */
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}

