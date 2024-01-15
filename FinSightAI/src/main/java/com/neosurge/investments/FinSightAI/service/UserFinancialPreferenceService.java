package com.neosurge.investments.FinSightAI.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import com.neosurge.investments.FinSightAI.repository.UserFinancialProfileRepository;
import com.investment.neosurge.FinSightAI.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service to maintain financial preference data for user
 * CRUD operations for UserFinancialProfile
 * */

@Service
public class UserFinancialPreferenceService {

    @Autowired
    private UserFinancialProfileRepository userFinancialProfileRepository;

    // creating a new entry to store user financial preferences.
    public String createNewPreference(String jwtToken, UserFinancialProfile userFinancialProfile) {
        try {
            String userId = JwtTokenUtil.verifyJWTToken(jwtToken);
            userFinancialProfile.setUserId(userId);
            if(userFinancialProfileRepository.findUserFinancialProfileByUserId(userId).isEmpty()) {
                userFinancialProfileRepository.insert(userFinancialProfile);
                return "preferences set successfully";
            }
            else throw new RuntimeException("Invalid Operation");
        }
        catch (JWTVerificationException jwtVerificationException) {
            throw new RuntimeException("Invalid JWT Token");
        }
        catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    // Update financial preference for a given user
    public String editFinancialPreferencesForUser(String jwtToken, UserFinancialProfile updatedUserFinancialProfile) {
        try {
            String userId = JwtTokenUtil.verifyJWTToken(jwtToken);
            UserFinancialProfile existingFinancialProfile = getUserFinancialProfile(jwtToken);
            if (!Objects.isNull(existingFinancialProfile)) {
                BeanUtils.copyProperties(updatedUserFinancialProfile, existingFinancialProfile, "userId", "id");
                userFinancialProfileRepository.save(existingFinancialProfile);
                return "preferences updated successfully";
            }
            else {
                throw new RuntimeException("Financial Profile Not Found");
            }
        } catch (JWTVerificationException jwtVerificationException) {
            throw new RuntimeException("Invalid JWT Token");
        } catch (Exception e) {
            throw new RuntimeException("Error updating Financial Profile");
        }
    }

    // reading preference data for a given user
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
