package com.lopeztw.ltcommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lopeztw.ltcommerce.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
}
