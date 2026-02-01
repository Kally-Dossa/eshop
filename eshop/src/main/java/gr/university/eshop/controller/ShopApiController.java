package gr.university.eshop.controller;

import gr.university.eshop.dto.LoginDto;
import gr.university.eshop.dto.ProductDto;
import gr.university.eshop.dto.ProductResponseDto;
import gr.university.eshop.dto.ShopRegisterDto;
import gr.university.eshop.model.Shop;
import gr.university.eshop.service.ShopService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    // --- 1. SHOP REGISTER ---
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody ShopRegisterDto dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(shopService.registerShop(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- 2. SHOP LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Shop shop = shopService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInShop", shop);
            return ResponseEntity.ok(Map.of("message", "Login successful", "shopName", shop.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    // --- 3. ADD PRODUCT ---
    @PostMapping("/products/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDto productDto, HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            shopService.addProductToShop(loggedInShop, productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Product added"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- 4. GET MY PRODUCTS ---
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getProducts(HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<ProductResponseDto> dtos = shopService.getProductsByShopAfm(loggedInShop.getAfm())
                .stream()
                .map(ProductResponseDto::new)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    // --- 5. UPDATE STOCK ---
    @PostMapping("/products/update-stock")
    public ResponseEntity<?> updateStock(@RequestParam Long productId, @RequestParam Integer newStock, HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            shopService.updateProductStock(productId, newStock, loggedInShop);
            return ResponseEntity.ok(Map.of("message", "Stock updated"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- 6. DELETE PRODUCT ---
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        try {
            shopService.deleteProduct(id, loggedInShop);
            return ResponseEntity.ok(Map.of("message", "Deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // --- 7. LOGOUT ---
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}