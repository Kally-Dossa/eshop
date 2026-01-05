package gr.university.eshop.Service;

import gr.university.eshop.Entity.*;
import gr.university.eshop.Repository.*;

import java.util.Optional;
import org.springframework.boot.*;
import org.springframework.context.annotation.*;
import org.springframework.beans.factory.annotation.*;

@Configuration
public class ShopServiceConfig implements CommandLineRunner{
    @Autowired
    private ShopRepository shopRepository;


    @Override
    public void run(String... args) throws Exception {

        String myShopAfm = "123456789";

        // Έλεγχος αν υπάρχει το κατάστημα για να μην το ξαναφτιάχνουμε
        Optional<Shop> existingShop = shopRepository.findById(myShopAfm);

        if (!existingShop.isPresent()) {
            // Δημιουργία Καταστήματος
            Shop shop = new Shop(myShopAfm, "Tech Shop", "Efi", "pass123");

            // Δημιουργία αρχικών προϊόντων
            Product p1 = new Product();
            p1.setType("Laptop");
            p1.setBrand("Dell");
            p1.setPrice(1000.0);
            p1.setStock(10);

            // Προσθήκη προϊόντος στο κατάστημα (σχέση One-to-Many)
            shop.addProduct(p1);

            // Αποθήκευση στη βάση (Cascade ALL θα αποθηκεύσει και το προϊόν)
            shopRepository.save(shop);

            System.out.println("--> Το κατάστημα και το προϊόν δημιουργήθηκαν επιτυχώς!");
        } else {
            System.out.println("--> Το κατάστημα υπάρχει ήδη.");
        }
    }
}
