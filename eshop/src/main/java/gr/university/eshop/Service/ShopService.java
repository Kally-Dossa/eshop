package gr.university.eshop.Service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import gr.university.eshop.Entity.*;
import gr.university.eshop.Repository.*;

import java.util.Optional;

@Service // [cite: 92]
public class ShopService {

    @Autowired // [cite: 94]
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    // --- FUNCTION 1: Add Product (for a shop) ---
    public void addProductToShop(String shopAfm, Product product) throws Exception {
        // 1. We find the shop based on AFM (like in findById)
        Optional<Shop> shopOpt = shopRepository.findById(shopAfm);

        if (shopOpt.isPresent()) {
            Shop shop = shopOpt.get();

            // 2. We connect the product with the shop.
            // We use the helper method addProduct that already exists in Shop.java
            shop.addProduct(product);

            // 3. We save the shop.
            // the product will be automatically saved in the database
            shopRepository.save(shop);
        } else {
            throw new Exception("Shop with AFM " + shopAfm + " not found.");
        }
    }

    // --- FUNCTION 2: Update Product Stock ---
    public void updateProductStock(Long productId, Integer newStock) throws Exception {
        // 1. We find the product by its ID
        Optional<Product> productOpt = productRepository.findById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // 2. We update the stock
            product.setStock(newStock);

            // 3. We save the changes (update)
            productRepository.save(product);
        } else {
            throw new Exception("Product with ID " + productId + " not found.");
        }
    }

    public Shop findShopByAfm(String afm) throws Exception {
        return shopRepository.findById(afm)
                .orElseThrow(() -> new Exception("Shop not found"));
    }
}