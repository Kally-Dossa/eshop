package gr.university.eshop.repository;

import gr.university.eshop.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, String> {
    // Finds a citizen by searching based on their email.
    Optional<Citizen> findByEmail(String email);
}