package rmboni.com.springAMQPProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import rmboni.com.springAMQPProducer.domain.Order;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringAmqpProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpProducerApplication.class, args);
	}

	@Bean(name="orderList")
	public List<Order> orderList() {
		return new ArrayList<>();
	}
}
