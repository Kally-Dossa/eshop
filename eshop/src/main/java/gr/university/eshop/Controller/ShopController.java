package gr.university.eshop.Controller;

import gr.university.eshop.Entity.Product;
import gr.university.eshop.Service.ShopService; // Το Service που φτιάξαμε πριν
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {

    @Autowired // Connection with Service
    private ShopService shopService;

    // --- FUNCTION 1: Add Product to Shop ---
    // URL: POST /add/product/{shopAfm}
    // Body: Product JSON object
    @PostMapping("/add/product/{shopAfm}")
    public String addProduct(@PathVariable String shopAfm, @RequestBody Product product) {
        try {
            shopService.addProductToShop(shopAfm, product);
            return "Το προϊόν προστέθηκε επιτυχώς στο κατάστημα με ΑΦΜ: " + shopAfm;
        } catch (Exception e) {
            return "Σφάλμα: " + e.getMessage();
        }
    }

    // --- FUNCTION 2: Update Product Stock ---
    // URL: PUT /update/stock/{productId}

    @PutMapping("/update/stock/{productId}")
    public String updateStock(@PathVariable Long productId, @RequestParam Integer newStock) {
        try {
            shopService.updateProductStock(productId, newStock);
            return "Το stock του προϊόντος (ID: " + productId + ") ενημερώθηκε σε: " + newStock;
        } catch (Exception e) {
            return "Σφάλμα: " + e.getMessage();
        }
    }
}