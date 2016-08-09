package com.rnsolutions.core.listener;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class NotificationAspect {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationAspect.class);
	@Before("execution(* *.com.rnsolutions.stumblr.dao.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		LOGGER.info("Method Invoked");
		
	}


}
