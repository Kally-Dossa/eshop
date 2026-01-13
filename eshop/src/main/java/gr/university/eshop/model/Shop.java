package gr.university.eshop.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Shop {
    @Id
    private String afm; // ΑΦΜ ως ID
    private String name; // Επωνυμία;
    private String email;
    private String password;
    private String role;


    @OneToMany(mappedBy="shop", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Shop() {}

    public Shop(String afm, String name, String email, String password,  String role) {
        this.afm = afm;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void addProduct(Product p) {
        products.add(p);
        p.setShop(this);
    }

    // Getters Setters
    public String getAfm() { return afm; }

    public void setName(String name) {
        this.name = name;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() { return name; }
    public List<Product> getProducts() { return products; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

}