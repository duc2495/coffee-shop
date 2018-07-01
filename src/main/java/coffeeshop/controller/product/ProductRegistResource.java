package coffeeshop.controller.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import coffeeshop.model.product.ProductType;

public class ProductRegistResource {
    @NotBlank
    @Size(max = 100)
    private String productName;
    @NotNull
    private ProductType productType;
    @NotNull
    @Size(min = 0, max = 10000000)
    private Integer price;
    private String imageURI;
    private String description;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }


}
