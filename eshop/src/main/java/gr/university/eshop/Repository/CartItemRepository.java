package gr.university.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.university.eshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    boolean existsByProductIdAndCartId(Long productId, Long cartId);
}
