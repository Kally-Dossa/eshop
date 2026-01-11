package gr.university.eshop.model;

import jakarta.persistence.*;
import java.util.List;


import java.util.ArrayList;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // each citizen has one cart
    @OneToOne
    @JoinColumn(name = "citizen_afm")
    private Citizen citizen;

    //in order to save quantity we need to create CartItem
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public Cart() {

    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    
    
}