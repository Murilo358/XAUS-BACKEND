package com.XAUS.SecurityConfig;

import com.XAUS.Models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{

            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("XAUS-SYSTEM")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDT())
                    .sign(algorithm);


            return token;
        }catch(JWTCreationException exception){
            //Helps to debug  (getting the jwt exception)
            throw new RuntimeException("ERROR GENERATION JWT TOKEN", exception);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT
                    .require(algorithm)
                    .withIssuer("XAUS-SYSTEM")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch(JWTVerificationException exception) {
            //Error if jwt token is invalid
            return "";
        }

    }

    private Instant generateExpirationDT(){

        return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
    }

}
