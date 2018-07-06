package coffeeshop.service.product;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coffeeshop.model.product.Product;
import coffeeshop.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class ProductService {
	@Autowired
	ProductRepository productRepository;

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
	public void updateProduct(Product product) {
		productRepository.updateProduct(product);
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

}
