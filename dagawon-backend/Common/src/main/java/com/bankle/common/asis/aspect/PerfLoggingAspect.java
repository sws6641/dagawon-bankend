package com.bankle.common.asis.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class PerfLoggingAspect {
    @Around("@annotation(PerfLogging)")
    public Object test(ProceedingJoinPoint joinPoint) throws Throwable{
        StopWatch watch = new StopWatch(joinPoint.getSignature().toLongString());

        watch.start();
        Object retVal = joinPoint.proceed();
        watch.stop();

        log.info(watch.prettyPrint());

        return retVal;
    }
}
