package com.neosurge.investments.FinSightAI.controller;

import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import com.neosurge.investments.FinSightAI.service.UserFinancialPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserFinancialPreferenceController {

    @Autowired
    private UserFinancialPreferenceService userFinancialPreferenceService;

    @CrossOrigin(origins = "*")
    @PostMapping("/newUserPreference")
    public ResponseEntity<Map<String,String>> createUserFinancialPreference(@RequestBody UserFinancialProfile userFinancialProfile, @RequestHeader (name="Authorization") String jwtToken) {
        jwtToken = jwtToken.split(" ")[1];
        Map<String,String> response = new HashMap<>();
        try {
            response.put("success",userFinancialPreferenceService.createNewPreference(jwtToken, userFinancialProfile));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e) {
            response.put("error",e.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/editUserPreference")
    public ResponseEntity<Map<String,String>> editUserFinancialPreference(@RequestBody UserFinancialProfile userFinancialProfile, @RequestHeader (name="Authorization") String jwtToken) {
        jwtToken = jwtToken.split(" ")[1];
        Map<String,String> response = new HashMap<>();
        try {
            response.put("success",userFinancialPreferenceService.editFinancialPreferencesForUser(jwtToken, userFinancialProfile));
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e) {
            response.put("error", e.toString());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getUserPreference")
    public ResponseEntity<?> getUserFinancialPreference(@RequestHeader (name="Authorization") String jwtToken) {
        jwtToken = jwtToken.split(" ")[1];
        try {
            UserFinancialProfile response = userFinancialPreferenceService.getUserFinancialProfile(jwtToken);
            return  new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("error", e.toString());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
