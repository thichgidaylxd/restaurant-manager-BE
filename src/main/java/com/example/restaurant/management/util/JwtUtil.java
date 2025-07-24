package com.example.restaurant.management.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtUtil {

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String signerKey;

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(signerKey.getBytes())
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();

        return UUID.fromString(claims.get("userAccountId", String.class));
    }
}
