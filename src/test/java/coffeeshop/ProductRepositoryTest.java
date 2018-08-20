package coffeeshop;
import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit4.SpringRunner;

import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.repository.ProductRepository;

@RunWith(SpringRunner.class)
@MybatisTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository productRepository;
	
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
    	Product p_check = productRepository.getProductById(id);
    	assertEquals(p.getProductId(), p_check.getProductId());
    	assertEquals(p.getDescription(), p_check.getDescription());
    	assertEquals(p.getImageUrl(), p_check.getImageUrl());
    	assertEquals(p.getPrice(), p_check.getPrice());
    	assertEquals(p.getProductName(), p_check.getProductName());
    	assertEquals(p.getProductType(), p_check.getProductType());
    }
    
    @Test
    public void getNewProductInTimeIntervalTest(){
    	Calendar carlendar1 = new GregorianCalendar();
    	Calendar carlendar2 = new GregorianCalendar();
    	carlendar2.set(2018, 7, 15);
    	Date timeTo = carlendar1.getTime();
    	Date timeFrom = carlendar2.getTime();
    	assertEquals(productRepository.getNewProductInTimeInterval(timeFrom, timeTo).size(), 95);
    	assertEquals(productRepository.getNewProductInTimeInterval(timeTo, timeTo).size(),0);
    }
}
