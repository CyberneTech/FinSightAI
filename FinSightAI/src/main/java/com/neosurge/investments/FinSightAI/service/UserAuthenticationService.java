package com.neosurge.investments.FinSightAI.service;

import com.neosurge.investments.FinSightAI.model.Users;
import com.neosurge.investments.FinSightAI.repository.UserRepository;
import com.neosurge.investments.FinSightAI.utils.PasswordUtil;
import com.investment.neosurge.FinSightAI.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

 // Service to Authenticate User; Sign-in for new user and Login for existing user
@Service
public class UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    /**
     * param: @User, contains user email, name and password
     * returns: @String of a successful signin confirmation
     */
    public String signInNewUser(Users user) {
        // to check if user already exists
        if (userRepository.findUsersByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        try {
            user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
            userRepository.insert(user);
             return "Successfully Signed-up ";
        }
        catch (Exception e) {
            throw new RuntimeException("Error Signing-up");
        }
    }

    /**
     * params: @String email, @String password
     * returns: @String email, @String password, @String JWT generated for a login session[24hrs]
     */
    public Map<String, String> loginUser(String email, String password) {
        Users user = userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!PasswordUtil.validatePassword(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = JwtTokenUtil.generateToken(user.getUserId());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("name", user.getName());
        response.put("email", user.getEmail());

        return response;

    }
}
