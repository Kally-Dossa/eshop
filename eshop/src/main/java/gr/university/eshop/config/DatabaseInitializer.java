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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {

            System.out.println("--- ΑΡΧΙΚΟΠΟΙΗΣΗ ΔΕΔΟΜΕΝΩΝ ΒΑΣΗΣ ---");

            // 1. Δημιουργία Καταστήματος (Shop)
            Shop shop = new Shop();
            shop.setAfm("999999999");
            shop.setName("Tech Store");
            shop.setEmail("shop@tech.gr");
            shop.setPassword(passwordEncoder.encode("1234"));
            shop.setRole("SHOP");
            shopRepository.save(shop);

            Shop homeStore = new Shop();
            homeStore.setAfm("888888888");
            homeStore.setName("Home & Gadget");
            homeStore.setEmail("sales@homegadget.gr");
            homeStore.setPassword(passwordEncoder.encode("1234"));
            homeStore.setRole("SHOP");
            shopRepository.save(homeStore);

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

            Product p4 = new Product();
            p4.setBrand("HP");
            p4.setDescription("HP Victus 15-fb37");
            p4.setPrice(850.00);
            p4.setStock(16);
            p4.setType("Laptop");
            p4.setShop(shop);
            productRepository.save(p4);

            Product p5 = new Product();
            p5.setBrand("Apple");
            p5.setDescription("MacBook Air 13");
            p5.setPrice(1000.00);
            p5.setStock(10);
            p5.setType("Laptop");
            p5.setShop(shop);
            productRepository.save(p5);

            Product p6 = new Product();
            p6.setBrand("Logitech");
            p6.setDescription("MX Master 3S Mouse");
            p6.setPrice(99.00);
            p6.setStock(25);
            p6.setType("Accessory");
            p6.setShop(shop);
            productRepository.save(p6);

            Product p7 = new Product();
            p7.setBrand("Dell");
            p7.setDescription("UltraSharp 27 Monitor");
            p7.setPrice(450.00);
            p7.setStock(8);
            p7.setType("Monitor");
            p7.setShop(shop);
            productRepository.save(p7);

            Product p8 = new Product();
            p8.setBrand("Sony");
            p8.setDescription("WH-1000XM5 Headphones");
            p8.setPrice(320.00);
            p8.setStock(12);
            p8.setType("Audio");
            p8.setShop(shop);
            productRepository.save(p8);

            Product p9 = new Product();
            p9.setBrand("Xiaomi");
            p9.setDescription("Redmi Note 13 Pro");
            p9.setPrice(350.00);
            p9.setStock(15);
            p9.setType("Smartphone");
            p9.setShop(shop);
            productRepository.save(p9);

            Product p10 = new Product();
            p10.setBrand("Nintendo");
            p10.setDescription("Switch OLED Model");
            p10.setPrice(340.00);
            p10.setStock(10);
            p10.setType("Console");
            p10.setShop(shop);
            productRepository.save(p10);

            Product p11 = new Product();
            p11.setBrand("Razer");
            p11.setDescription("BlackWidow V4 Keyboard");
            p11.setPrice(180.00);
            p11.setStock(7);
            p11.setType("Accessory");
            p11.setShop(shop);
            productRepository.save(p11);

            Product p12 = new Product();
            p12.setBrand("Asus");
            p12.setDescription("ROG Zephyrus G14");
            p12.setPrice(1900.00);
            p12.setStock(3);
            p12.setType("Laptop");
            p12.setShop(shop);
            productRepository.save(p12);

            Product p13 = new Product();
            p13.setBrand("Samsung");
            p13.setDescription("Odyssey G5 32");
            p13.setPrice(300.00);
            p13.setStock(14);
            p13.setType("Monitor");
            p13.setShop(shop);
            productRepository.save(p13);

            // 3. Δημιουργία Χρήστη (Citizen)
            Citizen user = new Citizen();
            user.setAfm("123456789");
            user.setName("Γιάννης");
            user.setSurname("Παπαδόπουλος");
            user.setEmail("user@test.gr");
            user.setPassword(passwordEncoder.encode("1234"));
            user.setRole("CITIZEN");
            Citizen savedUser = citizenRepository.save(user);

            Citizen user2 = new Citizen();
            user2.setAfm("987654321");
            user2.setName("Μαρία");
            user2.setSurname("Κωνσταντίνου");
            user2.setEmail("maria@test.gr");
            user2.setPassword(passwordEncoder.encode("maria1234567"));
            user2.setRole("CITIZEN");
            citizenRepository.save(user2);

            Citizen user3 = new Citizen();
            user3.setAfm("111222333");
            user3.setName("Νίκος");
            user3.setSurname("Γεωργίου");
            user3.setEmail("nikos@test.gr");
            user3.setPassword(passwordEncoder.encode("nikos123"));
            user3.setRole("CITIZEN");
            citizenRepository.save(user3);

            // 4. Δημιουργία Καλαθιού
            //Cart cart = new Cart();
            //cart.setCitizen(savedUser);
            //cartRepository.save(cart);

            System.out.println("--- Η ΒΑΣΗ ΓΕΜΙΣΕ ΕΠΙΤΥΧΩΣ ---");
        }
    }
}