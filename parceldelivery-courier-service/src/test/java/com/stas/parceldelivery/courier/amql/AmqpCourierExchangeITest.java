package com.stas.parceldelivery.courier.amql;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.stas.parceldelivery.commons.amqp.messages.CourierAssignedTask;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderCancelled;
import com.stas.parceldelivery.commons.amqp.messages.OrderStatusChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderUpdated;
import com.stas.parceldelivery.commons.amqp.utils.ExchangeUtil;
import com.stas.parceldelivery.commons.amqp.utils.QueueUtil;
import com.stas.parceldelivery.commons.amqp.utils.TemplateUtil;
import com.stas.parceldelivery.commons.constants.ExchangeName;
import com.stas.parceldelivery.commons.constants.Queues;
import com.stas.parceldelivery.commons.constants.Routes;
import com.stas.parceldelivery.courier.amqp.CourierListener;
import com.stas.parceldelivery.courier.amqp.CourierTransmitter;
import com.stas.parceldelivery.courier.service.CourierService;

@Testcontainers
@SpringBootTest
@ComponentScan("com.stas.parceldelivery.client.amqp")
public class AmqpCourierExchangeITest {
	
	

	@Container
	private static final RabbitMQContainer RABBIT_CONTAINER = new RabbitMQContainer("rabbitmq:management")
	.withExposedPorts(5672, 15672);

	@Autowired
	ConnectionFactory connectionFactory;

	@Autowired
	AmqpAdmin amqp;

	@Autowired
	CourierListener listener;

	@Autowired
	CourierTransmitter transmiter;

	@Autowired
	TestListener adminListener;

	@Autowired
	TestListener testListener;

	@MockBean
	CourierService courierService;

	@MockBean
	DummyService dummy;

	private RabbitTemplate orderAssigned;

	private RabbitTemplate orderUpdated;

	private RabbitTemplate orderCancelled;

	
	private final static CourierAssignedTask mAssignment = new CourierAssignedTask("o");
	private final static OrderUpdated mOrderUpdated = new OrderUpdated("o");
	private final static OrderCancelled mOrderCancelled = new OrderCancelled("o");
	private final static OrderStatusChanged mOrderStatusChanged = new OrderStatusChanged("o");
	private final static LocationChanged mLocationChanged = new LocationChanged("o");
	


	@PostConstruct
	public void init() {

		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.ADMIN_EXCHANGE
				, Routes.CourierTaskAssigned,
				/* tp */
				CourierTaskAssigned);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.CLIENT_EXCHANGE
				, Routes.OrderUpdated,
				/* tp */
				CourierOrderUpdated);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.CLIENT_EXCHANGE
				, Routes.OrderCancelled,
				/* tp */
				CourierOrderCancelled);
		
		
		orderAssigned = TemplateUtil.createTemplate(connectionFactory, ExchangeName.ADMIN_EXCHANGE,
				Routes.CourierTaskAssigned);
		
		orderUpdated = TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderUpdated);
		
		orderCancelled = TemplateUtil.createTemplate(connectionFactory, ExchangeName.CLIENT_EXCHANGE,
				Routes.OrderCancelled);
		
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

	private CourierService verifyServiceCalled(int n) {
		return verify(courierService, times(n));
	}

	private CourierService mockOnServiceCall(final CountDownLatch latch) {
		return Mockito.doAnswer((i) -> {
			latch.countDown();
			return null;
		}).when(courierService);
	}

	private void verifyDummyCalled(int n) {
		verify(dummy, times(n)).doit();
	}

	@Test
	public void testCourierCatchesTaskAssignment() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		mockOnServiceCall(latch).createCourierTask(any(CourierAssignedTask.class));
		mockOnDummyCall(latch);

		orderAssigned.convertAndSend(mAssignment);

		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).createCourierTask(any(CourierAssignedTask.class));
	}
	
	@Test
	public void testCourierCatchesUpdateOrderFromClient() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		mockOnServiceCall(latch).updateCourierTask(any(OrderUpdated.class));

		orderUpdated.convertAndSend(mOrderUpdated);

		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).updateCourierTask(any(OrderUpdated.class));
	}
	
	@Test
	public void testCourierCatchesCancelOrderFromClient() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		mockOnServiceCall(latch).cancelCourierTask(any(OrderCancelled.class));

		orderCancelled.convertAndSend(mOrderCancelled);

		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).cancelCourierTask(any(OrderCancelled.class));
	}

	@Test
	public void testClient_And_Admin_see_StatusChanged() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);
		mockOnDummyCall(latch);
		transmiter.statusChanged(mOrderStatusChanged);
		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(2);
	}
	
	@Test
	public void testClient_And_Admin_see_LocationChanged() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);
		mockOnDummyCall(latch);
		transmiter.locationChanged(mLocationChanged);
		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(2);
	}
	

}
