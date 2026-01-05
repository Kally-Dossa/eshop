package gr.university.eshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.university.eshop.Entity.*;


public interface ShopRepository extends JpaRepository<Shop, String> { // ID is String (AFM)
}
