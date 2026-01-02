package gr.university.eshop.Entity;

import jakarta.persistence.*;

@Entity
public class Citizen {

    @Id
    private String afm; // afm Primary Key

    private String name;
    private String surname;
    private String email;
    private String password;

    @OneToOne(mappedBy = "citizen", cascade = CascadeType.ALL)
    private Cart cart;

    public Citizen() {}

    public Citizen(String afm, String name, String surname, String email, String password) {
        this.afm = afm;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    // Getters και Setters
    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
}