package coffeeshop.repository;

import java.util.Date;
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

    public void updateProductInfo(Product product);
    
    public void updateProductImage(Product product);
    
    public void deleteProduct(Integer productId);
    
    public List<Product> searchByKeyWord(@Param("keyWord") String keyWord);

    public List<Product> getNewProductInTimeInterval(@Param("dayFrom") Date dayFrom, @Param("dayTo") Date dayTo);

}
