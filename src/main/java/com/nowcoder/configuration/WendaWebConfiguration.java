package com.nowcoder.configuration;

import com.nowcoder.interceptor.LoginRequredInterceptor;
import com.nowcoder.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 10412 on 2016/7/26.
 */
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter
{
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequredInterceptor loginRequredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(passportInterceptor);       //增加自己定义的密码拦截器
        registry.addInterceptor(loginRequredInterceptor).addPathPatterns("/user/*");   //增加注册登录拦截器（未登录跳转）
        super.addInterceptors(registry);
    }




}
