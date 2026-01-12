package gr.university.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.university.eshop.model.Order;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, String>{
    List<Order> findByCitizen_AfmOrderByOrderDateDesc(String afm);
}
