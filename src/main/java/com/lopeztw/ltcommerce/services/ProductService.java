package com.lopeztw.ltcommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lopeztw.ltcommerce.dto.ProductDTO;
import com.lopeztw.ltcommerce.entities.Product;
import com.lopeztw.ltcommerce.repositories.ProductRepository;
import com.lopeztw.ltcommerce.services.exceptions.DataBaseException;
import com.lopeztw.ltcommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado!")); // orElseThore tenta acessar um obj e caso n encontre, lança uma excessao
		return new ProductDTO(product);
		
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(String name, Pageable pageable) {
		Page<Product> result = repository.searchByName(name, pageable);
		return result.map(x -> new ProductDTO(x));
		
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity); // salvar
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) { // vai receber o id e o corpo
		
		Product entity = repository.getReferenceById(id); // aqui n vai no banco de dados, eh monitorado pela JPA	
	    copyDtoToEntity(dto, entity);
		entity = repository.save(entity); // salvar
		return new ProductDTO(entity);
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
	        	repository.deleteById(id);    		
		}
	    	catch (DataIntegrityViolationException e) { // caso tente deletar algo que esteja atrelado a outra coisa, ex: produto e pedido
	        	throw new DataBaseException("Falha de integridade referencial");
	   	}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		
	}
	
}
