package gr.university.eshop.config;

import gr.university.eshop.model.Cart;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.model.Product;
import gr.university.eshop.model.Shop;
import gr.university.eshop.repository.CartRepository;
import gr.university.eshop.repository.CitizenRepository;
import gr.university.eshop.repository.ProductRepository;
import gr.university.eshop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {

            System.out.println("--- ΑΡΧΙΚΟΠΟΙΗΣΗ ΔΕΔΟΜΕΝΩΝ ΒΑΣΗΣ ---");

            // 1. Δημιουργία Καταστήματος (Shop)
            Shop shop = new Shop();
            shop.setAfm("999999999");
            shop.setName("Tech Store");
            shop.setEmail("shop@tech.gr");
            shop.setPassword("1234");
            shop.setRole("SHOP");
            shopRepository.save(shop);

            // 2. Δημιουργία Προϊόντων
            Product p1 = new Product();
            p1.setBrand("Apple");
            p1.setDescription("iPhone 15 Pro");
            p1.setPrice(1200.00);
            p1.setStock(10);
            p1.setType("Smartphone");
            p1.setShop(shop);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setBrand("Samsung");
            p2.setDescription("Galaxy S24 Ultra");
            p2.setPrice(1400.00);
            p2.setStock(5);
            p2.setType("Smartphone");
            p2.setShop(shop);
            productRepository.save(p2);

            Product p3 = new Product();
            p3.setBrand("Sony");
            p3.setDescription("PlayStation 5");
            p3.setPrice(550.00);
            p3.setStock(20);
            p3.setType("Console");
            p3.setShop(shop);
            productRepository.save(p3);

            // 3. Δημιουργία Χρήστη (Citizen)
            Citizen user = new Citizen();
            user.setAfm("123456789");
            user.setName("Γιάννης");
            user.setSurname("Παπαδόπουλος");
            user.setEmail("user@test.gr");
            user.setPassword("1234");
            user.setRole("CITIZEN");
            Citizen savedUser = citizenRepository.save(user);

            // 4. Δημιουργία Καλαθιού
            Cart cart = new Cart();
            cart.setCitizen(savedUser);
            cartRepository.save(cart);

            System.out.println("--- Η ΒΑΣΗ ΓΕΜΙΣΕ ΕΠΙΤΥΧΩΣ ---");
        }
    }
}