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

            Product p14 = new Product();
            p14.setBrand("Bose");
            p14.setDescription("QuietComfort Ultra Earbuds");
            p14.setPrice(299.00);
            p14.setStock(18);
            p14.setType("Audio");
            p14.setShop(homeStore);
            productRepository.save(p14);

            Product p15 = new Product();
            p15.setBrand("Microsoft");
            p15.setDescription("Xbox Series X");
            p15.setPrice(499.00);
            p15.setStock(11);
            p15.setType("Console");
            p15.setShop(homeStore);
            productRepository.save(p15);

            Product p16 = new Product();
            p16.setBrand("Canon");
            p16.setDescription("EOS R6 Mark II");
            p16.setPrice(2500.00);
            p16.setStock(4);
            p16.setType("Camera");
            p16.setShop(homeStore);
            productRepository.save(p16);

            Product p17 = new Product();
            p17.setBrand("Dyson");
            p17.setDescription("V15 Detect Vacuum");
            p17.setPrice(750.00);
            p17.setStock(6);
            p17.setType("Home Appliance");
            p17.setShop(homeStore);
            productRepository.save(p17);

            Product p18 = new Product();
            p18.setBrand("Philips");
            p18.setDescription("Hue Smart Bulb Starter Kit");
            p18.setPrice(120.00);
            p18.setStock(30);
            p18.setType("Smart Home");
            p18.setShop(homeStore);
            productRepository.save(p18);

            Product p19 = new Product();
            p19.setBrand("Lenovo");
            p19.setDescription("ThinkPad X1 Carbon Gen 11");
            p19.setPrice(1650.00);
            p19.setStock(5);
            p19.setType("Laptop");
            p19.setShop(homeStore);
            productRepository.save(p19);

            Product p20 = new Product();
            p20.setBrand("Google");
            p20.setDescription("Pixel 8 Pro");
            p20.setPrice(999.00);
            p20.setStock(12);
            p20.setType("Smartphone");
            p20.setShop(homeStore);
            productRepository.save(p20);

            Product p21 = new Product();
            p21.setBrand("LG");
            p21.setDescription("C3 55 inch OLED TV");
            p21.setPrice(1300.00);
            p21.setStock(7);
            p21.setType("TV");
            p21.setShop(homeStore);
            productRepository.save(p21);

            Product p22 = new Product();
            p22.setBrand("Corsair");
            p22.setDescription("Vengeance 32GB DDR5 RAM");
            p22.setPrice(150.00);
            p22.setStock(22);
            p22.setType("PC Component");
            p22.setShop(homeStore);
            productRepository.save(p22);

            Product p23 = new Product();
            p23.setBrand("Garmin");
            p23.setDescription("Fenix 7 Solar");
            p23.setPrice(600.00);
            p23.setStock(9);
            p23.setType("Wearable");
            p23.setShop(homeStore);
            productRepository.save(p23);

            Product p24 = new Product();
            p24.setBrand("GoPro");
            p24.setDescription("HERO12 Black");
            p24.setPrice(400.00);
            p24.setStock(14);
            p24.setType("Camera");
            p24.setShop(homeStore);
            productRepository.save(p24);

            Product p25 = new Product();
            p25.setBrand("SteelSeries");
            p25.setDescription("Arctis Nova Pro Wireless");
            p25.setPrice(350.00);
            p25.setStock(10);
            p25.setType("Accessory");
            p25.setShop(homeStore);
            productRepository.save(p25);

            Product p26 = new Product();
            p26.setBrand("Western Digital");
            p26.setDescription("Black SN850X 2TB SSD");
            p26.setPrice(180.00);
            p26.setStock(25);
            p26.setType("Storage");
            p26.setShop(homeStore);
            productRepository.save(p26);

            Product p27 = new Product();
            p27.setBrand("TP-Link");
            p27.setDescription("Archer AX6000 Router");
            p27.setPrice(280.00);
            p27.setStock(13);
            p27.setType("Networking");
            p27.setShop(homeStore);
            productRepository.save(p27);

            Product p28 = new Product();
            p28.setBrand("Nvidia");
            p28.setDescription("GeForce RTX 4080 Super");
            p28.setPrice(1100.00);
            p28.setStock(2);
            p28.setType("PC Component");
            p28.setShop(homeStore);
            productRepository.save(p28);

            Product p29 = new Product();
            p29.setBrand("Sonos");
            p29.setDescription("Era 100 Smart Speaker");
            p29.setPrice(270.00);
            p29.setStock(9);
            p29.setType("Audio");
            p29.setShop(shop);
            productRepository.save(p29);

            Product p30 = new Product();
            p30.setBrand("Ring");
            p30.setDescription("Video Doorbell Pro 2");
            p30.setPrice(230.00);
            p30.setStock(15);
            p30.setType("Smart Home");
            p30.setShop(shop);
            productRepository.save(p30);

            Product p31 = new Product();
            p31.setBrand("Keychron");
            p31.setDescription("K2 Wireless Mechanical Keyboard");
            p31.setPrice(110.00);
            p31.setStock(20);
            p31.setType("Accessory");
            p31.setShop(shop);
            productRepository.save(p31);

            Product p32 = new Product();
            p32.setBrand("Elgato");
            p32.setDescription("Stream Deck MK.2");
            p32.setPrice(150.00);
            p32.setStock(12);
            p32.setType("Accessory");
            p32.setShop(shop);
            productRepository.save(p32);

            Product p33 = new Product();
            p33.setBrand("Seagate");
            p33.setDescription("Expansion 5TB External HDD");
            p33.setPrice(135.00);
            p33.setStock(18);
            p33.setType("Storage");
            p33.setShop(shop);
            productRepository.save(p33);

            Product p34 = new Product();
            p34.setBrand("BenQ");
            p34.setDescription("ScreenBar Halo Monitor Light");
            p34.setPrice(175.00);
            p34.setStock(10);
            p34.setType("Accessory");
            p34.setShop(homeStore);
            productRepository.save(p34);

            Product p35 = new Product();
            p35.setBrand("Epson");
            p35.setDescription("EcoTank ET-2810 Printer");
            p35.setPrice(220.00);
            p35.setStock(6);
            p35.setType("Office");
            p35.setShop(homeStore);
            productRepository.save(p35);

            Product p36 = new Product();
            p36.setBrand("Wacom");
            p36.setDescription("Intuos Pro Medium");
            p36.setPrice(340.00);
            p36.setStock(5);
            p36.setType("Creative");
            p36.setShop(homeStore);
            productRepository.save(p36);

            Product p37 = new Product();
            p37.setBrand("Blue");
            p37.setDescription("Yeti USB Microphone");
            p37.setPrice(130.00);
            p37.setStock(14);
            p37.setType("Audio");
            p37.setShop(homeStore);
            productRepository.save(p37);

            Product p38 = new Product();
            p38.setBrand("HyperX");
            p38.setDescription("Cloud II Gaming Headset");
            p38.setPrice(95.00);
            p38.setStock(30);
            p38.setType("Audio");
            p38.setShop(homeStore);
            productRepository.save(p38);

            Product p39 = new Product();
            p39.setBrand("Eufy");
            p39.setDescription("RoboVac L35 Hybrid");
            p39.setPrice(399.00);
            p39.setStock(7);
            p39.setType("Home Appliance");
            p39.setShop(homeStore);
            productRepository.save(p39);

            Product p40 = new Product();
            p40.setBrand("Crucial");
            p40.setDescription("X9 Pro 2TB Portable SSD");
            p40.setPrice(160.00);
            p40.setStock(22);
            p40.setType("Storage");
            p40.setShop(homeStore);
            productRepository.save(p40);

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