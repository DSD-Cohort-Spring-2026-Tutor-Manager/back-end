package org.tutortoise.service.login;

import java.util.Base64;

/**
 * Base64DecoderUtil for decoding base64 encoded credentials.
 */
public class Base64DecoderUtil {

    /**
     * Decodes base64 encoded credentials.
     *
     * @param encodedCredentials the base64 encoded credentials
     * @return the decoded credentials
     */
    public static String decode(String encodedCredentials) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }

    /**
     * Extracts password from decoded credentials in format "username:password".
     *
     * @param decodedCredentials the decoded credentials
     * @return the password
     */
    public static String extractPassword(String decodedCredentials) {
        String[] parts = decodedCredentials.split(":", 2);
        if (parts.length != 2) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        return parts[1];
    }

    /**
     * Extracts username from decoded credentials in format "username:password".
     *
     * @param decodedCredentials the decoded credentials
     * @return the username
     */
    public static String extractUsername(String decodedCredentials) {
        String[] parts = decodedCredentials.split(":", 2);
        if (parts.length != 2) {
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        return parts[0];
    }
}

