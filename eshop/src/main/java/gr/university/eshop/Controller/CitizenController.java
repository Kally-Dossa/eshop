package gr.university.eshop.Controller;

import gr.university.eshop.Entity.Citizen;
import gr.university.eshop.Service.CitizenService;
import gr.university.eshop.DTO.CitizenRegisterDto;
import gr.university.eshop.DTO.LoginDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Means this class responds with data (not HTML yet)
@RequestMapping("/api/citizen") // All URLs will start with /api/citizen
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    // --- REGISTRATION ---
    // URL: http://localhost:8080/api/citizen/register
    @PostMapping("/register")
    public String register(@RequestBody CitizenRegisterDto dto) {
        try {
            citizenService.registerCitizen(dto);
            return "Registration completed successfully!";
        } catch (Exception e) {
            return "Registration error: " + e.getMessage();
        }
    }

    // --- LOGIN ---
    // URL: http://localhost:8080/api/citizen/login
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            Citizen citizen = citizenService.login(loginDto.getEmail(), loginDto.getPassword());

            // WE KEEP THE USER IN MEMORY (SESSION)
            session.setAttribute("loggedInUser", citizen);

            return "Welcome " + citizen.getName();
        } catch (Exception e) {
            return "Connection error: " + e.getMessage();
        }
    }

    // --- LOGOUT ---
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // clear memory
        return "You are logged out.";
    }
}