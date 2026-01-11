<<<<<<< HEAD
package gr.university.eshop.Repository;

import gr.university.eshop.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByShopAfm(String afm);
}
=======
package gr.university.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.university.eshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
>>>>>>> menelaos/cart-checkout
