package gr.university.eshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.university.eshop.Entity.*;

public interface ProductRepository extends JpaRepository<Product, Long> { // ID is Long
}