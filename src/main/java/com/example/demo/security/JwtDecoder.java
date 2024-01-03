package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties properties;

    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256(properties.getSecretKey()))
                .build()
                .verify(token);
    }
    public boolean isExpire(String token){
        Claim claims = decode(token).getClaim("exp");
        var now = Instant.now().toEpochMilli();
        System.out.println("now: "+now);
        var expire =claims.asInstant().toEpochMilli();
        System.out.println("expire: "+expire);
        if(expire-now>0){
            return true;
        }
        return false;
    }
}
