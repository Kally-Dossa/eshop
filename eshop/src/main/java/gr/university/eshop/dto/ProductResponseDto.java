package gr.university.eshop.dto;

import gr.university.eshop.model.Product;

public class ProductResponseDto {
    private Long id;
    private String type;
    private String brand;
    private String description;
    private Double price;
    private Integer stock;

    // Instead of the full Shop object,  return the name or AFM!
    private String shopName;
    private String shopAfm;

    public ProductResponseDto(Product product) {
        this.id = product.getId();
        this.type = product.getType();
        this.brand = product.getBrand();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();

        // Safely extract only what we need from the Shop
        if (product.getShop() != null) {
            this.shopName = product.getShop().getName();
            this.shopAfm = product.getShop().getAfm();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getType() { return type; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public Double getPrice() { return price; }
    public Integer getStock() { return stock; }
    public String getShopName() { return shopName; }
    public String getShopAfm() { return shopAfm; }
}