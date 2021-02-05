package com.example.spring.boot.security.jwt.demo.comm;

/**
 * Const:
 *
 * @author jzheng
 * @since 2020/12/31 11:17 下午
 */
public class Const {

    /**
     * token 过期时间 5 天
     */
    public static final long EXPIRATION_TIME = 432_000_000;

    /**
     * Jwt Secret
     */
    public static final String SECRET = "CodeSheepSecret";

    /**
     * header 中携带 token 的前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    /**
     * token 在 Header 中的 Key
     */
    public static final String HEADER_STRING = "Authorization";
}
