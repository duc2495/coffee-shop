package coffeeshop.controller.product;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import coffeeshop.model.product.Product;
import coffeeshop.service.impl.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductHelper productHelper;

    @GetMapping
    public void getProductList(Model model) {
        List<Product> productList = productService.getProductList();
        if (productList.isEmpty()) {
            // TODO return 404 view
        }

        // resourceに変換
        List<ProductListResource> resources = productHelper.createProductListResource(productList);

        // TODO: return list product view
        return;
    }

    @GetMapping("/{productId}")
    public void getProduct(@PathVariable("productId") Integer productId, Model model) {
        Product product = productService.getProductDetail(productId);
        if (product == null) {
            // TODO return 404 view
        }

        // resourceに変換
        ProductDetailResource resource = productHelper.createProductDetailResource(product);

        // TODO: return product detail view
        return;
    }

    /**
     * 
     * @param resource
     * @param model
     */
    @PostMapping
    public void registProduct(@Valid @RequestBody ProductRegistResource resource, Model model) {
        /* service/repo layer can not access request data of web layer */
        // 商品modelを作成
        Product product = productHelper.createProductModel(resource);

        // DBにインサート
        productService.registProduct(product);

        // TODO: return view ?
        return;
    }

    /**
     * <pre>
     * vì resource của update giống với regist nên anh dùng chung class ProductRegistResource.
     * trường hợp có khác nhau thì tạo class ProductEditResource riêng
     * </pre>
     * 
     * @param productId
     * @param resource
     * @param model
     */
    @PatchMapping("/{productId}")
    public void updateProduct(@PathVariable("productId") Integer productId,
            @Valid @RequestBody ProductRegistResource resource, Model model) {

        // 商品のアクセス権限をチェック
        if (!productService.existProduct(productId)) {
            // TODO: return forbidden view
        }

        // 商品modelを作成
        Product product = productHelper.createProductModel(resource);

        // DBにを更新
        productService.updateProduct(product);

        // TODO: return view ?
        return;
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Integer productId,
            @Valid @RequestBody ProductRegistResource resource, Model model) {

        // 商品のアクセス権限をチェック
        if (!productService.existProduct(productId)) {
            // TODO: return forbidden view
        }

        // 商品削除
        productService.deleteProduct(productId);

        // TODO: return view ?
        return;
    }

}
