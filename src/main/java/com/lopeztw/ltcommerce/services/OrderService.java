package com.lopeztw.ltcommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lopeztw.ltcommerce.dto.OrderDTO;
import com.lopeztw.ltcommerce.dto.OrderItemDTO;
import com.lopeztw.ltcommerce.entities.Order;
import com.lopeztw.ltcommerce.entities.OrderItem;
import com.lopeztw.ltcommerce.entities.Product;
import com.lopeztw.ltcommerce.entities.User;
import com.lopeztw.ltcommerce.entities.enums.OrderStatus;
import com.lopeztw.ltcommerce.repositories.OrderItemRepository;
import com.lopeztw.ltcommerce.repositories.OrderRepository;
import com.lopeztw.ltcommerce.repositories.ProductRepository;
import com.lopeztw.ltcommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!")); // orElseThore tenta acessar um obj e caso n encontre, lança uma excessao
		return new OrderDTO(order);
		
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user); // usuario logado
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDto.getProductId());
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());  // product.getPrice() -> manter o preço q foi vendido na epoca e nao atualizado.
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
	
	
}
