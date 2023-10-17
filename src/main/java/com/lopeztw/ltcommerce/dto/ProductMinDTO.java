package com.lopeztw.ltcommerce.dto;

import com.lopeztw.ltcommerce.entities.Product;

public class ProductMinDTO {
	
	private Long id;
	private String name;
	private Double price;
	private String imgUrl;
	
	public ProductMinDTO(Long id, String name, Double price, String imgUrl) {
	
		this.id = id;
		this.name = name;
		this.price = price;
		this.imgUrl = imgUrl;
	}
		
		// facilitar a chamada la no ProductService
		public ProductMinDTO(Product entity) {
		
		
		id = entity.getId();
		name = entity.getName();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
	}

	// Nao precisa dos SETS pq nao quero fazer nenhuma alteração por aqui
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	
	
}
