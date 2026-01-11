package gr.university.eshop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import gr.university.eshop.model.Cart;


public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCitizen_Afm(Long afm);
    
}
