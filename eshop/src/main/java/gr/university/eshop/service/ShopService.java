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
        shop.setAfm(dto.getAfm()); // Μετατροπή String -> Long
        shop.setRole("SHOP");
        return shopRepository.save(shop); // Επιστροφή του αποθηκευμένου Shop
    }


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

    // Update the method signature to accept 'Shop loggedInShop'
    public void updateProductStock(Long productId, Integer newStock, Shop loggedInShop) throws Exception {

        // 1. Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        // 2. SECURITY CHECK: Compare AFMs
        if (!product.getShop().getAfm().equals(loggedInShop.getAfm())) {
            throw new Exception("Unauthorized: You do not own this product.");
        }

        // 3. Update stock and save
        product.setStock(newStock);
        productRepository.save(product);
    }

    public void deleteProduct(Long productId, Shop loggedInShop) throws Exception {
        // 1. Find the product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        // 2. CHECK: Does the product's shop ID match the logged-in shop AFM
        if (!product.getShop().getAfm().equals(loggedInShop.getAfm())) {
            throw new Exception("Unauthorized: You do not own this product.");
        }

        // 3. Delete
        productRepository.delete(product);
    }

    public List<Product> getProductsByShopAfm(String afm) {
        return productRepository.findByShopAfm(afm);
    }
}