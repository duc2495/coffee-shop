package coffeeshop.repository;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import coffeeshop.model.product.Product;

@Repository
public interface ProductRepository {

    public List<Product> getListProduct();

    public Product getProductById(@Param("productId") Integer productId);

    public int getNewProductId();

    public int insertProduct(Product product);

    public boolean hasProduct(@Param("productId") Integer productId);

    public void updateProduct(Product product);
    
    public void deleteProduct(Integer productId);

}
