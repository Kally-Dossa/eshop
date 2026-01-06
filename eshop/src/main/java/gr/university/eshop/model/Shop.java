package gr.university.eshop.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Shop {

    @Id
    private String afm; // afm shop  Primary Key

    private String companyName;
    private String owner;
    private String password;

    // shop has many items
    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Product> products;

    public Shop() {}

    public Shop(String afm, String companyName, String owner, String password) {
        this.afm = afm;
        this.companyName = companyName;
        this.owner = owner;
        this.password = password;
    }

    // Getters και Setters
    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}