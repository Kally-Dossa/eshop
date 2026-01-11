package gr.university.eshop.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    //price when order was made
    private double priceAtPurchase;

    // getters & setters
    public Long getId() { 
        return id;
    }

    public Order getOrder() {
        return order;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public int getQuantity() { 
        return quantity;
    }
    
    public double getPriceAtPurchase() { 
        return priceAtPurchase;
    }
    
    public void setOrder(Order order) { 
        this.order = order;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void setPriceAtPurchase(double priceAtPurchase) { 
        this.priceAtPurchase = priceAtPurchase;
    }
}