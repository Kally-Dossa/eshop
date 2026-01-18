package gr.university.eshop.repository;

import gr.university.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopAfm(String afm);
    List<Product> findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String brand, String description);
}
