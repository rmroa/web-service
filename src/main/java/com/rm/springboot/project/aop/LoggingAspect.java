package com.rm.springboot.project.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger log = LogManager.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.rm.springboot.project.controller.*.*(..))")
    public void aroundPointcut() {
    }

    @Around("aroundPointcut()")
    public Object logAroundMethods(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        String className = point.getTarget().getClass().toString();
        Object[] array = point.getArgs();
        log.info("method invoke " + className + ": " + methodName + "() with " + "arguments: " + Arrays.toString(array));
        Instant start = Instant.now();
        Object object = point.proceed();
        Instant finish = Instant.now();
        long time = Duration.between(start, finish).toMillis();
        log.info(className + ": " + methodName + "() with " + "Response: " + object);
        log.info("Time Taken = " + new SimpleDateFormat("mm:ss:SSS").format(new Date(time)) + "\n");
        return object;
    }
}
