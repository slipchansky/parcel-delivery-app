package com.stas.parceldelivery.client.amql;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.stas.parceldelivery.client.amqp.ClientListener;
import com.stas.parceldelivery.client.amqp.ClientMessageTransmitter;
import com.stas.parceldelivery.client.domain.Delivery;
import com.stas.parceldelivery.client.service.DeliveryService;
import com.stas.parceldelivery.commons.amqp.messages.DeliveryStatusChanged;
import com.stas.parceldelivery.commons.amqp.utils.BindingUtil;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.QueueNames;
import com.stas.parceldelivery.commons.enums.DeliveryStatus;

@SpringBootTest
@ComponentScan("com.stas.parceldelivery")
public class AmqpExchangeITest {

	@Autowired 
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqpAdmin;

	
	@Autowired
	ClientListener office;
	
	@Autowired
	ClientMessageTransmitter transmiter;
	
	
	@Autowired
	AdminTestListener adminListener;
	
	@Autowired
	AdminTestListener courierListener;
	

	@MockBean
	DeliveryService deliveryService;
	
	@MockBean
	DummyService dummy;
	
	
	RabbitTemplate  courierTemplate;
	RabbitTemplate  adminTemplate;
	
	
	@BeforeEach
	public void init( ) {
		Queue listenTo = QueueUtil.withQueue(amqpAdmin, QueueNames.CLIENT_QUEUE, true);
		TopicExchange serviceExchange = ExchangeUtil.withTopicExchange(amqpAdmin, QueueNames.SERVICE_EXCHANGE);
		BindingUtil.addBinding(amqpAdmin, serviceExchange, listenTo, QueueNames.FROM_SERVICE);
		adminTemplate = TemplateUtil.createTemplate(connectionFactory, QueueNames.SERVICE_EXCHANGE, QueueNames.FROM_ADMIN);
		courierTemplate = TemplateUtil.createTemplate(connectionFactory, QueueNames.SERVICE_EXCHANGE, QueueNames.FROM_ADMIN);
	}
	
	
	@Test
	public void testClientSeesNotificationsFromAdmin() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		Mockito.doAnswer((i) -> {
		latch.countDown();
			return null;
		}).when(deliveryService).updateDeliveryStatus(Mockito.any(DeliveryStatusChanged.class));
		
		DeliveryStatusChanged change = new DeliveryStatusChanged("1", DeliveryStatus.ASSIGNED);
		adminTemplate.convertAndSend(change);
		latch.await(5, TimeUnit.SECONDS);  
		Mockito.verify(deliveryService, Mockito.times(1)).updateDeliveryStatus(Mockito.any(DeliveryStatusChanged.class));
	}
	
	@Test
	public void testClientSeesNotificationsFromCourier() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		Mockito.doAnswer((i) -> {
		latch.countDown();
			return null;
		}).when(deliveryService).updateDeliveryStatus(Mockito.any(DeliveryStatusChanged.class));
		
		DeliveryStatusChanged change = new DeliveryStatusChanged("1", DeliveryStatus.ASSIGNED);
//		deliveryChangedTemplate.addBeforePublishPostProcessors(new MessagePostProcessor() {
//
//			@Override
//			public Message postProcessMessage(Message message) throws AmqpException {
//				message.getMessageProperties().getHeaders().put("foo", 1);
//				return message;
//			}
//			
//		});
		
		courierTemplate.convertAndSend(change);
		latch.await(5, TimeUnit.SECONDS);  
		Mockito.verify(deliveryService, Mockito.times(1)).updateDeliveryStatus(Mockito.any(DeliveryStatusChanged.class));
	}
	
	
	@Test
	public void testBothAdminAndCourierSeeTheNotificationsFromClient() throws InterruptedException, IOException {
		final CountDownLatch latch = new CountDownLatch (2);
		
		Mockito.doAnswer((i) -> {
		latch.countDown();
			return null;
		}).when(dummy).doit();
		
		Delivery d = Delivery.builder().addressTo("two").addressFrom("one").client("c").status(DeliveryStatus.CREATED).build();
		transmiter.deliveryUpdated(d);
		
		latch.await();
		Mockito.verify(dummy, Mockito.times(2)).doit();
	}
	
	

}
