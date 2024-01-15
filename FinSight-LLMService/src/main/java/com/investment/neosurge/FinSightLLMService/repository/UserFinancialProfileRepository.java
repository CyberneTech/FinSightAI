package com.investment.neosurge.FinSightLLMService.repository;

import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFinancialProfileRepository extends MongoRepository<UserFinancialProfile, String> {
    Optional<UserFinancialProfile> findUserFinancialProfileByUserId(String userId);
}
