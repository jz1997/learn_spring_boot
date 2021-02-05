package com.example.springbootjwt.util;

import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.interfaces.Claim;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JwtUtils: Jwt 工具类
 *
 * @author jzheng
 * @since 2020/12/29 11:44 下午
 */
public class JwtUtils {

    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "privateKey";

    /**
     * 生成签名
     *
     * @param claims claims 参数
     * @return 发返回 token
     */
    public static String sign(Map<String, String> claims) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("Type", "Jwt");
            header.put("alg", "HS256");

            Builder builder = JWT.create();

            // 设置 claims
            claims.forEach(builder::withClaim);

            // 返回token字符串
            return builder.withHeader(header)
                .withExpiresAt(date)
                .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param token token
     * @return xx
     */
    public static DecodedJWT verifyAndGetJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }
}