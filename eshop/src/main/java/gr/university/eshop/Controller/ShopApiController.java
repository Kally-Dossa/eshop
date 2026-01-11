package gr.university.eshop.Controller;

import gr.university.eshop.DTO.LoginDto;
import gr.university.eshop.DTO.ShopRegisterDto;
import gr.university.eshop.Entity.Shop;
import gr.university.eshop.Service.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/shop") // Όλα τα URLs εδώ ξεκινάνε με /api/shop
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    // URL: POST /api/shop/register
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody ShopRegisterDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Σφάλμα: " + Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        try {
            shopService.registerShop(dto);
            return ResponseEntity.ok("Η εγγραφή ολοκληρώθηκε!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Σφάλμα εγγραφής: " + e.getMessage());
        }
    }

    // URL: POST /api/shop/login
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Σφάλμα: " + Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        try {
            Shop shop = shopService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInShop", shop); // Αποθήκευση στο Session
            return ResponseEntity.ok("Welcome Shop: " + shop.getName());
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Σφάλμα: " + e.getMessage());
        }
    }
}