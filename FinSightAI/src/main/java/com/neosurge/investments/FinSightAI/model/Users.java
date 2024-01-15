package com.neosurge.investments.FinSightAI.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity for MongoDB collection managing user Auth data
 * */

@Document
@Getter
@Setter
public class Users {

    @Id
    private String userId;

    private String name;

    private String email;

    private String password;

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
