package com.coocpu.security_db_api_demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @auth Felix
 * @since 2025/4/1 0:34
 */
@Component
public class JWTUtil {

    private final String secret = "2llH3njfosLnk-opY2s90";

    public String createToken(String userJson) {
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        return JWT.create()
                .withHeader(header)
                .withClaim("user", userJson)
                .withClaim("username", "zhangsan")
                .withClaim("phone", "13800009999")
                .sign(Algorithm.HMAC256(secret));
    }

    public Boolean verifyToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret)).build()
                    .verify(token);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public String parseToken(String token) {
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(secret)).build()
                    .verify(token);
            return  verify.getClaim("username").asString();
//            String phone = verify.getClaim("phone").asString();
//            return true;
        } catch (Exception ignore) {
            return null;
        }
    }

}
