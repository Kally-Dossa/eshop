package gr.university.eshop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import gr.university.eshop.model.Cart;
import gr.university.eshop.model.CartItem;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    boolean existsByProductIdAndCartId(Long productId, Long cartId);
    Optional<CartItem> findByProductIdAndCartId(Long productId, Long CartId);

    List<CartItem> findByCart(Cart cart);
}
