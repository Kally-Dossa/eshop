package gr.university.eshop.Repository;

import gr.university.eshop.Entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByEmail(String email);
}