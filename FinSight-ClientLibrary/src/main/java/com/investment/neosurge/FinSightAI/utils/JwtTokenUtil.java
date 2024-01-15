package com.investment.neosurge.FinSightAI.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

import static com.investment.neosurge.FinSightAI.utils.Constants.SECRET_KEY;

/**
 * Utility class to create JSON WeB Tokens for user authentication and security of API
 * */

public class JwtTokenUtil {

    //defining encryption algorithm to sign the JWT
//    @Value("${api.secret.key}")
//    private static String SECRET_KEY;
    private static Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    /**
     *  params: @userId and generates JWT which expires after 24hrs
     * */
    public static String generateToken(String userId) {
        String jwtToken = JWT.create()
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24*60*3600*1000L))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
        return jwtToken;
    }

    /**
     *  params: @String JWT parsed from request header
     *  return: @String userId
     *         validates JWT, if expired returns Exception; else decodes JWT & provides userId.
     * */
    public static String verifyJWTToken(String jwtToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(jwtToken);
            Claim claim = decodedJWT.getClaim("userId");
            return claim.toString();

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage());
        }
    }

}
