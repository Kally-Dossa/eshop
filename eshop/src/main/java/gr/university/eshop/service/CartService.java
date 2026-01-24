package gr.university.eshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        
        /*retrieve citizen's cart , if citizen does not have one
        * a cart is created
        */
        Cart selectedCart = getOrCreateCart(citizen);
        List<CartItem> items = new ArrayList<>();

        for(CartItem cartItem : cartItemRepo.findByCart_Id(selectedCart.getId())){
            if(selectedCart.getId().equals(cartItem.getCart().getId())) {
                items.add(cartItem);
            }
        }

        response.setCart(selectedCart);
        response.setItems(items);
        response.setTotalPrice(response.calculateTotalPrice());

        return response;
    }

    public void addItem(Citizen citizen, Long productId, int quantity) throws Exception{
        
        Product product = productRepo.findById(productId)
            .orElseThrow(() -> new Exception("Το προϊόν δεν βρέθηκε."));
        
        //check if we have stock to add to cart
        if(product.getStock()<quantity) {
            throw new Exception("Δεν υπάρχει αρκετό απόθεμα.");
        }
        
        Cart cart = getOrCreateCart(citizen);
        
        if(!cartItemRepo.existsByProductIdAndCartId(productId, cart.getId())) {
            CartItem cartItem = new CartItem(cart, product, quantity);
            cartItemRepo.save(cartItem);
        }
        else{
            CartItem existingCartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new Exception("Δεν βρέθηκε το προϊόν στο καλάθι."));
            //Decided to work like this, when citizen add same product add the quantity added to existing
            //need to check if new quantity plus the quanitty in the cart exceeds stock    
            if(product.getStock()<quantity+existingCartItem.getQuantity()) {
                throw new Exception("Το απόθεμα είναι μικρότερο από το άθροισμα της ποσότητας που επιλέχθηκε και αυτής στο καλάθι.");
            }
            existingCartItem.setQuantity(existingCartItem.getQuantity()+quantity);
            cartItemRepo.save(existingCartItem);
        }
    }
 
    //updateItem should only update quantity
    public void updateItemQuantity(Citizen citizen, Long productId, int quantity) throws Exception{
        if(quantity<1) {
            throw new Exception("Η ποσότητα πρέπει να είναι >= 1");
        }
        
        Cart cart = getOrCreateCart(citizen);

        CartItem existingCartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new Exception("Δεν βρέθηκε το προϊόν στο καλάθι."));
        
        
        if(existingCartItem.getProduct().getStock()< quantity) {
            throw new Exception("Δεν υπάρχει αρκετό απόθεμα.");
        }
        existingCartItem.setQuantity(quantity);

        cartItemRepo.save(existingCartItem);
    }

    public void deleteItem(Citizen citizen, Long productId) throws Exception{
        Cart cart = getOrCreateCart(citizen);
        CartItem existingCartItem = cartItemRepo.findByProductIdAndCartId(productId, cart.getId())
                .orElseThrow(() -> new Exception("Δεν βρέθηκε το προϊόν στο καλάθι."));
        
        cartItemRepo.deleteById(existingCartItem.getId());
    }

    public void clearCart(Citizen citizen) {
        Cart cart = getOrCreateCart(citizen);
        
        for(CartItem item : cartItemRepo.findByCart_Id(cart.getId())){
            cartItemRepo.delete(item);
        }
        
    }    

    private Cart getOrCreateCart(Citizen citizen) {
        return cartRepo.findByCitizen_Afm(citizen.getAfm())
            .orElseGet(() -> {
                Cart cart = new Cart();
                cart.setCitizen(citizen);
                
                return cartRepo.save(cart);
            });
    }    

}
