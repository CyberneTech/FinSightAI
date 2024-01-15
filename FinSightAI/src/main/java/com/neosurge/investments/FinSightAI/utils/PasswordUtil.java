package com.neosurge.investments.FinSightAI.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static com.neosurge.investments.FinSightAI.utils.Constants.MESSAGE_DIGEST_ALGORITHM;

/**
 * Utility Class for Hashing User Password
 * Validating password during authentication
 * */
public class PasswordUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    public static boolean validatePassword(String providedPassword, String storedPassword) {
        String hashedProvidedPassword = hashPassword(providedPassword);
        return hashedProvidedPassword.equals(storedPassword);
    }


}
