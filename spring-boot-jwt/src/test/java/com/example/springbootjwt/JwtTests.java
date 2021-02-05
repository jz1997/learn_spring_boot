package com.example.springbootjwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.example.springbootjwt.util.JwtUtils;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.Test;

public class JwtTests {

    private static final String key = "token!qwertyuiopasdfghjklzxcvbnmm";

    private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJleHAiOjE2MDkyNTU0MTAsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.PnqmazqzcfDh8S9hxuGT6w2_ECUTj_RRJWxa7zGM0ZY";

    /**
     * 生成 token
     */
    @Test
    public void generateToken() {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", "zhangsan");
        claims.put("userId", "zhangsan");
        String token = JwtUtils.sign(claims);
        System.out.println(token);

        Map<String, Claim> resultClaims = Objects.requireNonNull(JwtUtils.verifyAndGetJwt(token)).getClaims();
        System.out.println(resultClaims.get("username").asString());
    }
}
