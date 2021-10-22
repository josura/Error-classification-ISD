package com.entonomachia.aspects;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspect {
    private static final Logger log = LogManager.getLogger(LoggingAspect.class);
    
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    @Around("springControllerPointcut()")		
    public Object logAround(ProceedingJoinPoint joinpoint) throws Throwable {
    	log.debug("Request for " + joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName() + 
    			" with arguments=" + Arrays.toString( joinpoint.getArgs()) );
    	Object returnvalue = joinpoint.proceed();
    	return returnvalue;
    }
}