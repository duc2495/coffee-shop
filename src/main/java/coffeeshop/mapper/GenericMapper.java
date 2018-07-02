package coffeeshop.mapper;

import java.util.List;

public interface GenericMapper<T, K> {
	
	public List<T> findAlḷ();
	public T selectByKey(K key);
	public void insert(T record);
	public void delete(K key);
	public void update(T record);
	
}
