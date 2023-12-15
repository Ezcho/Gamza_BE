package org.devcourse.shop_gamza.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class Jwt {
    private final String issuer;
    private final String secretKey;
    private final int expirySeconds;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;


    public Jwt(String issuer, String secretKey, int expirySeconds) {
        this.issuer = issuer;
        this.secretKey = secretKey;
        this.expirySeconds = expirySeconds;
        algorithm = Algorithm.HMAC512(secretKey);
        jwtVerifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    public String sign(Claims claims) {
        Date now = new Date();
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(now);
        if (expirySeconds > 0) {
            builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
        }
        builder.withClaim("username", claims.username);
        builder.withArrayClaim("roles", claims.roles);
        return builder.sign(algorithm);
    }

    public Claims verify(String token) throws JWTVerificationException {
        return new Claims(jwtVerifier.verify(token));
    }

    static public class Claims {
        String username;
        String[] roles;
        Date issuedAt;
        Date expiresAt;

        private Claims() {/*no-op*/}

        Claims(DecodedJWT decodedJWT) {
            Claim username = decodedJWT.getClaim("username");
            if (!username.isNull()) {
                this.username = username.asString();
            }

            Claim roles = decodedJWT.getClaim("roles");
            if (!roles.isNull()) {
                this.roles = roles.asArray(String.class);
            }
            this.issuedAt = decodedJWT.getIssuedAt();
            this.expiresAt = decodedJWT.getExpiresAt();
        }

        public static Claims from(String username, String[] roles) {
            Claims claims = new Claims();
            claims.username = username;
            claims.roles = roles;
            return claims;
        }

        public Map<String, Object> asMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("roles", roles);
            map.put("issuedAt", issuedAt());
            map.put("expiresAt", expiresAt());
            return map;
        }

        long issuedAt() {
            return issuedAt != null ? issuedAt.getTime() : -1;
        }

        long expiresAt() {
            return expiresAt != null ? expiresAt.getTime() : -1;
        }

        void eraseIssuedAt() {
            issuedAt = null;
        }

        void eraseExpiresAt() {
            expiresAt = null;
        }
    }

}
