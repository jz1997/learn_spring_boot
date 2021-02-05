package com.example.springbootjwt.intercepters;

import com.alibaba.fastjson.JSON;
import com.example.springbootjwt.util.JwtUtils;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * TokenInterceptor:
 *
 * @author jzheng
 * @since 2020/12/30 6:06 下午
 */
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String token = request.getHeader("token");

        Map<String, Object> responseMap = new HashMap<>(3);
        if (JwtUtils.verifyAndGetJwt(token) != null) {
            return true;
        }
        responseMap.put("code", -1);
        responseMap.put("msg", "token 失效");
        responseMap.put("data", null);
        String responseMapJson = JSON.toJSONString(responseMap);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().println(responseMapJson);
        response.flushBuffer();
        return false;
    }
}
