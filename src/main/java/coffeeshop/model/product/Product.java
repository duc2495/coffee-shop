package coffeeshop.model.product;

public class Product {

    private int productId;
    private String productName;
    private ProductType productType;
    private int price;
    private String imageUrl;
    private String description;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductType getProductType() {
        return this.productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public int getPrice() {
        return price;
    }

    public void setProductPrice(int productPrice) {
        this.price = productPrice;
    }

    public String getImageURI() {
        return imageUrl;
    }

    public void setImageURI(String imageURI) {
        this.imageUrl = imageURI;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}