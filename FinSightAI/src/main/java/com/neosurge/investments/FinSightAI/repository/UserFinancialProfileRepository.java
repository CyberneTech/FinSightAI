package com.neosurge.investments.FinSightAI.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.investment.neosurge.FinSightAI.model.UserFinancialProfile;
import java.util.Optional;

@Repository
public interface UserFinancialProfileRepository extends MongoRepository<UserFinancialProfile, String> {

    Optional<UserFinancialProfile> findUserFinancialProfileByUserId(String userId);

}