package gr.university.eshop.service;

import gr.university.eshop.model.Product;
import gr.university.eshop.model.Shop;
import gr.university.eshop.repository.ProductRepository;
import gr.university.eshop.repository.ShopRepository;
import gr.university.eshop.dto.ProductDto;
import gr.university.eshop.dto.ShopRegisterDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    // REGISTER SHOP (Αλλαγή: Επιστρέφει Shop αντί για void)
    @Transactional
    public Shop registerShop(ShopRegisterDto dto) throws Exception {
        // Check if email exists
        if (shopRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("This email already exists for a shop!");
        }

        Shop shop = new Shop();
        shop.setName(dto.getName());
        shop.setEmail(dto.getEmail());
        shop.setPassword(dto.getPassword());
        shop.setAfm(dto.getAfm());

        return shopRepository.save(shop); // Επιστροφή του αποθηκευμένου Shop
    }

    // ... (Οι υπόλοιπες μέθοδοι login, addProduct κλπ παραμένουν ίδιες) ...
    public Shop login(String email, String password) throws Exception {
        Optional<Shop> existingShop = shopRepository.findByEmail(email);
        if (existingShop.isPresent() && existingShop.get().getPassword().equals(password)) {
            return existingShop.get();
        } else {
            throw new Exception("Wrong email or password!");
        }
    }

    public void addProductToShop(Shop shop, ProductDto productDto) throws Exception {
        if (shop == null) throw new Exception("Shop not found");
        Product product = new Product(productDto);
        product.setShop(shop);
        productRepository.save(product);
    }

    public void updateProductStock(Long productId, Integer newStock) throws Exception {
        Product product = productRepository.findById(productId).orElseThrow(() -> new Exception("Product not found"));
        product.setStock(newStock);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByShopAfm(Long afm) {
        return productRepository.findByShopAfm(afm);
    }
}