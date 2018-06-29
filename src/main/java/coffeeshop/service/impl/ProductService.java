package coffeeshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import coffeeshop.mapper.ProductMapper;
import coffeeshop.model.Product;

@Service
public class ProductService extends GenericService<Product, Integer> {
	
	@Autowired
	public void setProductMapper(ProductMapper mapper) {
		this.setMapper(mapper);
	}
}
