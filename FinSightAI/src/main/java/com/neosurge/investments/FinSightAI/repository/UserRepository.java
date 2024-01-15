package com.neosurge.investments.FinSightAI.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neosurge.investments.FinSightAI.model.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {

    Optional<Users> findUsersByEmail(String email);
}
