package gr.university.eshop.controller;

import gr.university.eshop.dto.LoginDto;
import gr.university.eshop.dto.ProductDto;
import gr.university.eshop.dto.ProductResponseDto;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/shop")
public class ShopApiController {

    @Autowired
    private ShopService shopService;

    // --- HELPER: Create JSON Error Response ---
    private Map<String, String> generateError(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return errorResponse;
    }

    // --- 1. SHOP REGISTER ---
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody ShopRegisterDto dto, BindingResult result) {
        // Collect all validation errors (e.g. invalid email, empty fields)
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            Shop createdShop = shopService.registerShop(dto);
            // Return 201 Created and the Shop data
            return ResponseEntity.status(HttpStatus.CREATED).body(createdShop);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateError(e.getMessage()));
        }
    }

    // --- 2. SHOP LOGIN ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Shop shop = shopService.login(loginDto.getEmail(), loginDto.getPassword());
            session.setAttribute("loggedInShop", shop);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("shopName", shop.getName());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(generateError("Invalid credentials"));
        }
    }

    // --- 3. ADD PRODUCT (With Validation < 0) ---
    @PostMapping("/products/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody ProductDto productDto,
                                        BindingResult result,
                                        HttpSession session) {

        // 1. Έλεγχος Login
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(generateError("Unauthorized: Please login first."));
        }

        // 2. Έλεγχος Validation (Κενά πεδία, αρνητικά νούμερα κλπ)
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }

        // 3. Αποθήκευση
        try {
            shopService.addProductToShop(loggedInShop, productDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Product added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateError(e.getMessage()));
        }
    }

    // --- 4. GET MY PRODUCTS ---
    @GetMapping("/products") // Or whatever your URL is
    public ResponseEntity<List<ProductResponseDto>> getProducts(HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");

        // Fetch the raw entities from the database
        List<Product> products = shopService.getProductsByShopAfm(loggedInShop.getAfm());

        // CONVERT entities to DTOs
        List<ProductResponseDto> responseDtos = products.stream()
                .map(product -> new ProductResponseDto(product))
                .toList(); // or .collect(Collectors.toList()) for older Java versions

        return ResponseEntity.ok(responseDtos);
    }

    // --- 5. UPDATE STOCK (With Validation < 0) ---
    // URL: POST http://localhost:8080/api/shop/products/update-stock?productId=1&newStock=50
    @PostMapping("/products/update-stock")
    public ResponseEntity<?> updateStock(@RequestParam Long productId,
                                         @RequestParam Integer newStock,
                                         HttpSession session) {
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");
        if (loggedInShop == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(generateError("Unauthorized"));
        }

        // --- VALIDATION: Check for Negative Stock ---
        if (newStock < 0) {
            return ResponseEntity.badRequest().body(generateError("Stock cannot be negative."));
        }

        try {
            // FIX: Pass 'loggedInShop' as the 3rd argument
            shopService.updateProductStock(productId, newStock, loggedInShop);

            return ResponseEntity.ok(Map.of("message", "Stock updated successfully", "newStock", newStock));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(generateError("Update failed: " + e.getMessage()));
        }
    }

    // --- 6. DELETE PRODUCT ---
    // URL: DELETE http://localhost:8080/api/shop/products/delete/5
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, HttpSession session) {
        // 1. Get the logged-in Shop from the session
        Shop loggedInShop = (Shop) session.getAttribute("loggedInShop");

        // 2. Check if the user is actually logged in
        if (loggedInShop == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(generateError("Unauthorized"));
        }

        try {
            // 3. KEY CHANGE: Pass BOTH the product ID and the loggedInShop
            // The service will check if product.getShop().getAfm() matches loggedInShop.getAfm()
            shopService.deleteProduct(id, loggedInShop);

            return ResponseEntity.ok(Map.of("message", "Product deleted successfully"));

        } catch (Exception e) {
            // This catches the exception if the AFMs don't match (or if product isn't found)
            return ResponseEntity.badRequest()
                    .body(generateError("Delete failed: " + e.getMessage()));
        }
    }

    // --- 7. LOGOUT ---
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // Check if the attribute exists before removing it
        if (session.getAttribute("loggedInShop") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(generateError("Logout failed: No user is currently logged in."));
        }

        // If they were logged in, proceed to logout
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}