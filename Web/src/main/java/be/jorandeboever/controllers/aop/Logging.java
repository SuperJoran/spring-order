package be.jorandeboever.controllers.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    private static final Logger LOG = LogManager.getLogger();

    @Pointcut("execution(* be.jorandeboever.controllers.*.*(..))")
    private void selectAll(){}


    @Before("selectAll()")
    public void afterReturningAdvice(JoinPoint joinPoint){
        LOG.info(() -> String.format("Calling method: %s", joinPoint.getSignature().getName()));
    }
}
