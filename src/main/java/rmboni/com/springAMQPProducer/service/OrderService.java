package rmboni.com.springAMQPProducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmboni.com.springAMQPProducer.config.RabbitConfig;
import rmboni.com.springAMQPProducer.domain.Order;

@Service
public class OrderService {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void sendOrder(Order order) {
			// routing key + order
			rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_ORDERS, order);
	}
}
