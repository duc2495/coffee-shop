package coffeeshop;
import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.repository.ProductRepository;

@RunWith(SpringRunner.class)
@AutoConfigureMybatis
@MybatisTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SqlSession session;
	
    @Before
    public void setUp() {

    }
    @Test
    public void addProductTest(){
    	Product p = new Product();
    	p.setDescription("Test Desciption");
    	p.setImageUrl("Test imageURL");
    	p.setPrice(011);
    	p.setProductName("Test Name");
    	p.setProductType(ProductType.FROM_COFFEE);
    	productRepository.insertProduct(p);
    	int id = p.getProductId();
    	String sql = "Select * from product where product_id = ?";
    	PreparedStatement stm;
		try {
			stm = session.getConnection().prepareStatement(sql);
		
    	stm.setInt(1, id);
    	
    	ResultSet result = stm.executeQuery();
    	if(result.next()){
	    	assertEquals(p.getDescription(), result.getString("description"));
	    	assertEquals(p.getImageUrl(), result.getString("image_url"));
	    	assertEquals(p.getProductName(), result.getString("product_name"));
	    	assertEquals(p.getProductType().toString(), result.getString("product_type"));
    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
