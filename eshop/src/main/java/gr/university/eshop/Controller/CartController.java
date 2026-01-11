package gr.university.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gr.university.eshop.dto.GetCartDetailsDto;
import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    //consider returning list of CartItem
    @GetMapping(path = "/getCart")
    public GetCartDetailsDto getCart(HttpSession session) throws Exception{
        Citizen citizen = getCitizenFromSession(session);
        return cartService.getCart(citizen);
    }
    
    @PostMapping(path = "/addItem")
    public void addItem(@RequestBody Long productId,
        @RequestBody int quantity,
        HttpSession session) throws Exception{
        
        Citizen citizen = getCitizenFromSession(session);
        cartService.addItem(citizen, productId, quantity);
    }
    
    @PutMapping(path = "/updateItemQuantity")
    public void updateItemQuantity(@RequestBody Long productId, @RequestBody int quantity, 
        HttpSession session) throws Exception{
        
        Citizen citizen = getCitizenFromSession(session);
        cartService.updateItemQuantity(citizen, productId, quantity);
 
    }

    @DeleteMapping(path = "/deleteItem")
    public void deleteItem(@RequestParam(value = "id") Long productId, HttpSession session) throws Exception {
        Citizen citizen = getCitizenFromSession(session);
        cartService.deleteItem(citizen, productId);
    }


    @DeleteMapping(path = "/clearCart")
    public void clearCart(HttpSession session){
        Citizen citizen = getCitizenFromSession(session);
        cartService.clearCart(citizen);
    }

    //method to retrieve logged in citizen from httpsession
    private Citizen getCitizenFromSession(HttpSession session) {
        /* TODO
        String afm = (String) session.getAttribut("CITIZEN_AFM");
        String role = (String) session.getAttribut("ROLE");

        if(afm == null || !"CITIZEN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } 
        
        return citizenRepository.findById(afm)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Citizen not found")
        );

        */

        //TODO maybe do not store in session CItizen object but email and Role (Citizen or shop)
        return (Citizen)session.getAttribute("loggedInUser");
    }

}
