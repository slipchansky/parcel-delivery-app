package com.stas.parceldelivery.commons.amqp.aspects;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.messaging.MessageHeaders;

import com.stas.parceldelivery.commons.constants.ParceldeliveryHeaders;
import com.stas.parceldelivery.commons.exchange.CallContext;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class AmqlListenerAspect {

	AtomicInteger amqpRequestNumber = new AtomicInteger(); 
	
    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public void listenerCall() {
    }
    
    
    
    @AfterThrowing(pointcut = "listenerCall()", throwing = "exception")
    public void onListenerError(JoinPoint joinPoint, Throwable exception){
    	log.debug("AMQP message process error");
    	log.trace("Stack trace", exception);
    } 
    
    @Around("listenerCall()")
    public Object onListenerCall(ProceedingJoinPoint point) throws Throwable {
    	Object[] request = point.getArgs();
    	updateContext(request);
    	log.debug("AMQP message process start");
    	
    	Object result = point.proceed(request);
    	
    	log.debug("AMQP message process finished");
    	return result;
    }
    
    private void updateContext(Object[] request) {
    	Optional<Object> oHeaders = Arrays.stream(request).filter(x -> x.getClass()==MessageHeaders.class).findFirst();
    	Thread.currentThread().setName("amqp."+amqpRequestNumber.incrementAndGet());
    	if (!oHeaders.isPresent()) return;
    	
    	MessageHeaders headers = (MessageHeaders)oHeaders.get();
    	String correlationId = (String)headers.get(ParceldeliveryHeaders.CORRELATION_ID);
    	String requestId = (String)headers.get(ParceldeliveryHeaders.REQUEST_ID);
    	CallContext context = CallContext.getInstance();
    	context.updateContext(correlationId, requestId);
    	
    }
    
	

}
