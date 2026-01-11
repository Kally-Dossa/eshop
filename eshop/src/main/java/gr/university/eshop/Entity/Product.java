package gr.university.eshop.Entity;

import gr.university.eshop.DTO.ProductDto;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String brand;
    private String description;
    private Double price;
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_afm")
    private Shop shop;

    // 1. Κενός Constructor (Απαραίτητος για το Hibernate)
    public Product() {
    }

    // 2. Constructor που παίρνει τα δεδομένα από το DTO
    // ΠΡΟΣΟΧΗ: Εδώ γινόταν το λάθος και αποθήκευε null!
    public Product(ProductDto dto) {
        this.type = dto.getType();           // <-- Παίρνει τον Τύπο από το DTO
        this.brand = dto.getBrand();         // <-- Παίρνει τη Μάρκα
        this.description = dto.getDescription();
        this.price = dto.getPrice();
        this.stock = dto.getStock();
    }

    // Getters & Setters του Entity
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Shop getShop() { return shop; }
    public void setShop(Shop shop) { this.shop = shop; }
}