package com.example.springbootjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import org.junit.jupiter.api.Test;

public class JwtUtilsTests {

    private static final String key = "token!qwertyuiopasdfghjklzxcvbnmm";

    private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiYWRtaW4iLCJleHAiOjE2MDkyNTU0MTAsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJ6aGFuZ3NhbiJ9.PnqmazqzcfDh8S9hxuGT6w2_ECUTj_RRJWxa7zGM0ZY";

    /**
     * 生成 token
     */
    @Test
    public void generateToken() throws UnsupportedEncodingException {
        // 取当前时间 20 秒后
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 90);

        String token = JWT.create()
            // payload
            .withClaim("username", "zhangsan")
            .withClaim("userId", 1)
            .withClaim("role", "admin")
            // 过期时间
            .withExpiresAt(calendar.getTime())
            // 签名
            .sign(Algorithm.HMAC256(key));

        System.out.println(token);
    }

    /**
     * 验签和解析数据
     */
    @Test
    public void parseToken() throws UnsupportedEncodingException {
        // 创建验证对象
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key)).build();
        // 解析 token
        DecodedJWT decodedJWT = verifier.verify(token);
        // 获取 payload 属性
        String username = decodedJWT.getClaim("username").asString();
        Integer userId = decodedJWT.getClaim("userId").asInt();
        String role = decodedJWT.getClaim("role").asString();

        System.out.println("userId   = " + userId);
        System.out.println("username = " + username);
        System.out.println("role     = " + role);
    }
}
