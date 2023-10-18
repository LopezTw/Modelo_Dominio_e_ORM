package com.lopeztw.ltcommerce.dto;

import java.util.ArrayList;
import java.util.List;

import com.lopeztw.ltcommerce.entities.Category;
import com.lopeztw.ltcommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	
	private Long id;
	
	@Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
	@NotBlank(message = "Campo Obrigatório")
	private String name;
	@Size(min = 10, message = "Descrição precisa ter no minimo 10 caracteres")
	@NotBlank
	private String description;	
	@NotNull(message = "Campo requerido")
	@Positive(message = "O valor deve ser positivo")
	private Double price;
	private String imgUrl;
	
	@NotEmpty(message = "Deve ter pelo menos uma categoria!")
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
	
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
		
	// facilitar a chamada la no ProductService
	public ProductDTO(Product entity) {
				
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		for(Category cat : entity.getCategories()) { // Pra cada Categoria CAT, dentro da lista categories, adiciona na lista categories DTO, o dto correspondente
			categories.add(new CategoryDTO(cat));
		}
	}

	// Nao precisa dos SETS pq nao quero fazer nenhuma alteração por aqui
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	
	
}
