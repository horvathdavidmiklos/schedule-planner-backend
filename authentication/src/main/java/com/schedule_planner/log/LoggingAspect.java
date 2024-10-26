package com.schedule_planner.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.schedule_planner.log.LogMethod)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object[] args = joinPoint.getArgs();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        StringBuilder logMessage = new StringBuilder("Entering method: ");
        logMessage.append(method.getName());
        logMessage.append(" with parameters: ");

        // Másoljuk az eredeti argumentumokat, hogy ne módosítsuk azokat
        Object[] logArgs = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            logArgs[i] = args[i];
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof SensitiveData) {
                    logArgs[i] = "[SensitiveData]";
                }
            }
            logMessage.append(logArgs[i]).append(", ");
        }

        logger.info(logMessage.toString());

        try {
            Object result = joinPoint.proceed(args);
            logger.info("Exiting method: " + method.getName()+" with return value: " + result);
            return result;
        } catch (Throwable throwable) {
            logger.error("Exception in method: "+method.getName() + " with message: " + throwable.getMessage(), throwable);
            throw throwable;
        }
    }
}
