package com.investment.neosurge.FinSightLLMService.controller;

import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import com.investment.neosurge.FinSightLLMService.service.LLMInsightService;
import com.investment.neosurge.FinSightLLMService.service.UserFinancialPreferenceService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * endpoint to get investment insights for user
 * */
@RestController
@RequestMapping("/api/v1")
public class FinancialInsightController {

    @Autowired
    private LLMInsightService llmInsightService;

    @Autowired
    private UserFinancialPreferenceService userFinancialPreferenceService;

    //Rate Limiter for the API endpoint
    private final Bucket bucket;

    public FinancialInsightController() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(5)
                .refillGreedy(5, Duration.ofMinutes(1))
                .build();
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getInsights")
    public ResponseEntity<?> getUserFinancialPreference(@RequestHeader(name="Authorization") String jwtToken) {
        jwtToken = jwtToken.split(" ")[1];
        try {
            //checking for bucket capacity and calling service to fetch insight data
            if(bucket.tryConsume(1)) {
                UserFinancialProfile userFinancialProfile = userFinancialPreferenceService.getUserFinancialProfile(jwtToken);
                String responseInsight = llmInsightService.llmGenerateInsight(userFinancialProfile);
                return new ResponseEntity<>(responseInsight, HttpStatus.OK);
            }
            else {
                throw new RuntimeException("Too many request");
            }
        }
        catch (Exception e) {
            Map<String,String> response = new HashMap<>();
            response.put("error", e.toString());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
