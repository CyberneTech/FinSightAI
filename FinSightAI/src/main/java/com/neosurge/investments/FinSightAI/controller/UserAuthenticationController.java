package com.neosurge.investments.FinSightAI.controller;

import com.neosurge.investments.FinSightAI.model.Users;
import com.neosurge.investments.FinSightAI.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class UserAuthenticationController {
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @CrossOrigin(origins = "*")
    @PostMapping("/signin")
    public ResponseEntity<Map<String,String>> signIn(@RequestBody Users user) {
        Map<String,String> response = new HashMap<>();
        try {
            response.put("success",userAuthenticationService.signInNewUser(user));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e) {
            response.put("error",e.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String,String> body) {
        Map<String,String> response = new HashMap<>();
        try {
            String email = body.get("email");
            String password = body.get("password");
            response = userAuthenticationService.loginUser(email,password);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e) {
            response.put("error", e.toString());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
