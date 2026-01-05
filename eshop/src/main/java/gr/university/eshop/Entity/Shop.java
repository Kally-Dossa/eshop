package gr.university.eshop.Entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Shop {
    @Id
    private String afm; // ΑΦΜ ως ID
    private String name; // Επωνυμία
    private String owner;
    private String password;

    @OneToMany(mappedBy="shop", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Shop() {}

    public Shop(String afm, String name, String owner, String password) {
        this.afm = afm;
        this.name = name;
        this.owner = owner;
        this.password = password;
    }

    public void addProduct(Product p) {
        products.add(p);
        p.setShop(this);
    }

    // Getters Setters
    public String getAfm() { return afm; }
    public String getName() { return name; }
    public List<Product> getProducts() { return products; }
}