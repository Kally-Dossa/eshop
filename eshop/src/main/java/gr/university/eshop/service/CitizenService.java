package gr.university.eshop.service;

import gr.university.eshop.entity.Cart;
import gr.university.eshop.entity.Citizen;
import gr.university.eshop.repository.CitizenRepository;
import gr.university.eshop.dto.CitizenRegisterDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    // FUNCTION 1: REGISTER
    @Transactional // If something goes wrong, it cancels the entire process at the base.
    public void registerCitizen(CitizenRegisterDto dto) throws Exception {

        // Step 1: Check if the email already exists
        if (citizenRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("This email already exists!");
        }

        // Step 2: Transfer data from DTO to Entity (Table)
        Citizen citizen = new Citizen();
        citizen.setName(dto.getName());
        citizen.setEmail(dto.getEmail());
        citizen.setPassword(dto.getPassword());
        citizen.setAfm(dto.getAfm());

        // Step 3: Create an Empty Cart for the new customer
        Cart cart = new Cart();
        // We connect the basket to the citizen and back
        cart.setCitizen(citizen);
        citizen.setCart(cart);

        // Step 4: save on db
        citizenRepository.save(citizen);
    }

    // FUNCTION 2: LOGIN
    public Citizen login(String email, String password) throws Exception {

        // We search for the citizen based on the email
        Optional<Citizen> existingCitizen = citizenRepository.findByEmail(email);

        // If the user exists AND the password is correct
        if (existingCitizen.isPresent() && existingCitizen.get().getPassword().equals(password)) {
            return existingCitizen.get(); // Return the user to put them in the Session
        } else {
            throw new Exception("Wrong email or password!");
        }
    }
}
