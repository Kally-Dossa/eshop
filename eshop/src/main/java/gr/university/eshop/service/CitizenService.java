package gr.university.eshop.service;

import gr.university.eshop.dto.CitizenRegisterDto;
import gr.university.eshop.model.Cart;
import gr.university.eshop.model.Citizen;
import gr.university.eshop.repository.CartRepository;
import gr.university.eshop.repository.CitizenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Citizen registerCitizen(CitizenRegisterDto dto) throws Exception {
        // 1. Έλεγχος αν τα βασικά πεδία είναι null ή κενά (trimmed)
        if (dto.getAfm() == null || dto.getEmail() == null || dto.getPassword() == null) {
            throw new Exception("Παρακαλώ συμπληρώστε όλα τα απαραίτητα πεδία.");
        }

        // 2. Έλεγχος ΑΦΜ (Μήκος και αν είναι αριθμητικό)
        if (dto.getAfm().length() != 9 || !dto.getAfm().matches("\\d+")) {
            throw new Exception("Το ΑΦΜ πρέπει να αποτελείται από ακριβώς 9 ψηφία.");
        }

        // 3. Έλεγχος μοναδικότητας Email
        if (citizenRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("Το Email χρησιμοποιείται ήδη.");
        }

        // 4. Έλεγχος αν υπάρχει ήδη το ΑΦΜ
        if (citizenRepository.existsById(dto.getAfm())) {
            throw new Exception("Το ΑΦΜ υπάρχει ήδη.");
        }

        // 3. Δημιουργία και Αποθήκευση
        Citizen citizen = new Citizen();
        citizen.setAfm(dto.getAfm());      // Set PK manually
        citizen.setName(dto.getName());
        citizen.setSurname(dto.getSurname()); // Set Surname
        citizen.setEmail(dto.getEmail());
        //always store hashed password and not plain text
        citizen.setPassword(passwordEncoder.encode(dto.getPassword())); // Θυμηθείτε το hashing σε real app
        citizen.setRole("CITIZEN");

        Citizen savedCitizen = citizenRepository.save(citizen);

        /*
        * in cartService for everyendpoint a method is used to retrieve user's cart
        * if no cart is found then it is created so we do not need to create cart here
        */
        return savedCitizen;
    }

    public Citizen login(String email, String password) throws Exception {
        Optional<Citizen> existingUser = citizenRepository.findByEmail(email);
        //before hashing pass applied 
        //if (existingUser.isPresent() && existingUser.get().getPassword().equals(password)) {
        if (existingUser.isPresent() && passwordEncoder.matches(password, existingUser.get().getPassword())) {
            return existingUser.get();
        } else {
            throw new Exception("Λάθος Email ή Κωδικός");
        }
    }
}