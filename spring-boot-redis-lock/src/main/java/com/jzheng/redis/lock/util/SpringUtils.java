package com.jzheng.redis.lock.util;

import javax.validation.constraints.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * SpringUtils: Spring 工具类
 *
 * @author jzheng
 * @since 2021/2/1 12:04 上午
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttrs = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        if (requestAttrs == null) {
            return null;
        }
        return requestAttrs.getRequest();
    }

    public static String getMessage(String code, Object... args) {
        LocaleResolver localeResolver = getBean(LocaleResolver.class);
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        Locale locale = localeResolver.resolveLocale(request);
        return applicationContext.getMessage(code, args, locale);
    }

}