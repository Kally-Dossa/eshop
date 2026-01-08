package gr.university.eshop.Repository;

import gr.university.eshop.Entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {

    // This method is required for Login and Registration.
    // Finds a citizen by searching based on their email.
    Optional<Citizen> findByEmail(String email);
}