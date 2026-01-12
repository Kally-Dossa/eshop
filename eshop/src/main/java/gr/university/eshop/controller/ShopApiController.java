package gr.university.eshop.controller;

import gr.university.eshop.dto.LoginDto;
import gr.university.eshop.dto.ProductDto;
import gr.university.eshop.dto.ShopRegisterDto;
import gr.university.eshop.model.Product;
import gr.university.eshop.model.Shop;
import gr.university.eshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    // 1. ΕΓΓΡΑΦΗ SHOP
    // URL: POST http://localhost:8080/api/shop/register
    // Body (JSON): { "name": "...", "afm": "...", "email": "...", "password": "..." }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody ShopRegisterDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Σφάλμα Validation: " + Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
        }
        try {
            Shop createdShop = shopService.registerShop(dto);
            return ResponseEntity.ok(createdShop); // Επιστρέφει το αντικείμενο που δημιουργήθηκε (JSON)
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Σφάλμα εγγραφής: " + e.getMessage());
        }
    }

    // 2. LOGIN SHOP
    // URL: POST http://localhost:8080/api/shop/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Shop shop = shopService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInShop", shop); // Κρατάει το session για επόμενα calls στο Postman
            return ResponseEntity.ok("Επιτυχής σύνδεση: " + shop.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Σφάλμα: " + e.getMessage());
        }
    }

    // 3. ΠΡΟΣΘΗΚΗ ΠΡΟΪΟΝΤΟΣ (Απαιτεί Login πρώτα)
    // URL: POST http://localhost:8080/api/shop/products/add
    @PostMapping("/products/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto, HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Πρέπει να συνδεθείτε ως Κατάστημα πρώτα.");
        }

        try {
            shopService.addProductToShop(loggedInShop, productDto);
            return ResponseEntity.ok("Το προϊόν προστέθηκε επιτυχώς!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Σφάλμα: " + e.getMessage());
        }
    }

    // 4. ΛΙΣΤΑ ΠΡΟΪΟΝΤΩΝ ΤΟΥ ΚΑΤΑΣΤΗΜΑΤΟΣ
    // URL: GET http://localhost:8080/api/shop/products
    @GetMapping("/products")
    public ResponseEntity<?> getMyProducts(HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Δεν είστε συνδεδεμένοι.");
        }
        List<Product> products = shopService.getProductsByShopAfm(loggedInShop.getAfm());
        return ResponseEntity.ok(products);
    }
}