package com.atguigu.interceptors;

import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @program: springboot-headline-part
 * @BelongsPackage com.atguigu.interceptors
 * @Author: 闭仕荣
 * @Date: 2024/1/8 21:57
 * @description: 登录包含拦截器，检查请求头是否包含有效的token
 */
/*
    描述：拦截器的具体配置
*   如果有效，那么就放行，后面进行正常的发布
*   如果无效，那么就进行拦截，返回504错误
*
* */
@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;

    //里面就三个方法，根据实际需要，来决定实现哪个方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中提取token
        String token = request.getHeader("token");
        //检查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        //有效就放行
        if(!expiration){
            return true;
        }
        //无效则报错
        Result result=Result.build(null, ResultCodeEnum.NOTLOGIN);
        //通过objectMapper实现在json数据跟java对象之间的转换，然后再通过response进行返回
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
        return false;
    }
}