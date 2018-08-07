package coffeeshop;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import coffeeshop.repository.ProductRepository;
import coffeeshop.service.product.ProductService;

@RunWith(SpringRunner.class)
public class ProductServiceTest {
	
	@TestConfiguration
    static class ProducteServiceImplTestContextConfiguration {
  
        @Bean
        public ProductService productService() {
            return new ProductService();
        }
    }
	
	@Autowired
    private ProductService productService;
 
    @MockBean
    private ProductRepository productRepository;
    
    @Before
    public void setUp() {

    }
}
