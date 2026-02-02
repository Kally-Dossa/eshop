package gr.university.eshop.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import gr.university.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByShopAfm(String afm, Pageable pageable);
    List<Product> findByShopAfm(String afm);
    List<Product> findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String brand, String description);
}
