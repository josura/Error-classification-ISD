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
    
    @Pointcut("within(com.entonomachia..*)")
    public void packagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "packagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause = {}", joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL");
    }
    
    @Around("springControllerPointcut()")		
    public Object logAround(ProceedingJoinPoint joinpoint) throws Throwable {
    	log.info("Request for " + joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName() + 
    			" with arguments=" + Arrays.toString( joinpoint.getArgs()) );
    	Object returnvalue = joinpoint.proceed();
    	return returnvalue;
    }
}