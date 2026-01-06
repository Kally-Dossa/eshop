package gr.university.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.service.CartService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    //consider returning list of CartItem
    @GetMapping(path = "/getCart")
    public Cart getCart() throws Exception{
        //TODO get citizen from login either spring security or session
        Citizen c = new Citizen("123146", "Takis", "Takaras", "wqe@sd.c", "1234");
        return cartService.getCart(c);
    }
    
    @PostMapping(path = "/addItem")
    public void addItem(@RequestBody Long productId,
        @RequestBody int quantity,
        @RequestBody Long cartId) throws Exception{
        //TODO: process POST request
        
        cartService.addItem(productId, quantity, cartId);
    }
    
    @PutMapping(path = "updateItem")
    public void updateItem(@RequestBody CartItem cartItem) throws Exception{
        cartService.updateItem(cartItem);
        
    }

    @DeleteMapping(path = "/deleteItem")
    public void deleteItem(@RequestParam(value = "id") Long cartItemId) throws Exception {
        cartService.deleteItem(cartItemId);
    }

}
