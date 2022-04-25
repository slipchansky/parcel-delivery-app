package com.stas.parceldelivery.client.amql;

import static com.stas.parceldelivery.commons.constants.Queues.AdminLocationChanged;
import static com.stas.parceldelivery.commons.constants.Queues.AdminStatusChanged;
import static com.stas.parceldelivery.commons.constants.Queues.ClientLocationChanged;
import static com.stas.parceldelivery.commons.constants.Queues.ClientOrderAssigned;
import static com.stas.parceldelivery.commons.constants.Queues.ClientStatusChanged;
import static com.stas.parceldelivery.commons.constants.Queues.CourierTaskAssigned;
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

import com.stas.parceldelivery.client.amqp.ClientListener;
import com.stas.parceldelivery.client.amqp.ClientMessageTransmitter;
import com.stas.parceldelivery.client.service.DeliveryService;
import com.stas.parceldelivery.commons.amqp.messages.LocationChanged;
import com.stas.parceldelivery.commons.amqp.messages.OrderAssignment;
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

@Testcontainers
@SpringBootTest
@ComponentScan("com.stas.parceldelivery.client.amqp")
public class ClientAmqpExchangeITest {
	
	

//	@Container
//	private static final RabbitMQContainer RABBIT_CONTAINER = new RabbitMQContainer("rabbitmq:management")
//	.withExposedPorts(5672, 15672);

	@Autowired
	ConnectionFactory connectionFactory;

	@Autowired
	AmqpAdmin amqp;

	@Autowired
	ClientListener office;

	@Autowired
	ClientMessageTransmitter transmiter;

	@Autowired
	TestListener adminListener;

	@Autowired
	TestListener courierListener;

	@MockBean
	DeliveryService deliveryService;

	@MockBean
	DummyService dummy;

	private RabbitTemplate orderAssigned;

	private RabbitTemplate locationChanged;

	private RabbitTemplate statusChanged;

	private final static OrderCreated mOrderCreated = new OrderCreated();
	private final static OrderAssignment mAssignment = new OrderAssignment();
	private final static OrderCancelled mOrderCancelled = new OrderCancelled();
	private final static OrderUpdated mOrderUpdated = new OrderUpdated();
	private final static OrderStatusChanged mStatusChanged = new OrderStatusChanged();
	private final static LocationChanged mLocationChanged = new LocationChanged();

	@PostConstruct
	public void init() {

		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.ADMIN_EXCHANGE, 
				Routes.OrderAssignment,
				/* tp */
				ClientOrderAssigned);
		
		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.ADMIN_EXCHANGE, 
				Routes.CourierTaskAssigned,
				/* tp */
				CourierTaskAssigned);
		

		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.COURIER_EXCHANGE, Routes.OrderStatusChanged,
				/* tp */
				AdminStatusChanged, ClientStatusChanged);

		ExchangeUtil.exchangeWithTopicToQueues(amqp, true, ExchangeName.COURIER_EXCHANGE, Routes.LocationChanged,
				/* tp */
				AdminLocationChanged, ClientLocationChanged);

		orderAssigned = TemplateUtil.createTemplate(connectionFactory, ExchangeName.ADMIN_EXCHANGE,
				Routes.OrderAssignment);
		locationChanged = TemplateUtil.createTemplate(connectionFactory, ExchangeName.COURIER_EXCHANGE,
				Routes.LocationChanged);
		statusChanged = TemplateUtil.createTemplate(connectionFactory, ExchangeName.COURIER_EXCHANGE,
				Routes.OrderStatusChanged);
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

	private DeliveryService verifyServiceCalled(int n) {
		return verify(deliveryService, times(n));
	}

	private DeliveryService mockOnServiceCall(final CountDownLatch latch) {
		return Mockito.doAnswer((i) -> {
			latch.countDown();
			return null;
		}).when(deliveryService);
	}

	private void verifyDummyCalled(int n) {
		verify(dummy, times(n)).doit();
	}

	@Test
	public void testClient_Sees_OrderAssigned() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		mockOnServiceCall(latch).deliveryAssigned(any(OrderAssignment.class));

		orderAssigned.convertAndSend(mAssignment);

		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).deliveryAssigned(any(OrderAssignment.class));
	}
	
	

	@Test
	public void testClient_And_Admin_see_StatusChanged() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		mockOnServiceCall(latch).updateStatus(any(OrderStatusChanged.class));
		mockOnDummyCall(latch);

		statusChanged.convertAndSend(mStatusChanged);

		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).updateStatus(any(OrderStatusChanged.class));
		verifyDummyCalled(1);
	}

	@Test
	public void testClient_And_Admin_see_LocationChanged() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		mockOnServiceCall(latch).updateLocation(any(LocationChanged.class));
		mockOnDummyCall(latch);

		locationChanged.convertAndSend(mLocationChanged);

		latch.await(1, TimeUnit.SECONDS);
		verifyServiceCalled(1).updateLocation(any(LocationChanged.class));
		verifyDummyCalled(1);
	}

	@Test
	public void testAmin_And_Courier_See_Order_Updated() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		mockOnDummyCall(latch);

		transmiter.orderUpdated(mOrderUpdated);

		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(2);
	}

	@Test
	public void testAmin_Sees_Order_Created() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);

		mockOnDummyCall(latch);

		transmiter.orderCreated(mOrderCreated);

		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(1);
	}

	@Test
	public void testBoth_Amin_And_Courier_see_OrderUpdated() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		mockOnDummyCall(latch);

		transmiter.orderUpdated(mOrderUpdated);

		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(2);
	}

	@Test
	public void testBoth_Amin_And_Courier_see_OrderCancelled() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		mockOnDummyCall(latch);

		transmiter.orderCancelled(mOrderCancelled);

		latch.await(1, TimeUnit.SECONDS);
		verifyDummyCalled(2);
	}

}
