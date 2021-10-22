package com.entonomachia.aspects;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingInterfaceCalls {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springControllerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }
    
    
    
    
//    @Before("springControllerPointcut() && args(..,request)")
//    public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {
// 
//        log.info("Entering in Method :  " + joinPoint.getSignature().getName());
//        log.info("Class Name :  " + joinPoint.getSignature().getDeclaringTypeName());
//        log.info("Arguments :  " + Arrays.toString(joinPoint.getArgs()));
//        log.info("Target class : " + joinPoint.getTarget().getClass().getName());
// 
//        if (null != request) {
//            log.info("Start Header Section of request ");
//            log.info("Method Type : " + request.getMethod());
//            Enumeration<String> headerNames = request.getHeaderNames();
//            while (headerNames.hasMoreElements()) {
//                String headerName = headerNames.nextElement();
//                String headerValue = request.getHeader(headerName);
//                log.info("Header Name: " + headerName + " Header Value : " + headerValue);
//            }
//            log.info("Request Path info :" + request.getServletPath());
//            log.info("End Header Section of request ");
//        }
//    }
}
