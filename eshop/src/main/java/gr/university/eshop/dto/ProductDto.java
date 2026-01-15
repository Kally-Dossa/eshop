package gr.university.eshop.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class ProductDto {
    // Το @NotBlank ελέγχει να μην είναι null ΚΑΙ να μην είναι κενό κείμενο
    @NotBlank(message = "Ο τύπος προϊόντος είναι υποχρεωτικός")
    private String type;

    @NotBlank(message = "Η μάρκα είναι υποχρεωτική")
    private String brand;
    private String description;

    @NotNull(message = "Η τιμή είναι υποχρεωτική")
    @Min(value = 0, message = "Η τιμή δεν μπορεί να είναι αρνητική")
    private Double price;

    @NotNull(message = "Το απόθεμα είναι υποχρεωτικό")
    @Min(value = 0, message = "Το απόθεμα δεν μπορεί να είναι αρνητικό")
    private Integer stock;


    public ProductDto() {
    }


    public ProductDto(String type, String brand, String description, Double price, Integer stock) {
        this.type = type;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // --- GETTERS & SETTERS ---

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