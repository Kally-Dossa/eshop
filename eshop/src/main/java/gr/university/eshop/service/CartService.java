package gr.university.eshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import gr.university.eshop.dto.GetCartDetailsDto;
import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.model.Product;
import gr.university.eshop.repository.CartItemRepository;
import gr.university.eshop.repository.CartRepository;
import gr.university.eshop.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;
    
    @Autowired 
    private CartItemRepository cartItemRepo;
    
    @Autowired 
    private ProductRepository productRepo;
 

    public GetCartDetailsDto getCart(Citizen citizen) {
        GetCartDetailsDto response = new GetCartDetailsDto();
        
        /*retrieve citizen's cart , if citizen does not have a cart
        * which should not happen, a cart is created and assigned to the citizen
        */
        Cart selectedCart = getOrCreateCart(citizen);
        List<CartItem> items = new ArrayList<>();

        for(CartItem cartItem : cartItemRepo.findAll()){
            if(selectedCart.getId().equals(cartItem.getCart().getId())) {
                items.add(cartItem);
            }
        }

        response.setCart(selectedCart);
        response.setItems(items);
        response.setTotalPrice(response.calculateTotalPrice());

        return response;
    }

    public void addItem(Citizen citizen, Long productId, int quantity) {
        
        Product product = productRepo.findById(productId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not Found!"));
        
        //check if we have stock to add to cart
        if(product.getStock()<quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough Stock of product");
        }
        
        Cart cart = getOrCreateCart(citizen);
        
        if(!cartItemRepo.existsByProductIdAndCartId(productId, cart.getId())) {
            CartItem cartItem = new CartItem(cart, product, quantity);
            cartItemRepo.save(cartItem);
        }
        else{
            CartItem existingCartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "CartItem not Found"));
            //Decided to work like this, when citizen add same product add the quantity added to existing    
            existingCartItem.setQuantity(existingCartItem.getQuantity()+quantity);
            cartItemRepo.save(existingCartItem);
        }
    }
 
    //updateItem should only update quantity
    public void updateItemQuantity(Citizen citizen, Long productId, int quantity) {
        if(quantity<1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be >= 1");
        }
        
        Cart cart = getOrCreateCart(citizen);

        CartItem existingCartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "CartItem not Found"));
        
        
        if(existingCartItem.getProduct().getStock()< quantity) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not enough stock of product");
        }
        existingCartItem.setQuantity(quantity);

        cartItemRepo.save(existingCartItem);
    }

    public void deleteItem(Citizen citizen, Long productId) {
        Cart cart = getOrCreateCart(citizen);
        CartItem existingCartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "CartItem not Found"));
        
        cartItemRepo.deleteById(existingCartItem.getId());
    }

    public void clearCart(Citizen citizen) {
        Cart cart = getOrCreateCart(citizen);
        
        for(CartItem item : cartItemRepo.findByCart(cart)){
            cartItemRepo.delete(item);
        }
        
    }    

    private Cart getOrCreateCart(Citizen citizen) {
        return cartRepo.findByCitizen_Afm(citizen.getAfm())
            .orElseGet(() -> {
                Cart cart = new Cart();
                cart.setCitizen(citizen);
                //TODO
                //citizen.setCart(cart);
                //citizenRepo.save(citizen);
                return cartRepo.save(cart);
            });
    }    

}
