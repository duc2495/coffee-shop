package coffeeshop.controller.product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import coffeeshop.model.product.Product;

@Component
public class ProductHelper {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * 商品のresource→model変換
     * 
     * @param resource
     * @return
     */
    public Product createProductModel(ProductRegistResource resource) {
        // 商品情報をマッピング
        Product model = modelMapper.map(resource, Product.class);

        // TODO: その他のマッピング

        return model;
    }

    /**
     * 商品詳細のmodel→resource変換
     * 
     * @param models
     * @return
     */
    public ProductDetailResource createProductDetailResource(Product model) {
        // 商品情報をマッピング
        ProductDetailResource resource = modelMapper.map(model, ProductDetailResource.class);

        // その他をマッピング nếu có

        return resource;
    }
    
    /**
     * 商品詳細のmodel→resource変換
     * 
     * @param models
     * @return
     */
    public ProductResource createProductResource(Product model) {
        // 商品情報をマッピング
    	ProductResource resource = modelMapper.map(model, ProductResource.class);

        // その他をマッピング nếu có

        return resource;
    }
}
