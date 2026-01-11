package gr.university.eshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "orders") //order can be a reserved sql keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "citizen_afm", nullable = false)
    private Citizen citizen;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public List<OrderItem> getItems(){
        return items;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    


}
