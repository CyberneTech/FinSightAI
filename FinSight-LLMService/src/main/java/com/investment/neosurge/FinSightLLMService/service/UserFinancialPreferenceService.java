package com.investment.neosurge.FinSightLLMService.service;

import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import com.investment.neosurge.FinSightLLMService.repository.UserFinancialProfileRepository;
import com.investment.neosurge.FinSightAI.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFinancialPreferenceService {

    @Autowired
    private UserFinancialProfileRepository userFinancialProfileRepository;

    public UserFinancialProfile getUserFinancialProfile(String jwtToken) {
        String userId = JwtTokenUtil.verifyJWTToken(jwtToken);
        Optional<UserFinancialProfile> financialProfile = userFinancialProfileRepository.findUserFinancialProfileByUserId(userId);
        if(financialProfile.isPresent()) {
            return financialProfile.get();
        }
        else {
            throw new RuntimeException("Financial Profile Not Found");
        }
    }
}
