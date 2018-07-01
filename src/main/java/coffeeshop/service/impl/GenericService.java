package coffeeshop.service.impl;

import java.util.List;
import coffeeshop.mapper.GenericMapper;

public class GenericService<T, K> implements coffeeshop.service.GenericService<T, K> {
	
	private GenericMapper<T, K> mapper;
	
	public GenericMapper<T, K> getMapper() {
		return this.mapper;
	}
	
	public void setMapper(GenericMapper<T, K> mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<T> getAll() {
		
		return mapper.findAlḷ();
	}

	@Override
	public T selectByKey(K key) {
		return mapper.selectByKey(key);
	}

	@Override
	public void insert(T record) {
		mapper.insert(record);
	}

	@Override
	public void delete(K key) {
		mapper.delete(key);
	}

	@Override
	public void update(T record) {
		mapper.update(record);
	}
	
}
