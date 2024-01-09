package com.atguigu.config;

import com.atguigu.interceptors.LoginProtectedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: springboot-headline-part
 * @BelongsPackage com.atguigu.config
 * @Author: 闭仕荣
 * @Date: 2024/1/8 22:12
 * @description:
 */

/*
* 配置类，主要是用于配置了拦截器(拦截器用来实现token的验证)
*
*
*
* */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginProtectedInterceptor loginProtectedInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginProtectedInterceptor).addPathPatterns("/headline/**");
    }
}