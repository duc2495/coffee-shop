package coffeeshop.service;

import java.util.List;

public interface GenericService<T, K> {
	
	public List<T> getAll();
	public T selectByKey(K key);
	public void insert(T record);
	public void delete(K key);
	public void update(T record);
}
