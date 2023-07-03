package com.issue.tracker.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class LoggingAspect {

    private final AuthenticationService authenticationService;

    @Before("execution(* com.issue.tracker.*.*Controller.*(..)) ||" +
            "execution(* com.issue.tracker.ticket.comment.*Controller.*(..)) ")
    public void logBeforeAllControllerMethods(JoinPoint joinPoint) {

        String userName = authenticationService.getAuthentication().getName();
        String getMethodPath = joinPoint.getSignature().toString();

        String inMethodStart = "user: " + userName + "; " +
                "at: " + OffsetDateTime.now() + "; " +
                "executed method: " + getMethodPath
                + " **START**";
        log.info(inMethodStart);
    }

    @After("execution(* com.issue.tracker.*.*Controller.*(..)) ||" +
            "execution(* com.issue.tracker.ticket.comment.*Controller.*(..)) ")
    public void logAfterAllControllerMethods(JoinPoint joinPoint) {

        String userName = authenticationService.getAuthentication().getName();
        String getMethodPath = joinPoint.getSignature().toString();

        String inMethodStart = "user: " + userName + "; " +
                "at: " + OffsetDateTime.now() + "; " +
                "executed method: " + getMethodPath
                + " **END**";
        log.info(inMethodStart);
    }
}
