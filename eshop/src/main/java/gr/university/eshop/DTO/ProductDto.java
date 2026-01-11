package gr.university.eshop.DTO;

public class ProductDto {

    private String type;
    private String brand;
    private String description;
    private Double price;
    private Integer stock;

    // Κενός Constructor (Απαραίτητος)
    public ProductDto() {
    }

    // Constructor με ορίσματα (Προαιρετικός, για δική σου ευκολία)
    public ProductDto(String type, String brand, String description, Double price, Integer stock) {
        this.type = type;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // --- GETTERS & SETTERS (ΑΥΤΑ ΕΛΕΙΠΑΝ) ---

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}