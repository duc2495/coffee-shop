package coffeeshop.service.product;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffeeshop.model.order.Order;
import coffeeshop.model.order.OrderStatus;
import coffeeshop.model.product.Product;
import coffeeshop.model.product.ProductType;
import coffeeshop.repository.ProductRepository;
import coffeeshop.service.OrderService;

@Service
@Transactional(readOnly = true)
public class ProductService {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderService orderService;
	
	/**
	 * 商品一覧を取得する
	 * 
	 * @return
	 */
	public List<Product> getProductList() {
		return productRepository.getListProduct();
	}

	/**
	 * 商品詳細を取得
	 * 
	 * @param productId
	 * @return
	 */
	public Product getProductDetail(Integer productId) {
		return productRepository.getProductById(productId);
	}
	public List<Product> getProductList(ProductType productType){
		List<Product> list = this.getProductList();
		list.removeIf(new Predicate<Product>() {

			@Override
			public boolean test(Product t) {
				// TODO Auto-generated method stub
				return !t.getProductType().equals(productType);
			}
		});
		return list;
	}
	public List<Product> getProductPage(int page, int numPerPage, ProductType productType){
		List<Product> list = this.getProductList(productType);
		list.sort(new Comparator<Product>() {
			@Override
			public int compare(Product o1, Product o2) {
				// TODO Auto-generated method stub
				return o1.getProductId() - o2.getProductId();
			}
			
		});
		return list.subList((page-1)*numPerPage, page*numPerPage>list.size() ? list.size() :  page*numPerPage);
	}
	
	/**
	 * 商品登録
	 * 
	 * @param product
	 * @return
	 */
	@Transactional(readOnly = false)
	public int registProduct(Product product) {
		// productのidを予約
		int productId = productRepository.getNewProductId();

		// 商品テーブルにインサート
		product.setProductId(productId);
		productRepository.insertProduct(product);

		return productId;
	}

	/**
	 * 商品が存在するかどうかチェック
	 * 
	 * @param productId
	 * @return
	 */
	public boolean existProduct(Integer productId) {
		return productRepository.hasProduct(productId);
	}

	/**
	 * 商品を更新する
	 * 
	 * @param product
	 */
	@Transactional(readOnly = false)
	public void updateProductInfo(Product product) {
		productRepository.updateProductInfo(product);
	}
	
	/**
	 * 商品のイメージを更新する
	 * 
	 * @param product
	 */
	@Transactional(readOnly = false)
	public void updateProductImage(Product product) {
		productRepository.updateProductImage(product);
	}

	/**
	 * 商品削除
	 * 
	 * @param productId
	 */
	@Transactional(readOnly = false)
	public void deleteProduct(Integer productId) {
		productRepository.deleteProduct(productId);
	}
	
	public List<Product> searchByKeyWord(String keyWord){
		return productRepository.searchByKeyWord(keyWord);
	}
	
	public List<Product> getNewProductInTimeInterval(Date dayFrom, Date dayTo){
		return productRepository.getNewProductInTimeInterval(dayFrom, dayTo);
	}
	
	
}
