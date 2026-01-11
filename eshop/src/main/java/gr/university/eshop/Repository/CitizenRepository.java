package gr.university.eshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gr.university.eshop.model.Citizen;

public interface CitizenRepository extends JpaRepository<Citizen, String>{

}
