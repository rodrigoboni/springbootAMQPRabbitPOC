package rmboni.com.springAMQPProducer.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rmboni.com.springAMQPProducer.config.RabbitConfig;
import rmboni.com.springAMQPProducer.domain.Order;

import java.util.List;

@Component
public class OrderListener {
	private final Logger logger = LoggerFactory.getLogger(OrderListener.class);
	
	@Autowired
	private List<Order> orderList;
	
	@RabbitListener(queues = RabbitConfig.QUEUE_ORDERS)
	public void orderListener(Order order) {
		orderList.add(order);
		logger.info("Order received: {}", order);
	}
}
