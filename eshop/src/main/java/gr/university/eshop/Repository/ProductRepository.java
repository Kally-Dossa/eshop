package gr.university.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.university.eshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
