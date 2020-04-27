package rmboni.com.springAMQPProducer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rmboni.com.springAMQPProducer.domain.Order;
import rmboni.com.springAMQPProducer.service.OrderService;

import java.util.List;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private List<Order> orderList;
	
	@PostMapping("/sendOrder")
	public String sendOrder(Order order, RedirectAttributes redirectAttributes) {
		orderService.sendOrder(order);
		redirectAttributes.addFlashAttribute("message", "Order sent successfully!");
		return "redirect:/";
	}

	@GetMapping("/")
	public String home() {
		return "index.html";
	}
	
	@GetMapping("/orders")
	public String getOrders(Model model) {
		model.addAttribute("orders", orderList);
		return "orders.html";
	}
}
