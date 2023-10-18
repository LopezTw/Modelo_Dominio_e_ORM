package com.lopeztw.ltcommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lopeztw.ltcommerce.dto.OrderDTO;
import com.lopeztw.ltcommerce.entities.Order;
import com.lopeztw.ltcommerce.repositories.OrderRepository;
import com.lopeztw.ltcommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!")); // orElseThore tenta acessar um obj e caso n encontre, lança uma excessao
		return new OrderDTO(order);
		
	}
	
	
}
