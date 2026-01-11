package gr.university.eshop.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;

public class GetCartDetailsDto {

    private Cart cart;
    private List<CartItem> items;
    private double totalPrice;
    
    public Cart getCart() {
        return cart;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
    }
    
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double calculateTotalPrice(){
        double total = 0;
        for(CartItem item : items){
            total += item.getProduct().getPrice()* item.getQuantity();
        }
        //use bigDecimal to keep two decimals for totalcost and round other decimals
        total = BigDecimal.valueOf(total).setScale(2,RoundingMode.HALF_UP).doubleValue();
        return total;
    }
}
