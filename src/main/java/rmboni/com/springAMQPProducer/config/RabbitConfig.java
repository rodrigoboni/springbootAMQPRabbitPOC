package rmboni.com.springAMQPProducer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {
	public final static String QUEUE_ORDERS = "orders-queue";
	public final static String QUEUE_DEAD_ORDERS = "orders-queue";
	public final static String EXCHANGE_ORDERS = "orders-exchange";
	
	@Bean
	Queue ordersQueue() {
		return QueueBuilder
				.durable(QUEUE_ORDERS) // config queue as durable type
				.withArgument("x-dead-letter-exchange", "") // also config 15sec TTL for sending msg to dlq
				.withArgument("x-dead-letter-routing-key", QUEUE_DEAD_ORDERS)
				.withArgument("x-message-ttl", 15000)
				.build();
	}
	
	@Bean
	Queue deadLetterQueue() { //config dlq
		return QueueBuilder.durable(QUEUE_DEAD_ORDERS).build();
	}
	
	@Bean
	Exchange ordersExchange() { //config exchange
		return ExchangeBuilder.topicExchange(EXCHANGE_ORDERS).build();
	}
	
	@Bean
	Binding binding(Queue ordersQueue, TopicExchange ordersExchange) {
		// config queue x exchange x route key
		// bind orders queue to orders exchange with routing key "orders-queue"
		return BindingBuilder.bind(ordersQueue).to(ordersExchange).with(QUEUE_ORDERS);
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
		// config default json encoder for sending messages
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
		
		return rabbitTemplate;
	}
	
	@Bean
	public MessageConverter producerJackson2MessageConverter() { //message encoder
		return new Jackson2JsonMessageConverter();
	}
	
	
	@Override
	public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
		// config listener to json decoding
		registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
	}
	
	@Bean
	public MessageHandlerMethodFactory messageHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory defaultMessageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
		defaultMessageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
		
		return defaultMessageHandlerMethodFactory;
	}
	
	@Bean
	public org.springframework.messaging.converter.MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}
}
