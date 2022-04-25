package com.stas.parceldelivery.admin.amql;


import static com.stas.parceldelivery.commons.constants.Queues.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.stas.parceldelivery.admin.amqp.AdminListener;
import com.stas.parceldelivery.admin.amqp.AdminMessageTransmitter;
import com.stas.parceldelivery.admin.domain.Courier;
import com.stas.parceldelivery.admin.domain.DeliveryTask;
import com.stas.parceldelivery.admin.service.AdminService;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderCreated;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.ExchangeName;
import com.stas.parceldelivery.commons.constants.Queues;
import com.stas.parceldelivery.commons.constants.Routes;


@SpringBootTest(classes = {AdminAmqpExchangeITest.AdminAmqpTestApp.class, AdminListener.class, TestListener.class, AdminMessageTransmitter.class})
public class AdminAmqpExchangeITest  {
	
	@SpringBootApplication(exclude = {
		    DataSourceAutoConfiguration.class, 
		    DataSourceTransactionManagerAutoConfiguration.class, 
		    HibernateJpaAutoConfiguration.class,
		    WebClientAutoConfiguration.class
		})
	public static class AdminAmqpTestApp {
	}

	
	

	@Autowired 
	ConnectionFactory connectionFactory;
	
	@Autowired
	AmqpAdmin amqp;

	
	@Autowired
	AdminListener office;
	
	@Autowired
	AdminMessageTransmitter transmiter;
	
	
	@Autowired
	TestListener adminListener;
	
	@Autowired
	TestListener courierListener;
	

	@MockBean
	AdminService adminService;
	
	@MockBean
	DummyService dummy;

	private RabbitTemplate orderAssigned;

	private RabbitTemplate locationChanged;

	private RabbitTemplate statusChanged;
	
	private RabbitTemplate orderCreated;

	private RabbitTemplate orderUpdated;

	private RabbitTemplate orderCancelled;
	

	
	
	@PostConstruct
	public void init()  {
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderCreated,
				AdminOrderCreated
				);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderUpdated,
				AdminOrderUpdated, CourierOrderUpdated
				);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, 
				ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderCancelled,
				AdminOrderCancelled,  CourierOrderCancelled);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.COURIER_EXCHANGE, 
				Routes.OrderStatusChanged,
				/* tp */
				AdminStatusChanged, ClientStatusChanged);

		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.COURIER_EXCHANGE, Routes.LocationChanged,
				/* tp */
				AdminLocationChanged, ClientLocationChanged);
		
		
		orderAssigned = TemplateUtil.createTemplate(connectionFactory, ExchangeName.ADMIN_EXCHANGE, Routes.OrderAssignment);
		
		locationChanged = TemplateUtil.createTemplate(connectionFactory, ExchangeName.COURIER_EXCHANGE, Routes.LocationChanged);
		statusChanged = TemplateUtil.createTemplate(connectionFactory, ExchangeName.COURIER_EXCHANGE, Routes.OrderStatusChanged);
		
		orderCreated =  TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE, Routes.OrderCreated);
		orderUpdated =  TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE, Routes.OrderUpdated);
		orderCancelled = TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE, Routes.OrderCancelled);
	}
	
	@BeforeEach
	public void cleanUp() {
		QueueUtil.purgeQueues(amqp, Queues.all());
	}
	
	private void mockOnDummyCall(final CountDownLatch latch) {
		Mockito.doAnswer((i) -> {
		latch.countDown();
			return null;
		}).when(dummy).doit();
	}

	private AdminService verifyServiceCalled(int n) {
		return verify(adminService, times(n));
	}

	private AdminService mockOnServiceCall(final CountDownLatch latch) {
		return Mockito.doAnswer((i) -> {
		latch.countDown();
			return null;
		}).when(adminService);
	}
	private void verifyDummyCalled(int n) {
		verify(dummy, times(n)).doit();
	}
	
	
	@Test
	public void createOrderListenTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		
		mockOnServiceCall(latch).createOrder(any(OrderCreated.class));
		mockOnDummyCall(latch);
		
		orderCreated.convertAndSend(new OrderCreated());
		
		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).createOrder(any(OrderCreated.class));
	}

	@Test
	public void updateOrderListenTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		
		mockOnServiceCall(latch).updateOrder(any(OrderUpdated.class));;
		mockOnDummyCall(latch);
		orderUpdated.convertAndSend(new OrderUpdated());
		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).updateOrder(any(OrderUpdated.class));;
	}
	
	@Test
	public void changeStatusListenTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		mockOnServiceCall(latch).changeStatus(any(OrderStatusChanged.class));;
		statusChanged.convertAndSend(new OrderStatusChanged());
		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).changeStatus(any(OrderStatusChanged.class));;
	}
	
	@Test
	public void updateLocationListenTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		mockOnServiceCall(latch).updateLocation(any(LocationChanged.class));
		locationChanged.convertAndSend(new LocationChanged());
		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).updateLocation(any(LocationChanged.class));
	}
	
	

	@Test
	public void cancelOrderListenTest() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (1);
		mockOnServiceCall(latch).cancelOrder(any(OrderCancelled.class));
		mockOnDummyCall(latch);
		
		orderCancelled.convertAndSend(new OrderCancelled());
		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).cancelOrder(any(OrderCancelled.class));
		
	}


	@Test
	public void testBothClientAndCourierSee_OrderAssigned () throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch (2);
		mockOnDummyCall(latch);
		DeliveryTask task = new DeliveryTask().builder()
				.id("t1")
				.assignee(Courier.builder().id("a1").build())
				.build();
		
		transmiter.orderAssigned(task);
		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(2);
	}
	
	

}
