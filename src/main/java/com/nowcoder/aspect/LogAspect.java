package com.nowcoder.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by 10412 on 2016/7/19.
 */
@Aspect
@Component
public class LogAspect
{
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.nowcoder.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint)
    {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs())
        {
            sb.append("arg: " + arg.toString() + "|");
        }
        logger.info("Before Method: "+ sb.toString());
    }

    @After("execution(* com.nowcoder.controller.IndexController.*(..))")
    public void afterMethod()
    {
        logger.info("After Method: "+new Date());
    }
}
