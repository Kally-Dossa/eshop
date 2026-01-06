package gr.university.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.model.Product;
import gr.university.eshop.repository.CartItemRepository;
import gr.university.eshop.repository.CartRepository;

@Service
public class CartService {

    @Autowired CartRepository cartRepo;
    @Autowired CartItemRepository cartItemRepo;

    public Cart getCart(Citizen c) {
        return cartRepo.findByCitizen(c);
    }

    public void addItem(Long productId, int quantity, Long cartId) {
        //TODO find product from productID
        Product product = new Product();
        Cart cart = cartRepo.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
        
        if(!cartItemRepo.existsByProductIdAndCartId(productId, cartId)) {
            CartItem cartItem = new CartItem(cart, product, quantity);
            cartItemRepo.save(cartItem);

        }
    }
 
    //updateItem should only update quantity
    public void updateItem(CartItem cartItem) {
        CartItem existingCartItem = cartItemRepo.findById(cartItem.getId())
            .orElseThrow(() -> new RuntimeException("CartItem not found with id: "+ cartItem.getId()));
        
        existingCartItem.setQuantity(cartItem.getQuantity());

        cartItemRepo.save(existingCartItem);
    }

    public void deleteItem(Long cartItemId) {
        cartItemRepo.deleteById(cartItemId);
    }




}
